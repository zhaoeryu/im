package cn.wecloud.layim.layui.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/17
 */
@Data
public class LayimChatlogVo implements Serializable {

    private String username;
    private String id;
    private String avatar;
    private Long timestamp;
    private String content;

    private static final long serialVersionUID = 3883815648315532383L;
}
