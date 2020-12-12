package Host;

import Mode.BatchProcessing;
import TCPConnection.*;

public class HybridNode {

	public static void main(String[] args) {
		// Wait for connection request from master node
		System.out.println("Hybrid Node start...");
		System.out.println("Node wait for activation...");
		BroadcastServer s = new BroadcastServer();
		s.receiveSearch(NODE_TYPE);
		while (true) {
			System.out.println("wait");
			DataReceiveServer drs = new DataReceiveServer();
			String command = drs.receiveData();
			String clientIP = drs.getClientIP();
			System.out.println(command+" "+clientIP);
			System.out.println(command.split("-")[0]);
			new BatchProcessing("txt").BPmain();
			new DataSendClient(clientIP, command.split("-")[0]).sendFile();
		}
	}

	private static final String NODE_TYPE = "Hybrid";
}
