package cn.study.im.mvc.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.study.common.dozer.service.IGenerator;
import cn.study.common.utils.SpringContextHolder;
import cn.study.im.constants.BaseConstants;
import cn.study.im.enums.LayimApplyStatusEnum;
import cn.study.im.enums.LayimApplyTypeEnum;
import cn.study.im.enums.LayimMessageTypeEnum;
import cn.study.im.model.LayuiResult;
import cn.study.im.mvc.domain.dto.MsgBoxUser;
import cn.study.im.mvc.domain.entity.*;
import cn.study.im.mvc.domain.message.TxtMessage;
import cn.study.im.mvc.domain.po.ApplyGroupPo;
import cn.study.im.mvc.domain.po.ApplyUserPo;
import cn.study.im.mvc.domain.po.AuditPo;
import cn.study.im.mvc.domain.vo.LayimChatlogVo;
import cn.study.im.mvc.domain.vo.LayimGroupVo;
import cn.study.im.mvc.domain.vo.LayimUserGroupVo;
import cn.study.im.mvc.domain.vo.LayimUserVo;
import cn.study.im.mvc.service.*;
import cn.study.im.netty.event.MsgBoxEvent;
import cn.study.im.netty.utils.ObjectMapperUtils;
import cn.study.im.security.LayimUser;
import cn.study.im.security.SecurityHelper;
import cn.study.im.security.annotation.AnonymousAccess;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
@Slf4j
@RestController
@RequestMapping("/layim")
public class LayimController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserGroupService userGroupService;
    @Resource
    private UserFriendGroupService userFriendGroupService;
    @Resource
    private IGenerator generator;
    @Resource
    private RelUserFriendGroupService relUserFriendGroupService;
    @Resource
    private RelUserGroupService relUserGroupService;
    @Resource
    private ApplyAuditService applyAuditService;
    @Resource
    private MessageLogService messageLogService;

    /**
     * 获取主页信息
     *
     * @return
     */
    @GetMapping("/getList")
    public LayuiResult getList() {
        String id = SecurityHelper.getUserId();

        // 个人信息
        LayimUserVo mine = generator.convert(userInfoService.getById(id), LayimUserVo.class);
        // 好友
        List<LayimUserGroupVo> userGroups = userFriendGroupService.getUserFriendGroupListByUserId(id);
        // 群
        List<LayimGroupVo> groups = userGroupService.getGroupsByUserId(id);

        Map<String, Object> map = new HashMap<>();
        map.put("mine", mine);
        map.put("friend", userGroups);
        map.put("group", groups);
        return LayuiResult.ok().data(map);
    }

    /**
     * 获取群成员
     */
    @GetMapping("/getMembers")
    public LayuiResult getMembers(
            @RequestParam("id") String id
    ) {
        // 成员列表
        List<LayimUserVo> list = userGroupService.getUsersByGroupId(id);

        Map<String, Object> map = new HashMap<>();
        map.put("members", list.size());
        map.put("list", list);
        return LayuiResult.ok().data(map);
    }

    /**
     * 自动回复
     */
    @GetMapping("/autoreply")
    public LayuiResult autoreply() {

        Map<String, Object> map = new HashMap<>();
        return LayuiResult.ok().data(map);
    }

    /**
     * 修改用户相关
     */
    @PutMapping("/user")
    public LayuiResult updateUser(
            @RequestBody LayimUserVo resource
    ) throws Exception {
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInfo::getId, resource.getId());
        boolean flag = false;
        if (StrUtil.isNotBlank(resource.getStatus())) {
            updateWrapper.set(UserInfo::getStatus, resource.getStatus());
            flag = true;
        }
        if (StrUtil.isNotBlank(resource.getSign())) {
            updateWrapper.set(UserInfo::getSign, resource.getSign());
            flag = true;
        }
        if (flag) {
            userInfoService.update(updateWrapper);
        }
        return LayuiResult.ok();
    }

    /**
     * 搜索好友
     */
    @GetMapping("/search/user")
    public LayuiResult searchUser(
            @RequestParam("keyword") String keyword
    ) {
        return LayuiResult.ok().data(userInfoService.list(new LambdaQueryWrapper<UserInfo>().like(UserInfo::getUsername, keyword)));
    }

    /**
     * 搜索群
     */
    @GetMapping("/search/group")
    public LayuiResult searchGroup(
            @RequestParam("keyword") String keyword
    ) {
        return LayuiResult.ok().data(userGroupService.list(new LambdaQueryWrapper<UserGroup>().like(UserGroup::getGroupname, keyword)));
    }

    /**
     * 申请加好友
     */
    @PostMapping("/apply/user")
    public LayuiResult applyUser(
            @RequestBody ApplyUserPo po
    ) {
        if (StrUtil.equals(po.getMineId(), po.getToId())) {
            return LayuiResult.failed().msg("不能添加自己为好友");
        }

        // 验证是否已为好友
        List<RelUserFriendGroup> meRels = relUserFriendGroupService.list(new LambdaQueryWrapper<RelUserFriendGroup>()
                .eq(RelUserFriendGroup::getUserId, po.getMineId())
                .eq(RelUserFriendGroup::getFriendId, po.getToId())
        );
        List<RelUserFriendGroup> hisRels = relUserFriendGroupService.list(new LambdaQueryWrapper<RelUserFriendGroup>()
                .eq(RelUserFriendGroup::getUserId, po.getToId())
                .eq(RelUserFriendGroup::getFriendId, po.getMineId())
        );
        if (CollectionUtil.isNotEmpty(meRels) && CollectionUtil.isNotEmpty(hisRels)) {
            return LayuiResult.failed().msg("已成为好友，不能重复添加");
        }

        // 是否已经发过验证
        List<ApplyAudit> applyAudits = applyAuditService.list(new LambdaQueryWrapper<ApplyAudit>()
                .eq(ApplyAudit::getApplyUserId, po.getMineId())
                .eq(ApplyAudit::getAuditUserId, po.getToId())
                .eq(ApplyAudit::getStatus, LayimApplyStatusEnum.APPLY)
        );
        if (CollectionUtil.isNotEmpty(applyAudits)) {
            return LayuiResult.failed().msg("已经发送过验证了，请等待对方同意");
        }

        // 添加申请
        ApplyAudit applyAudit = new ApplyAudit();
        applyAudit.setType(LayimApplyTypeEnum.FRIEND.getValue());
        applyAudit.setApplyUserId(po.getMineId());
        applyAudit.setAuditUserId(po.getToId());
        applyAudit.setGroupId(po.getGroupId());
        applyAudit.setRemark(po.getRemark());
        applyAudit.setStatus(LayimApplyStatusEnum.APPLY.getValue());
        applyAuditService.save(applyAudit);

        // 给审核人发送通知
        SpringContextHolder.getApplicationContext().publishEvent(new MsgBoxEvent(new MsgBoxUser(po.getToId())));
        return LayuiResult.ok();
    }

    /**
     * 申请加群
     */
    @PostMapping("/apply/group")
    public LayuiResult applyGroup(
            @RequestBody ApplyGroupPo po
    ) {
        // 验证是否存在
        List<RelUserGroup> list = relUserGroupService.list(new LambdaQueryWrapper<RelUserGroup>()
                .eq(RelUserGroup::getUserId, po.getMineId())
                .eq(RelUserGroup::getGroupId, po.getGroupId())
        );
        if (CollectionUtil.isNotEmpty(list)) {
            return LayuiResult.failed().msg("已在该群里，请勿重复添加");
        }

        // 查找群
        UserGroup userGroup = userGroupService.getById(po.getGroupId());

        // 是否已经发过验证
        List<ApplyAudit> applyAudits = applyAuditService.list(new LambdaQueryWrapper<ApplyAudit>()
                .eq(ApplyAudit::getApplyUserId, po.getMineId())
                .eq(ApplyAudit::getAuditUserId, userGroup.getUserId())
                .eq(ApplyAudit::getStatus, LayimApplyStatusEnum.APPLY)
        );
        if (CollectionUtil.isNotEmpty(applyAudits)) {
            return LayuiResult.failed().msg("已经发送过验证了，请等待对方同意");
        }

        if (StrUtil.equals(SecurityHelper.getUserId(), userGroup.getUserId())) {
            return LayuiResult.failed().msg("您是该群的群主，不要重复添加哦！");
        }

        // 添加申请
        ApplyAudit applyAudit = new ApplyAudit();
        applyAudit.setType(LayimApplyTypeEnum.GROUP.getValue());
        applyAudit.setApplyUserId(po.getMineId());
        applyAudit.setAuditUserId(userGroup.getUserId());
        applyAudit.setGroupId(po.getGroupId());
        applyAudit.setRemark(po.getRemark());
        applyAudit.setStatus(LayimApplyStatusEnum.APPLY.getValue());
        applyAuditService.save(applyAudit);

        // 给审核人发送通知
        SpringContextHolder.getApplicationContext().publishEvent(new MsgBoxEvent(new MsgBoxUser(userGroup.getUserId())));
        return LayuiResult.ok();
    }

    @PostMapping("/audit/apply")
    public LayuiResult auditUser(
            @RequestBody AuditPo po
    ) {
        applyAuditService.apply(po);
        return LayuiResult.ok();
    }


    /**
     * 我的消息列表
     */
    @GetMapping("/audit/list")
    public LayuiResult auditList(Pageable pageable) {
        return applyAuditService.getMsgList(pageable);
    }

    /**
     * 注册用户
     */
    @AnonymousAccess
    @PostMapping("/register")
    public LayuiResult register(
            @RequestBody UserInfo userInfo
    ) {
        Assert.notBlank(userInfo.getUsername());
        userInfoService.register(userInfo);
        return LayuiResult.ok();
    }

    /**
     * 未读审核消息
     * @return
     */
    @GetMapping("/unreadmsg")
    public LayuiResult unreadmsg(){
        String userId = SecurityHelper.getUserId();

        long unreadCount = applyAuditService.count(new LambdaQueryWrapper<ApplyAudit>()
                .in(ApplyAudit::getAuditUserId, userId)
                .eq(ApplyAudit::getStatus, LayimApplyStatusEnum.APPLY.getValue()));

        return LayuiResult.ok().data(unreadCount);
    }

    /**
     * 聊天记录
     */
    @GetMapping("/chatlog")
    public LayuiResult chatlog(
            @RequestParam("id") String id,
            @RequestParam("type") String type,
            @PageableDefault(page = 1) Pageable pageable
    ){
        LayimUser layimUser = SecurityHelper.getUser();
        String userId = layimUser.getId();
        PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize());
        List<MessageLog> list = new ArrayList<>();
        Map<String,UserInfo> userMap = new HashMap<>();
        if (LayimMessageTypeEnum.FRIEND.getValue().equals(type)){
            // 单聊
            list = messageLogService.list(new LambdaQueryWrapper<MessageLog>()
                    .eq(MessageLog::getChatType, type)
                    .and(item -> item.nested(n -> n.eq(MessageLog::getFromId,userId).eq(MessageLog::getToId,id))
                            .or().nested(n -> n.eq(MessageLog::getFromId,id).eq(MessageLog::getToId,userId))
                    )
                    .orderByDesc(MessageLog::getSendTime)
            );

            UserInfo userInfo = new UserInfo();
            userInfo.setId(layimUser.getId());
            userInfo.setUsername(layimUser.getUsername());
            userInfo.setAvatar(layimUser.getAvatar());
            userMap.put(userId,userInfo);
            UserInfo friendUser = Optional.ofNullable(userInfoService.getById(id)).orElse(new UserInfo());
            userMap.put(id,friendUser);
        }else if (LayimMessageTypeEnum.GROUP.getValue().equals(type)){
            // 群聊
            list = messageLogService.list(new LambdaQueryWrapper<MessageLog>()
                    .eq(MessageLog::getChatType, type)
                    .eq(MessageLog::getToId,id)
                    .orderByDesc(MessageLog::getSendTime)
            );

            List<String> uids = relUserGroupService.list(new LambdaQueryWrapper<RelUserGroup>()
                    .eq(RelUserGroup::getGroupId, id)
            ).stream()
                    .map(RelUserGroup::getUserId)
                    .distinct()
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(uids)){
                userInfoService.list(new LambdaQueryWrapper<UserInfo>().in(UserInfo::getId,uids))
                .forEach(item -> userMap.put(item.getId(),item));
            }
        }
        List<LayimChatlogVo> logs = list.stream()
                .map(item -> {
                    LayimChatlogVo chatlog = new LayimChatlogVo();
                    UserInfo fuser = Optional.ofNullable(userMap.get(item.getFromId())).orElse(new UserInfo());
                    chatlog.setUsername(Optional.ofNullable(fuser.getUsername()).orElse("访客-" + IdUtil.simpleUUID()));
                    chatlog.setAvatar(Optional.ofNullable(fuser.getAvatar()).orElse(BaseConstants.DEFAULT_AVATAR));
                    chatlog.setId(fuser.getId());
                    chatlog.setUsername(Optional.ofNullable(fuser.getUsername()).orElse("访客-" + IdUtil.simpleUUID()));
                    chatlog.setAvatar(Optional.ofNullable(fuser.getAvatar()).orElse(BaseConstants.DEFAULT_AVATAR));
                    chatlog.setTimestamp(item.getSendTime().getTime());
                    chatlog.setContent(Optional.ofNullable(ObjectMapperUtils.readValue(item.getContent(), TxtMessage.class)).map(TxtMessage::getMsg).orElse(item.getContent()));
                    return chatlog;
                }).collect(Collectors.toList());

        PageInfo<MessageLog> pageInfo = new PageInfo<>(list);
        return LayuiResult.ok().data(logs).pages(pageInfo.getPages()).total(pageInfo.getTotal());
    }

    /**
     * 创建群
     */
    @PostMapping("/group/add")
    public LayuiResult groupCreate(@RequestBody UserGroup userGroup) {
        try {
            userGroup.setAvatar(BaseConstants.DEFAULT_AVATAR);
            userGroup.setUserId(SecurityHelper.getUserId());
            userGroupService.save(userGroup);
        }catch (DuplicateKeyException e) {
            return LayuiResult.failed().msg("["+userGroup.getGroupname()+"]已存在，请更换名称");
        }
        return LayuiResult.ok();
    }

}
