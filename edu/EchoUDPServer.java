package week8;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

public class EchoUDPServer {
	public static void main(String args[]) throws Exception {
		DatagramSocket socket = new DatagramSocket(8888);
		byte data[] = new byte[150];
		DatagramPacket packet = new DatagramPacket(data, data.length);

		while (true) {
			socket.receive(packet);

			byte packetData[] = packet.getData();
			int packetSize = packet.getLength();
			String s2 = new String(packetData, 0, packetSize);

			System.out.println(new Date() + "  " + packet.getAddress() + " : " + packet.getPort() + " " + s2);
			socket.send(packet);
		}
	}
}