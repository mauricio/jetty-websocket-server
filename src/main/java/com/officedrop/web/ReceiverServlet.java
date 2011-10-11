package com.officedrop.web;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class ReceiverServlet extends WebSocketServlet implements WebSocket.OnTextMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
		return this;
	}

	@Override
	public void onClose(int closeCode, String message) {
		
	}

	@Override
	public void onOpen(Connection connection) {
		
	}

	@Override
	public void onMessage(String message) { 
		
		String[] values = message.split( "@" );
		ConnectionManager.getInstance().sendMessage(values[0], values[1]);
		
	}

}
