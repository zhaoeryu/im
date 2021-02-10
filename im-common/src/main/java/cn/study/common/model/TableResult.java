package cn.study.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/06
 */
public class TableResult<T> implements Serializable {

    private List<T> content;
    private Long totalElements;

    public TableResult() {
    }

    public TableResult(List<T> content, Long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private static final long serialVersionUID = -2773594811698920995L;
}
