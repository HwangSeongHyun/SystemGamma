package Host;

import TCPConnection.*;

public class StorageNode {

	public static void main(String[] args) {
		// Wait for connection request from master node
		while (true) {
			System.out.println("Storage Node start...");
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

	private static final String NODE_TYPE = "Storage";

}
