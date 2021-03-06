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
     * ??????????????????
     *
     * @return
     */
    @GetMapping("/getList")
    public LayuiResult getList() {
        String id = SecurityHelper.getUserId();

        // ????????????
        LayimUserVo mine = generator.convert(userInfoService.getById(id), LayimUserVo.class);
        // ??????
        List<LayimUserGroupVo> userGroups = userFriendGroupService.getUserFriendGroupListByUserId(id);
        // ???
        List<LayimGroupVo> groups = userGroupService.getGroupsByUserId(id);

        Map<String, Object> map = new HashMap<>();
        map.put("mine", mine);
        map.put("friend", userGroups);
        map.put("group", groups);
        return LayuiResult.ok().data(map);
    }

    /**
     * ???????????????
     */
    @GetMapping("/getMembers")
    public LayuiResult getMembers(
            @RequestParam("id") String id
    ) {
        // ????????????
        List<LayimUserVo> list = userGroupService.getUsersByGroupId(id);

        Map<String, Object> map = new HashMap<>();
        map.put("members", list.size());
        map.put("list", list);
        return LayuiResult.ok().data(map);
    }

    /**
     * ????????????
     */
    @GetMapping("/autoreply")
    public LayuiResult autoreply() {

        Map<String, Object> map = new HashMap<>();
        return LayuiResult.ok().data(map);
    }

    /**
     * ??????????????????
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
     * ????????????
     */
    @GetMapping("/search/user")
    public LayuiResult searchUser(
            @RequestParam("keyword") String keyword
    ) {
        return LayuiResult.ok().data(userInfoService.list(new LambdaQueryWrapper<UserInfo>().like(UserInfo::getUsername, keyword)));
    }

    /**
     * ?????????
     */
    @GetMapping("/search/group")
    public LayuiResult searchGroup(
            @RequestParam("keyword") String keyword
    ) {
        return LayuiResult.ok().data(userGroupService.list(new LambdaQueryWrapper<UserGroup>().like(UserGroup::getGroupname, keyword)));
    }

    /**
     * ???????????????
     */
    @PostMapping("/apply/user")
    public LayuiResult applyUser(
            @RequestBody ApplyUserPo po
    ) {
        if (StrUtil.equals(po.getMineId(), po.getToId())) {
            return LayuiResult.failed().msg("???????????????????????????");
        }

        // ????????????????????????
        List<RelUserFriendGroup> meRels = relUserFriendGroupService.list(new LambdaQueryWrapper<RelUserFriendGroup>()
                .eq(RelUserFriendGroup::getUserId, po.getMineId())
                .eq(RelUserFriendGroup::getFriendId, po.getToId())
        );
        List<RelUserFriendGroup> hisRels = relUserFriendGroupService.list(new LambdaQueryWrapper<RelUserFriendGroup>()
                .eq(RelUserFriendGroup::getUserId, po.getToId())
                .eq(RelUserFriendGroup::getFriendId, po.getMineId())
        );
        if (CollectionUtil.isNotEmpty(meRels) && CollectionUtil.isNotEmpty(hisRels)) {
            return LayuiResult.failed().msg("????????????????????????????????????");
        }

        // ????????????????????????
        List<ApplyAudit> applyAudits = applyAuditService.list(new LambdaQueryWrapper<ApplyAudit>()
                .eq(ApplyAudit::getApplyUserId, po.getMineId())
                .eq(ApplyAudit::getAuditUserId, po.getToId())
                .eq(ApplyAudit::getStatus, LayimApplyStatusEnum.APPLY)
        );
        if (CollectionUtil.isNotEmpty(applyAudits)) {
            return LayuiResult.failed().msg("????????????????????????????????????????????????");
        }

        // ????????????
        ApplyAudit applyAudit = new ApplyAudit();
        applyAudit.setType(LayimApplyTypeEnum.FRIEND.getValue());
        applyAudit.setApplyUserId(po.getMineId());
        applyAudit.setAuditUserId(po.getToId());
        applyAudit.setGroupId(po.getGroupId());
        applyAudit.setRemark(po.getRemark());
        applyAudit.setStatus(LayimApplyStatusEnum.APPLY.getValue());
        applyAuditService.save(applyAudit);

        // ????????????????????????
        SpringContextHolder.getApplicationContext().publishEvent(new MsgBoxEvent(new MsgBoxUser(po.getToId())));
        return LayuiResult.ok();
    }

    /**
     * ????????????
     */
    @PostMapping("/apply/group")
    public LayuiResult applyGroup(
            @RequestBody ApplyGroupPo po
    ) {
        // ??????????????????
        List<RelUserGroup> list = relUserGroupService.list(new LambdaQueryWrapper<RelUserGroup>()
                .eq(RelUserGroup::getUserId, po.getMineId())
                .eq(RelUserGroup::getGroupId, po.getGroupId())
        );
        if (CollectionUtil.isNotEmpty(list)) {
            return LayuiResult.failed().msg("????????????????????????????????????");
        }

        // ?????????
        UserGroup userGroup = userGroupService.getById(po.getGroupId());

        // ????????????????????????
        List<ApplyAudit> applyAudits = applyAuditService.list(new LambdaQueryWrapper<ApplyAudit>()
                .eq(ApplyAudit::getApplyUserId, po.getMineId())
                .eq(ApplyAudit::getAuditUserId, userGroup.getUserId())
                .eq(ApplyAudit::getStatus, LayimApplyStatusEnum.APPLY)
        );
        if (CollectionUtil.isNotEmpty(applyAudits)) {
            return LayuiResult.failed().msg("????????????????????????????????????????????????");
        }

        if (StrUtil.equals(SecurityHelper.getUserId(), userGroup.getUserId())) {
            return LayuiResult.failed().msg("????????????????????????????????????????????????");
        }

        // ????????????
        ApplyAudit applyAudit = new ApplyAudit();
        applyAudit.setType(LayimApplyTypeEnum.GROUP.getValue());
        applyAudit.setApplyUserId(po.getMineId());
        applyAudit.setAuditUserId(userGroup.getUserId());
        applyAudit.setGroupId(po.getGroupId());
        applyAudit.setRemark(po.getRemark());
        applyAudit.setStatus(LayimApplyStatusEnum.APPLY.getValue());
        applyAuditService.save(applyAudit);

        // ????????????????????????
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
     * ??????????????????
     */
    @GetMapping("/audit/list")
    public LayuiResult auditList(Pageable pageable) {
        return applyAuditService.getMsgList(pageable);
    }

    /**
     * ????????????
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
     * ??????????????????
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
     * ????????????
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
            // ??????
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
            // ??????
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
                    chatlog.setUsername(Optional.ofNullable(fuser.getUsername()).orElse("??????-" + IdUtil.simpleUUID()));
                    chatlog.setAvatar(Optional.ofNullable(fuser.getAvatar()).orElse(BaseConstants.DEFAULT_AVATAR));
                    chatlog.setId(fuser.getId());
                    chatlog.setUsername(Optional.ofNullable(fuser.getUsername()).orElse("??????-" + IdUtil.simpleUUID()));
                    chatlog.setAvatar(Optional.ofNullable(fuser.getAvatar()).orElse(BaseConstants.DEFAULT_AVATAR));
                    chatlog.setTimestamp(item.getSendTime().getTime());
                    chatlog.setContent(Optional.ofNullable(ObjectMapperUtils.readValue(item.getContent(), TxtMessage.class)).map(TxtMessage::getMsg).orElse(item.getContent()));
                    return chatlog;
                }).collect(Collectors.toList());

        PageInfo<MessageLog> pageInfo = new PageInfo<>(list);
        return LayuiResult.ok().data(logs).pages(pageInfo.getPages()).total(pageInfo.getTotal());
    }

    /**
     * ?????????
     */
    @PostMapping("/group/add")
    public LayuiResult groupCreate(@RequestBody UserGroup userGroup) {
        try {
            userGroup.setAvatar(BaseConstants.DEFAULT_AVATAR);
            userGroup.setUserId(SecurityHelper.getUserId());
            userGroupService.save(userGroup);
        }catch (DuplicateKeyException e) {
            return LayuiResult.failed().msg("["+userGroup.getGroupname()+"]???????????????????????????");
        }
        return LayuiResult.ok();
    }

}
