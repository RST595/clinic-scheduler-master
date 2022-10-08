package org.saturn.clinicscheduler;

import org.saturn.clinicscheduler.config.Config;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class App extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {Config.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("RestServlet",
                new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.scan("org.aston");
        context.register(Config.class, org.springdoc.webmvc.ui.SwaggerConfig.class,
                org.springdoc.core.SwaggerUiConfigProperties.class, org.springdoc.core.SwaggerUiOAuthProperties.class,
                org.springdoc.webmvc.core.SpringDocWebMvcConfiguration.class,
                org.springdoc.core.MultipleOpenApiSupportCondition.class,
                org.springdoc.core.SpringDocConfiguration.class, org.springdoc.core.SpringDocConfigProperties.class,
                org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class);

        return context;
    }

}