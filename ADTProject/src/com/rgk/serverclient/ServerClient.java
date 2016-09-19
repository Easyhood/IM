package com.rgk.serverclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerClient {
	public static void main(String[] args) {
		try {
			final ServerSocket server = new ServerSocket(12345);
			System.out.println("服务端打开"+new Date());
			
			new Thread(){
				public void run() {
					while(true){
						try {
							Socket socket = server.accept();
							System.out.println("客户端"+socket+"连接进来了");
							new MessageThread(socket).start();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				};
			}.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static class MessageThread extends Thread{
		Socket socket;
		private DataInputStream inputStream;
		public MessageThread(Socket socket) {
			this.socket = socket;
			try {
				inputStream = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			while(true){
				try {
					String result = inputStream.readUTF();
					System.out.println(result);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
