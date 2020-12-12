package TCPConnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BroadcastServer implements Server{
	private static final int SEND_PORT = 9300;
	private static final int RECEIVE_PORT = 9000;
	
	public void receiveSearch(String nodeType) {
        String serverIp;
        DatagramPacket dp;
        byte[] message = new byte[1000];
        dp = new DatagramPacket(message, message.length);
        try {
            DatagramSocket ds = new DatagramSocket(RECEIVE_PORT);
            ds.receive(dp);
            ds.close();
            serverIp = dp.getAddress().getHostAddress();
            new SearchDevice(serverIp, SEND_PORT, nodeType).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
 
    static class SearchDevice extends Thread {
        InetAddress ia;
        int port;
        String msg;
 
        SearchDevice(String IPaddr, int Port, String nodeType) {
            try {
                ia = InetAddress.getByName(IPaddr);
                port = Port;
                msg = nodeType;
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
 
        public void run() {
            try {
                DatagramSocket ds = new DatagramSocket();
                int length = msg.length();
                byte[] msgbyte = msg.getBytes();
                DatagramPacket dp = new DatagramPacket(msgbyte, length, ia, port);
                ds.send(dp);
                ds.close();
 
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
