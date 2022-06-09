package org.gabrielyget.cloudnative.Tema05.Config;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.gabrielyget.cloudnative.Tema05.Builders.JsonResponseBuilder;
import org.gabrielyget.cloudnative.Tema05.Services.TollService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.ws.rs.ext.RuntimeDelegate;
import java.util.Arrays;

@Configuration
public class AppConfig {
    @Bean(destroyMethod = "shutdown")
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean
    @DependsOn("cxf")
    public Server jaxRsServer() {
        final JAXRSServerFactoryBean factoryBean = RuntimeDelegate.getInstance().createEndpoint(tollRestService(), JAXRSServerFactoryBean.class);
        factoryBean.setServiceBean(Arrays.asList(tollRestService()));
        factoryBean.setProviders(Arrays.asList(jsonProvider()));
        return factoryBean.create();
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }

    @Bean
    public TollService tollRestService() {
        return new TollService();
    }

    @Bean
    public JsonResponseBuilder jsonResponseBuilder() {
        return new JsonResponseBuilder();
    }
}