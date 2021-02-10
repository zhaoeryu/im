package cn.study.im.netty.attribute;

import cn.study.im.netty.session.Session;
import io.netty.util.AttributeKey;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
