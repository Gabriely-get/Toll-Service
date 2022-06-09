package org.gabrielyget.cloudnative.Tema05.Engine;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.gabrielyget.cloudnative.Tema05.Config.AppConfig;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class CXFRunner {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8000);

        final ServletHolder servletHolder = new ServletHolder(new CXFServlet());
        final ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/");
        servletContextHandler.addServlet(servletHolder, "/rest/*");
        servletContextHandler.addEventListener(new ContextLoaderListener());

        servletContextHandler.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
        servletContextHandler.setInitParameter("contextConfigLocation", AppConfig.class.getName());
        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}
