package com.mq.letter.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
//@Slf4j
//@EnableTransactionManagement
public class DruidDataSourceConfig implements EnvironmentAware {

    private static final Object DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";

    private Environment env;

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
    }

    /**
     * DataSource
     *
     * @return data source
     */
    @Bean("dataSource")
    @Primary
    public DataSource dataSource() {
        return buildDataSource(buildDbProperties("spring.datasource.mytest0."));
    }

    private Map<String, Object> buildDbProperties(String prefix) {
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, prefix);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", propertyResolver.getProperty("type"));
        map.put("driver-class-name", propertyResolver.getProperty("driver-class-name"));
        map.put("url", propertyResolver.getProperty("url"));
        map.put("username", propertyResolver.getProperty("username"));
        map.put("password", propertyResolver.getProperty("password"));
        return map;
    }

    /**
     * log DataSource
     *
     * @return data source
     */
//    @ConfigurationProperties(prefix = "")
//    @Bean("logDataSource")
//    public DataSource logDataSource() {
//        return buildDataSource(buildDbProperties("custom.datasource.mytest1."));
//    }

//    /**
//     * Dynamic data source.
//     *
//     * @return the data source
//     */
//    @Bean("dynamicDataSource")
//    public DataSource dynamicDataSource() {
//        DynamicDataSource dynamicRoutingDataSource = new DynamicDataSource();
//        Map<Object, Object> dataSourceMap = new HashMap<>(2);
//        dataSourceMap.put("bizDataSource", bizDataSoure());
//        dataSourceMap.put("logDataSource", logDataSource());
//        // Set master datasource as default
//        dynamicRoutingDataSource.setDefaultTargetDataSource(bizDataSoure());
//        // Set biz and log datasource as target datasource
//        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
//        // To put datasource keys into DataSourceContextHolder to judge if the datasource is exist
//        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());
//        return dynamicRoutingDataSource;
//    }

    /**
     * 创建DataSource
     *
     * @param type
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @return
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        try {
            Object type = dsMap.get("type");
            if (type == null)
                type = DATASOURCE_TYPE_DEFAULT;

            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();

            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


//    @Bean("transactionManager")
//    public PlatformTransactionManager bizTransactionManager(@Autowired @Qualifier("bizDataSource") DataSource bizDataSource) {
//        return new DataSourceTransactionManager(bizDataSource);
//    }

//    @Bean("logTransactionManager")
//    public PlatformTransactionManager logTransactionManager(@Autowired @Qualifier("logDataSource") DataSource logDataSource) {
//        return new DataSourceTransactionManager(logDataSource);
//    }

//    /**
//     * 为DataSource绑定更多数据
//     *
//     * @param dataSource
//     * @param env
//     */
//    private void dataBinder(DataSource dataSource, Environment env){
//        RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
//        //dataBinder.setValidator(new LocalValidatorFactory().run(this.applicationContext));
//        dataBinder.setConversionService(conversionService);
//        dataBinder.setIgnoreNestedProperties(false);//false
//        dataBinder.setIgnoreInvalidFields(false);//false
//        dataBinder.setIgnoreUnknownFields(true);//true
//        if(dataSourcePropertyValues == null){
//            Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
//            Map<String, Object> values = new HashMap<String, Object>(rpr);
//            // 排除已经设置的属性
//            values.remove("type");
//            values.remove("driver-class-name");
//            values.remove("url");
//            values.remove("username");
//            values.remove("password");
//            dataSourcePropertyValues = new MutablePropertyValues(values);
//        }
//        dataBinder.bind(dataSourcePropertyValues);
//    }

    /**
     * ServletRegistrationBean,
     *
     * @return
     * @see com.alibaba.druid.support.http.ResourceServlet
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean druid = new ServletRegistrationBean();
        druid.setServlet(new StatViewServlet());
        druid.setUrlMappings(Arrays.asList("/druid/*"));

        Map<String, String> params = new HashMap<>();
        params.put("loginUsername", "admin");
        params.put("loginPassword", "admin");
        druid.setInitParameters(params);
        return druid;
    }

    /**
     * @return
     * @see com.alibaba.druid.support.http.WebStatFilter
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean fitler = new FilterRegistrationBean();
        fitler.setFilter(new WebStatFilter());
        fitler.setUrlPatterns(Arrays.asList("/*"));
        fitler.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return fitler;
    }

}
