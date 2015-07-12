package com.tk.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.ParseException;

public class DaytimeClient {

	public static void main(String[] args) throws IOException, ParseException {
		for(int i=0; i<1000; i++) {
			new Thread("therad-" + i) {
				public void run() {
					Socket socket = null;
					try {
						socket = new Socket("localhost", 13);
						socket.setSoTimeout(300000); // 300s
						InputStream is = socket.getInputStream();
						StringBuilder sb = new StringBuilder();
						InputStreamReader r = new InputStreamReader(is);
						for(int c = r.read(); c != -1; c = r.read()) {
							sb.append((char)c);
						}
						System.out.println(this.getName() + " -- " + sb.toString());
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if(socket != null) {
							try {
								socket.close();
							} catch (IOException e) {}   
						}
					}
				}
			}.start();
		}
	}

}
