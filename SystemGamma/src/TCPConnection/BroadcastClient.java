package TCPConnection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class BroadcastClient implements Client{
	private static final int SEND_PORT = 9000;
	private static final int RECEIVE_PORT = 9300;
	private static final String IP = "255.255.255.255";
	public static HashMap<InetAddress, String> nodeList = new HashMap<InetAddress, String>();
	
	public HashMap<InetAddress, String> searchActiveNode() {
		RecvServer rm = new RecvServer();
        rm.start();
        //1.5초간 대기
        for (int i = 0; i < 2; i++) {
            try {
                new SearchDevice(IP, SEND_PORT).start();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }
 
        rm.closeServer();
        return nodeList;
    }
 
    static class SearchDevice extends Thread {
        InetAddress ia;
        int port;
 
        public SearchDevice(String IPaddr, int Port) {
            try {
                ia = InetAddress.getByName(IPaddr);
                port = Port;
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
 
        public void run() {
            String msg = "Default Message";
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
 
    static class RecvServer extends Thread {
        String text;
        String clientIp;
        DatagramPacket dp;
        DatagramSocket ds;
        Object lock = new Object();
 
        public void closeServer() {
            synchronized (lock) {
                ds.close();
            }
        }
 
        public void run() {
            byte[] message = new byte[1000];
            dp = new DatagramPacket(message, message.length);
            try {
                ds = new DatagramSocket(RECEIVE_PORT);
                ds.receive(dp);
                synchronized (lock) {
                    text = new String(message, 0, dp.getLength());
                    ds.close();
                    
                    clientIp = dp.getAddress().getHostAddress();
                    nodeList.put(dp.getAddress(), text);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
 
        }
    }

}
