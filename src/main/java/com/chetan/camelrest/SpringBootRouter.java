package com.chetan.camelrest;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SpringBootRouter extends RouteBuilder {

    public static final String LOG_NAME = "com.chetan.camelrest";

    @Override
    public void configure() {

        //region camel restRoutes
        restConfiguration()
                .clientRequestValidation(true);

        rest().get("/get")//http://localhost:8080/rest/camel
                .produces("text/plain").route().routeId("getRestEndPoint_RouteId")
                .transform().constant("This is rest camel get")
                .log(LoggingLevel.INFO, LOG_NAME, "get body:----- ${body}")//.end();
                .to("direct:restContinue");
        //endregion camel restRoutes

        from("direct:restContinue").routeId("forLogging")
                .log(LoggingLevel.INFO, LOG_NAME, "logging body from RouteId forLogging:----- ${body}");
    }

    @Bean
    ServletRegistrationBean<CamelHttpTransportServlet> servletRegistrationBean() {
        ServletRegistrationBean<CamelHttpTransportServlet> servlet =
                new ServletRegistrationBean<>( new CamelHttpTransportServlet(), "/camel/*");
        servlet.setName("CamelServlet");
        return servlet;
    }
}
