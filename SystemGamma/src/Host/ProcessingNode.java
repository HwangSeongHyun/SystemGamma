package Host;

import TCPConnection.*;

public class ProcessingNode {

	public static void main(String[] args) {
		// Wait for connection request from master node
		while (true) {
			System.out.println("Processing Node start...");
			System.out.println("Node wait for activation...");
			BroadcastServer s = new BroadcastServer();
			s.receiveSearch(NODE_TYPE);
			System.out.println("wait");
			DataReceiveServer drs = new DataReceiveServer();
			String command = drs.receiveData();
			String clientIP = drs.getClientIP();
			System.out.println(command+" "+clientIP);
		}
	}

	private final static String NODE_TYPE = "Processing";

}
