package cn.wecloud.layim.netty.utils;

import cn.wecloud.layim.netty.attribute.Attributes;
import cn.wecloud.layim.netty.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
@Slf4j
public class SessionUtil {

    // userId -> Channel
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    // groupId -> ChannelGroup
    private static final Map<String, ChannelGroup> groupIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Channel channel, Session session){
        userIdChannelMap.put(session.getId(),channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unbindSession(Channel channel){
        if (hasLogin(channel)){
            Session session = getSession(channel);
            userIdChannelMap.remove(session.getId());
            channel.attr(Attributes.SESSION).set(null);
            log.info("[{}]离线",session.getUsername());
        }
    }

    public static Session getSession(Channel channel){
        return channel.attr(Attributes.SESSION).get();
    }

    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId,ChannelGroup channelGroup){
        groupIdChannelMap.put(groupId,channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId){
        return groupIdChannelMap.get(groupId);
    }

    // 测试方法,记得删除
    @Deprecated
    public static ChannelGroup initChannelGroup(String groupId,ChannelGroup channelGroup){
        userIdChannelMap.keySet().forEach(item -> channelGroup.add(userIdChannelMap.get(item)));
        groupIdChannelMap.put(groupId,channelGroup);
        return channelGroup;
    }
}
