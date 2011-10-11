package com.officedrop.web;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class PingServlet extends WebSocketServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8167061513415082134L;

	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String  protocol) {
		return new ServerWebSocket();
	}	
	
}