package week8;
import java.net.*;
import java.io.*;

public class MultiThreadedServer {
	private static boolean closed = false;
	public static void main(String[] args) throws Exception {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(3334);
		} catch (IOException ioe) {
			System.out.println("Could not create server socket on port 3334. Quitting.");
			System.exit(-1);
		}

		while (!closed) {
			Socket s = ss.accept();
			Thread clientThread = new Thread (new MultiThreadedServer().new ClientServiceThread(s));
			clientThread.start();
		}
		ss.close();
		System.out.println("Server Stopped");
	}

	public class ClientServiceThread implements Runnable {
		Socket s;
		boolean clientStop = false;
		ClientServiceThread(Socket s) {
			this.s = s;
		}

		public void run() {
			BufferedReader din = null;
			PrintWriter dout = null;
			System.out.println("Accepted Client Address - " + s.getInetAddress().getHostName());
			try {
				din = new BufferedReader(new InputStreamReader(s.getInputStream()));
				dout = new PrintWriter(s.getOutputStream(), true);

				while (! clientStop) {
					String clientCommand = din.readLine();
					System.out.println("Client Says :" + clientCommand);

					if (clientCommand.equalsIgnoreCase("quit")) {
						clientStop = true;
						System.out.print("Stopping client thread for client : ");
					} else if (clientCommand.equalsIgnoreCase("end")) {
						clientStop = true;
						System.out.print("Stopping client thread for client : ");
						closed = true;
					} else {
						dout.println("Server Says : " + clientCommand);
						dout.flush();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					din.close();
					dout.close();
					s.close();
					System.out.println("...Stopped");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}