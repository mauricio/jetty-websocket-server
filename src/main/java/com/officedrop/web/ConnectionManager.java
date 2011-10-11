package com.officedrop.web;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ConnectionManager {

	private static final ConnectionManager INSTANCE = new ConnectionManager();
	
	private Map<String, List<ServerWebSocket>> connections = new HashMap<String, List<ServerWebSocket>>();
	private ReadWriteLock readWriteLock;
	private Lock readLock;
	private Lock writeLock;
	
	private ConnectionManager() {
		this.readWriteLock = new ReentrantReadWriteLock();
		this.readLock = this.readWriteLock.readLock();
		this.writeLock = this.readWriteLock.writeLock();
	}
	
	public void removeWebSocket( ServerWebSocket connection ) {
		
		this.writeLock.lock();
		
		try {
			
			List<ServerWebSocket> sockets = this.connections.get( connection.getConnection().getProtocol() );
			sockets.remove(connection);
			
		} finally {
			this.writeLock.unlock();
		}
		
	}
	
	public void sendMessage( String protocol, String message ) {
		
		this.readLock.lock();
		
		try {
			
			List<ServerWebSocket> sockets = this.connections.get( protocol );
			
			if ( sockets != null && !sockets.isEmpty() ) {
				
				//System.out.printf( "Sending message %s to clients %s (%s)%n", protocol, message, sockets.size() );
				
				for ( ServerWebSocket socket : sockets ) {
					try {
						socket.getConnection().sendMessage(message);	
					} catch ( Exception e ) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
					
				}
			}
			
		} finally {
			this.readLock.unlock();
		}
		
	}
	
	public void addWebSocket( ServerWebSocket connection  ) {
		this.writeLock.lock();
		
		try {
			
			List<ServerWebSocket> sockets = this.connections.get( connection.getConnection().getProtocol() );
			
			if ( sockets == null ) {
				sockets = new LinkedList<ServerWebSocket>();
				this.connections.put( connection.getConnection().getProtocol(), sockets );
			}
			
			sockets.add( connection );
			
		} finally {
			this.writeLock.unlock();
		}
	}
	
	public static ConnectionManager getInstance() {
		return INSTANCE;
	}
	
}
