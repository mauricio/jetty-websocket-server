package com.officedrop.web;

import org.eclipse.jetty.websocket.WebSocket;

public class ServerWebSocket implements WebSocket.OnTextMessage {

	private Connection connection;

	public Connection getConnection() {
		return connection;
	}
	
	@Override
	public void onClose(int closeCode, String message) {
		//System.out.printf("Closed with code %s and message %s%n", closeCode, message);
		ConnectionManager.getInstance().removeWebSocket(this);
	}

	@Override
	public void onOpen(Connection connection) {
		//System.out.printf("Opened with connection %s%n", connection);
		this.connection = connection;
		ConnectionManager.getInstance().addWebSocket(this);
	}

	@Override
	public void onMessage(String message) {
		//System.out.printf("Received message on server %s%n", message);
		try {
			this.connection.sendMessage(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
