package com.tk.socket;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

public class MultithreadedDaytimeServer {

	public final static int PORT = 13;
	
	public static void main(String[] args) {
		
		try(ServerSocket server = new ServerSocket(PORT)) {
			while(true) {
				try {
					Socket connection = server.accept();
					Thread task = new DaytimeThread(connection);
					task.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.err.println("Couldn't start server");
		}
	}
}

class DaytimeThread extends Thread {
	private Socket connection;

	DaytimeThread(Socket connection) {
		this.connection = connection;
	}
	
	@Override
	public void run() {
		try {
			Random rand = new Random();
			Thread.sleep(rand.nextInt(500));
			Writer out = new OutputStreamWriter(connection.getOutputStream());
			Date now = new Date();
			out.write(now.toString() + "\r\n");
			out.flush();
		} catch (IOException e) {
			System.err.println(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (IOException e) {}
		}
	}
	
	
}
