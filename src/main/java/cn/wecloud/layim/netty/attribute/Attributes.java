package cn.wecloud.layim.netty.attribute;

import cn.wecloud.layim.netty.session.Session;
import io.netty.util.AttributeKey;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
