package cn.wecloud.layim.layui.model;

import cn.study.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
public class LayuiResult<T> implements Serializable {
    private static final long serialVersionUID = -6190689122701100762L;

    /**
     * 响应编码
     */
    private int code = 0;
    /**
     * 提示消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;
    private Integer pages = 0;
    private long total = 0;

    public Integer getPages() {
        return pages;
    }

    public LayuiResult() {
        super();
        this.code(ErrorCode.OK.getCode()).msg(ErrorCode.OK.getMessage());
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public long getTotal() {
        return total;
    }

    @JsonIgnore
    public boolean isOk() {
        return this.code == ErrorCode.OK.getCode();
    }

    public static LayuiResult ok() {
        return new LayuiResult().code(ErrorCode.OK.getCode()).msg(ErrorCode.OK.getMessage());
    }

    public static LayuiResult failed() {
        return new LayuiResult().code(ErrorCode.FAIL.getCode()).msg(ErrorCode.FAIL.getMessage());
    }

    public LayuiResult code(int code) {
        this.code = code;
        return this;
    }

    public LayuiResult msg(String msg) {
        this.msg = msg;
        return this;
    }

    public LayuiResult data(T data) {
        this.data = data;
        return this;
    }

    public LayuiResult pages(Integer pages) {
        this.pages = pages;
        return this;
    }

    public LayuiResult total(long total) {
        this.total = total;
        return this;
    }

    @Override
    public String toString() {
        return "LayuiResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
