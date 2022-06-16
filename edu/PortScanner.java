package week8;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class PortScanner {
	public static void main(String args[]) throws Exception {
		InetAddress address = InetAddress.getByName("localhost");
		scan(address);

	}

	public static void scan(InetAddress remote) {
		String hostname = remote.getHostName();
		for (int port = 6785; port < 6790; port++) {
//		for (int port = 0; port < 65536; port++) {
			System.out.println("Scanning port" + port);
			try {
				Socket s = new Socket(remote, port);
				System.out.println("There is a server on port " + port + " of " + hostname);
				s.close();
			} catch (IOException e) {
				// The remote host is not listening on this port
			}
		}
	}

}
