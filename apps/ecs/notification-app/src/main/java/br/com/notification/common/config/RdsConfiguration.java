package br.com.notification.common.config;

import io.awspring.cloud.jdbc.config.annotation.RdsInstanceConfigurer;
import io.awspring.cloud.jdbc.datasource.TomcatJdbcDataSourceFactory;
import org.springframework.context.annotation.Bean;

//@Configuration
public class RdsConfiguration {

    @Bean
    public RdsInstanceConfigurer rdsInstanceConfigurer() {
        return () -> {
            var dataSourceFactory = new TomcatJdbcDataSourceFactory();
            dataSourceFactory.setInitialSize(20);
            dataSourceFactory.setValidationQuery("SELECT 1 FROM DUAL");
            return dataSourceFactory;
        };
    }

}
