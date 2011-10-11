package com.officedrop.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class JettyStarter {

	public static void main( String[] args ) throws Exception {
		
		System.out.println("Starting jetty server");
		
		Server server = new Server();
		
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMinThreads(10);
		threadPool.setMaxThreads(20);
		threadPool.setDetailedDump(false);
		
		server.setThreadPool( threadPool );
		
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setHost( "localhost" );
		connector.setPort( 8080 );
		connector.setMaxIdleTime( Integer.MAX_VALUE );
		connector.setAcceptors( 2 );
		connector.setStatsOn(false);
		connector.setLowResourcesConnections( 20000 );		
		
		server.addConnector(connector);
		
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		handler.setContextPath("/");		
		handler.addServlet( PingServlet.class , "/ping");
		handler.addServlet( ReceiverServlet.class , "/ping_producer");
		
		server.setHandler(handler);
		server.start();
		
		System.out.println("Jetty server started");

		server.join();				
		
	}
	
}
