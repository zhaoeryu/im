package cn.study.im.mvc.domain.vo;

import lombok.Data;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
@Data
public class LayimConfig {

    /** 开启聊天工具栏音频 */
    private Boolean isAudio = false;
    /** 开启聊天工具栏视频 */
    private Boolean isVideo = false;
    /** 是否简约模式（若开启则不显示主面板） */
    private Boolean brief = false;
    /** 自定义主面板最小化时的标题 */
    private String title = "WebIM";
    /** 主面板相对浏览器右侧距离 */
    private String right;
    /** 聊天面板最小化时相对浏览器右侧距离 */
    private String minRight;
    /** 是否开启好友 */
    private Boolean isfriend = true;
    /** 是否开启群组 */
    private Boolean isgroup = true;
    /** 是否始终最小化主面板 */
    private Boolean min = false;
    /** 是否开启桌面消息提醒 */
    private Boolean notice = false;
    /**
     * 设定消息提醒的声音文件（所在目录：./layui/css/modules/layim/voice/）
     * 若不开启，设置 false 即可
     */
    private String voice = "default.mp3";
    /** 可允许的消息最大字符长度 */
    private Integer maxLength;
    /** 是否开启消息盒子 */
    private Boolean msgbox = false;
    /** 是否开启查找 */
    private Boolean find = false;
    /** 是否开启聊天记录 */
    private Boolean chatLog = true;
}
