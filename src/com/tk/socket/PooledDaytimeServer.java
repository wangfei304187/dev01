package com.tk.socket;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PooledDaytimeServer {

	public final static int PORT = 13;
	
	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(50);
		
		try(ServerSocket server = new ServerSocket(PORT)) {
			while(true) {
				try {
					Socket connection = server.accept();
					Callable<Void> task = new DaytimeTask(connection);
					pool.submit(task);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.err.println("Couldn't start server");
		}
	}
}

class DaytimeTask implements Callable<Void> {
	private Socket connection;

	DaytimeTask(Socket connection) {
		this.connection = connection;
	}
	
	@Override
	public Void call() throws Exception {
		try {
			Random rand = new Random();
			Writer out = new OutputStreamWriter(connection.getOutputStream());
			Date now = new Date();
			out.write(now.toString() + "\r\n");
			out.flush();
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			connection.close();
		}
		
		return null;
	}
	
	
}
