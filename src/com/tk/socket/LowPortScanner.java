package com.tk.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class LowPortScanner {

	public static void main(String[] args) {
		String host = "localhost";
		for(int i=1; i<1024; i++) {
			Socket s = null;
			try {
				s = new Socket(host, i);
				System.out.println("There is a server on port " + i + " of " + host);
			} catch (UnknownHostException e) {
			} catch (IOException e) {
			} finally {
				if(s != null) {
					try {
						s.close();
					} catch (IOException e) {}
				}
			}
			
		}
	}
}
