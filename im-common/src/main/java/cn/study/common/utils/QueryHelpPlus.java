/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package cn.study.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.study.common.annotation.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2019-6-4 14:59:48
 */
@Slf4j
@SuppressWarnings({"unchecked", "all"})
public class QueryHelpPlus {

    public static <R, Q> QueryWrapper getPredicate(R obj, Q query) {
        QueryWrapper<R> queryWrapper = new QueryWrapper<R>();
        if (query == null) {
            return queryWrapper;
        }
        try {
            List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                Query q = field.getAnnotation(Query.class);
                if (q != null) {
                    String propName = q.propName();
                    String joinName = q.joinName();
                    String blurry = q.blurry();
                    String attributeName = isBlank(propName) ? field.getName() : propName;
                    attributeName = StrUtil.toUnderlineCase(attributeName);
                    Class<?> fieldType = field.getType();
                    Object val = field.get(query);
                    if (ObjectUtil.isNull(val) || "".equals(val)) {
                        continue;
                    }
                    Query.Join join = null;
                    // 模糊多字段
                    if (ObjectUtil.isNotEmpty(blurry)) {
                        String[] blurrys = blurry.split(",");
                        queryWrapper.and(wrapper -> {
                            for (int i=0;i< blurrys.length;i++) {
                                String column = StrUtil.toUnderlineCase(blurrys[i]);
                                //if(i!=0){
                                    wrapper.or();
                                //}
                                wrapper.like(column, val.toString());
                            }
                        });
                        continue;
                    }
                    /*if (ObjectUtil.isNotEmpty(joinName)) {
                        String[] joinNames = joinName.split(">");
                        for (String name : joinNames) {
                            switch (q.join()) {
                                case LEFT:
                                    if (ObjectUtil.isNotNull(join)) {
                                        join = join.join(name, JoinType.LEFT);
                                    } else {
                                        join = root.join(name, JoinType.LEFT);
                                    }
                                    break;
                                case RIGHT:
                                    if (ObjectUtil.isNotNull(join)) {
                                        join = join.join(name, JoinType.RIGHT);
                                    } else {
                                        join = root.join(name, JoinType.RIGHT);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }*/
                    String finalAttributeName = attributeName;
                    switch (q.type()) {
                        case EQUAL:
                            //queryWrapper.and(wrapper -> wrapper.eq(finalAttributeName, val));
                            queryWrapper.eq(attributeName, val);
                            break;
                        case GREATER_THAN:
                           queryWrapper.ge(finalAttributeName, val);
                            break;
                        case LESS_THAN:
                            queryWrapper.le(finalAttributeName, val);
                            break;
                        case LESS_THAN_NQ:
                           queryWrapper.lt(finalAttributeName, val);
                            break;
                        case INNER_LIKE:
                           queryWrapper.like(finalAttributeName, val);
                            break;
                        case LEFT_LIKE:
                           queryWrapper.likeLeft(finalAttributeName, val);
                            break;
                        case RIGHT_LIKE:
                           queryWrapper.likeRight(finalAttributeName, val);
                            break;
                        case IN:
                            if (CollUtil.isNotEmpty((Collection<Long>) val)) {
                               queryWrapper.in(finalAttributeName, (Collection<Long>) val);
                            }
                            break;
                        case NOT_EQUAL:
                           queryWrapper.ne(finalAttributeName, val);
                            break;
                        case NOT_NULL:
                           queryWrapper.isNotNull(finalAttributeName);
                            break;
                        case BETWEEN:
                            List<Object> between = new ArrayList<>((List<Object>) val);
                           queryWrapper.between(finalAttributeName, between.get(0), between.get(1));
                            break;
                        case UNIX_TIMESTAMP:
                            List<Object> UNIX_TIMESTAMP = new ArrayList<>((List<Object>)val);
                            if(!UNIX_TIMESTAMP.isEmpty()){
                                SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                long time1 = fm.parse(UNIX_TIMESTAMP.get(0).toString()).getTime()/1000;
                                long time2 = fm.parse(UNIX_TIMESTAMP.get(1).toString()).getTime()/1000;
                                queryWrapper.between(finalAttributeName, time1, time2);
                            }
                            break;
                        default:
                            break;
                    }
                }
                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return queryWrapper;
    }

    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
}
