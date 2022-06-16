package demo;

import java.net.*;
import java.io.*;

public class Server implements Runnable {
    private static BufferedReader din;
	private static PrintWriter dout;
	private static boolean closed = false;

    public static void main(String[] args) throws Exception {
        ServerSocket ss = null;
		try {
			ss = new ServerSocket(3334);
		} catch (IOException e) {
			System.out.println("Could not create server socket on port 3334. Quitting.");
			System.exit(-1);
		}
		
		Socket s = ss.accept();
		
		din = new BufferedReader(new InputStreamReader(s.getInputStream()));
		dout = new PrintWriter(s.getOutputStream(), true);
		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
		String str = "";
		new Thread(new Server()).start();
		while (!str.equals("stop")) {		
			str = br.readLine();
			/* Write to socket */
			dout.println(str);
			dout.flush();
		}
		System.out.println("closing");
		din.close();
		s.close();
		ss.close();
		closed = true;
		System.exit(0);
    }

    public void run() {
		/*
		 * Keep on reading from the socket till we receive "Bye" from the
		 * server. Once we received that then we want to break.
		 */
		String responseLine;
		try {
			System.out.println("Waiting");
			while ((responseLine = din.readLine()) != null) {
				System.out.println(responseLine);
				if (responseLine.indexOf("*** Bye") != -1)
					break;
				if (closed)
					break;
			}
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
	}
    
}
