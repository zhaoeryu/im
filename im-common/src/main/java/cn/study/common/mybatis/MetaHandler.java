/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package cn.study.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

@Slf4j
public class MetaHandler implements MetaObjectHandler {

    /**
     * 新增数据执行
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            Date now = new Date();
            if (metaObject.hasSetter("createTime")) {
                log.debug("自动插入 createTime");
                this.setFieldValByName("createTime", now, metaObject);
            }
            if (metaObject.hasSetter("updateTime")) {
                log.debug("自动插入 updateTime");
                this.setFieldValByName("updateTime", now, metaObject);
            }
//            if (metaObject.hasSetter("delFlag")) {
//                log.debug("自动插入 delFlag");
//                this.setFieldValByName("delFlag", 1, metaObject);
//            }
        } catch (Exception e) {
            log.error("自动注入失败:{}", e);
        }
    }

    /**
     * 更新数据执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            Date now = new Date();
            if (metaObject.hasSetter("updateTime")) {
                log.debug("自动插入 updateTime");
                this.setFieldValByName("updateTime", now, metaObject);
            }
            if (metaObject.hasSetter("createTime")) {
                log.debug("自动插入 createTime");
                this.setFieldValByName("createTime", null, metaObject);
            }
//            if (metaObject.hasSetter("delFlag")) {
//                log.debug("自动插入 delFlag");
//                this.setFieldValByName("delFlag", null, metaObject);
//            }
        } catch (Exception e) {
            log.error("自动注入失败:{}", e);
        }
    }

}
