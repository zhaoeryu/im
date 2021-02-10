package cn.study.im.mvc.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.study.common.base.BaseServiceImpl;
import cn.study.im.enums.LayimApplyStatusEnum;
import cn.study.im.enums.LayimApplyTypeEnum;
import cn.study.im.model.LayuiResult;
import cn.study.im.mvc.domain.entity.ApplyAudit;
import cn.study.im.mvc.domain.entity.UserGroup;
import cn.study.im.mvc.domain.entity.UserInfo;
import cn.study.im.mvc.domain.vo.LayimApply;
import cn.study.im.mvc.domain.vo.LayimUserVo;
import cn.study.im.mvc.mapper.ApplyAuditMapper;
import cn.study.im.mvc.service.ApplyAuditService;
import cn.study.im.mvc.service.UserGroupService;
import cn.study.im.mvc.service.UserInfoService;
import cn.study.im.security.SecurityHelper;
import cn.study.im.util.TimeAgoUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ApplyAuditServiceImpl extends BaseServiceImpl<ApplyAuditMapper, ApplyAudit> implements ApplyAuditService {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserGroupService userGroupService;

    @Override
    public LayuiResult getMsgList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        String userId = SecurityHelper.getUserId();
        List<ApplyAudit> list = list(new LambdaQueryWrapper<ApplyAudit>()
                .eq(ApplyAudit::getApplyUserId, userId)
                .or()
                .eq(ApplyAudit::getAuditUserId, userId)
                .orderByDesc(ApplyAudit::getUpdateTime));
        List<String> uids = list.stream().map(ApplyAudit::getApplyUserId).collect(Collectors.toList());
        uids.addAll(list.stream().map(ApplyAudit::getAuditUserId).collect(Collectors.toList()));
        Map<String, UserInfo> userMap = new HashMap<>();
        Map<String, List<ApplyAudit>> groupMap = list.stream().collect(Collectors.groupingBy(ApplyAudit::getType));
        List<String> groupIds = Optional.ofNullable(groupMap.get(LayimApplyTypeEnum.GROUP.getValue())).orElse(new ArrayList<>())
                .stream()
                .map(ApplyAudit::getGroupId)
                .collect(Collectors.toList());
        Map<String, UserGroup> groupTable = new HashMap<>();
        if (CollectionUtil.isNotEmpty(groupIds)){
            userGroupService.list(new LambdaQueryWrapper<UserGroup>().in(UserGroup::getId,groupIds))
                    .forEach(item -> groupTable.put(item.getId(),item));
        }

        if (CollectionUtil.isNotEmpty(uids)){
            List<String> newUids = uids.stream().distinct().collect(Collectors.toList());
            List<UserInfo> userInfos = userInfoService.list(new LambdaQueryWrapper<UserInfo>().in(UserInfo::getId, newUids));
            userInfos.forEach(item -> userMap.put(item.getId(),item));
        }

        List<LayimApply> collect = list.stream()
                .map(item -> {
                    LayimApply layimApply = new LayimApply();
                    layimApply.setId(item.getId());
                    layimApply.setContent(parseContent(item,userMap,groupTable));
                    layimApply.setUid(userId);
                    if (StrUtil.equalsAnyIgnoreCase(userId,item.getAuditUserId())){
                        if (LayimApplyStatusEnum.APPLY.getValue() == item.getStatus()){
                            layimApply.setFrom(item.getApplyUserId());
                        }
                    }
                    layimApply.setFromGroup(item.getGroupId());
                    layimApply.setType(item.getType());
                    layimApply.setRemark(item.getRemark());
//                    layimApply.setHref();
                    layimApply.setRead(item.getReadFlag());
                    layimApply.setTime(TimeAgoUtils.format(item.getUpdateTime()));
                    UserInfo userInfo = userMap.get(StrUtil.equals(userId, item.getApplyUserId()) ? item.getAuditUserId() : item.getApplyUserId());
                    if (null != userInfo){
                        LayimUserVo userVo = new LayimUserVo();
                        userVo.setId(userInfo.getId());
                        userVo.setUsername(userInfo.getUsername());
                        userVo.setStatus(userInfo.getStatus());
                        userVo.setSign(userInfo.getSign());
                        userVo.setAvatar(userInfo.getAvatar());
                        layimApply.setUser(userVo);
                    }
                    if (LayimApplyTypeEnum.GROUP.getValue().equals(item.getType())){
                        layimApply.setGroup(groupTable.get(item.getGroupId()));
                    }
                    return layimApply;
                }).collect(Collectors.toList());

        // 标记消息已读
        List<Integer> unReadIds = list.stream()
                // 审核者读消息 改
                .filter(item -> userId.equals(item.getAuditUserId()))
                // 未读消息 改
                .filter(item -> item.getReadFlag() == 0)
                .map(ApplyAudit::getId)
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(unReadIds)){
            update(new LambdaUpdateWrapper<ApplyAudit>().in(ApplyAudit::getId,unReadIds)
            .set(ApplyAudit::getReadFlag,1));
        }

        PageInfo<ApplyAudit> pageInfo = new PageInfo<>(list);
        return LayuiResult.ok().data(collect).pages(pageInfo.getPages());
    }

    private String parseContent(ApplyAudit item,Map<String,UserInfo> userMap,Map<String,UserGroup> groupMap){
        String message = "";
        String mineId = SecurityHelper.getUserId();
        if (StrUtil.equalsAnyIgnoreCase(mineId,item.getApplyUserId())){
            // 我申请的
            if (LayimApplyStatusEnum.APPLY.getValue() == item.getStatus()){
                // 待审核
                if (LayimApplyTypeEnum.FRIEND.getValue().equals(item.getType())){
                    // 好友
                    message = "已发出添加["+userMap.get(item.getAuditUserId()).getUsername()+"]的申请";
                }else if(LayimApplyTypeEnum.GROUP.getValue().equals(item.getType())){
                    // 群
                    message = "已发出加入群["+groupMap.get(item.getGroupId()).getGroupname()+"]的申请";
                }
            }else if(LayimApplyStatusEnum.AGREE.getValue() == item.getStatus()){
                // 已同意
                if (LayimApplyTypeEnum.FRIEND.getValue().equals(item.getType())){
                    // 好友
                    message = "["+ userMap.get(item.getAuditUserId()).getUsername() +"]同意添加您为好友";
                }else if(LayimApplyTypeEnum.GROUP.getValue().equals(item.getType())){
                    // 群
                    message = "["+groupMap.get(item.getGroupId()).getGroupname()+"]同意您入群";
                }
            }else if(LayimApplyStatusEnum.DENY.getValue() == item.getStatus()){
                // 已拒绝
                if (LayimApplyTypeEnum.FRIEND.getValue().equals(item.getType())){
                    // 好友
                    message = "["+ userMap.get(item.getAuditUserId()).getUsername() +"]拒绝添加您为好友";
                }else if(LayimApplyTypeEnum.GROUP.getValue().equals(item.getType())){
                    // 群
                    message = "["+groupMap.get(item.getGroupId()).getGroupname()+"]拒绝您加入群";
                }
            }
        }else{
            // 需要我审核的
            if (LayimApplyStatusEnum.APPLY.getValue() == item.getStatus()){
                // 待审核
                if (LayimApplyTypeEnum.FRIEND.getValue().equals(item.getType())){
                    // 好友
                    message = "申请添加你为好友";
                }else if(LayimApplyTypeEnum.GROUP.getValue().equals(item.getType())){
                    // 群
                    message = "申请加入群["+groupMap.get(item.getGroupId()).getGroupname()+"]";
                }
            }else if(LayimApplyStatusEnum.AGREE.getValue() == item.getStatus()){
                // 已同意
                if (LayimApplyTypeEnum.FRIEND.getValue().equals(item.getType())){
                    // 好友
                    message = "已同意["+userMap.get(item.getApplyUserId()).getUsername()+"]的添加好友请求";
                }else if(LayimApplyTypeEnum.GROUP.getValue().equals(item.getType())){
                    // 群
                    message = "已同意["+userMap.get(item.getApplyUserId()).getUsername()+"]的入群请求";
                }
            }else if(LayimApplyStatusEnum.DENY.getValue() == item.getStatus()){
                // 已拒绝
                if (LayimApplyTypeEnum.FRIEND.getValue().equals(item.getType())){
                    // 好友
                    message = "已拒绝["+userMap.get(item.getApplyUserId()).getUsername()+"]的添加好友请求";
                }else if(LayimApplyTypeEnum.GROUP.getValue().equals(item.getType())){
                    // 群
                    message = "已拒绝["+userMap.get(item.getApplyUserId()).getUsername()+"]的入群请求";
                }
            }
        }

        return message;
    }
}
