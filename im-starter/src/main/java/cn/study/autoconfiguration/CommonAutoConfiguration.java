package cn.study.autoconfiguration;

import cn.study.common.dozer.service.EJBGenerator;
import cn.study.common.mybatis.MetaHandler;
import cn.study.common.utils.SpringContextHolder;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/05/24
 */
@Slf4j
@Configuration
public class CommonAutoConfiguration {

    @Bean
    @ConditionalOnClass(value = PaginationInterceptor.class)
    public PaginationInterceptor paginationInterceptor(){
        log.info("load plugin [pagination]");
        return new PaginationInterceptor();
    }

    @Bean
    @ConditionalOnClass(value = MetaObjectHandler.class)
    public MetaObjectHandler metaHandler(){
        return new MetaHandler();
    }

    @Bean
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }

    @Bean
    public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(@Value("classpath*:dozer/*.xml" ) Resource[] resources) throws Exception {
        final DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean = new DozerBeanMapperFactoryBean();
        dozerBeanMapperFactoryBean.setMappingFiles(resources);
        return dozerBeanMapperFactoryBean;
    }
    @Bean
    @Lazy(true)
    public EJBGenerator ejbGenerator(){
        return new EJBGenerator();
    }


}
