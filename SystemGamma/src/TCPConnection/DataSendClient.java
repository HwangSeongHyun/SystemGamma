package TCPConnection;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DataSendClient implements Client {
	private String ip;
	private static final int PORT = 11000;
	private String mode;
	private String ip2;
	
	public DataSendClient(String ip, String mode) {
		this.ip = ip.substring(1);
		this.ip2 = ip;
		this.mode = mode;
	}
	
	public void requestData(String mode, String option) {
		try {
			DatagramSocket ds = new DatagramSocket();
			String msg = mode+"-"+option;
			byte[] msgbuff = msg.getBytes();
			DatagramPacket dp = new DatagramPacket(msgbuff, msgbuff.length, 
					InetAddress.getByName(this.ip), PORT);
			ds.send(dp);
			ds.close();
		} catch(UnknownHostException ue) {
			ue.printStackTrace();
		} catch(IOException ie) {
			ie.printStackTrace();
		}
	}
	
	public void sendFile() {
		DatagramSocket ds = null;
        File f = new File("C:\\SystemGamma\\Result\\count_result.txt");
        if (!f.exists()) {
            System.exit(0);
        }
        try {
            ds = new DatagramSocket();
            InetAddress ia = InetAddress.getByName(ip2);
            String str = "start";
            DatagramPacket dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ia, 8888);
 
            ds.send(dp);
            String data = "count_result.txt";
            dp = new DatagramPacket(data.getBytes(), data.getBytes().length, ia, 8888);
            ds.send(dp);
            DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));
            FileInputStream fi = new FileInputStream(f);
 
            byte[] by = new byte[BUFFER_SIZE];
            int count = 0;
            while (true) {
                int x = dis.read(by, 0, by.length);
                if (x == -1)
                    break;
                dp = new DatagramPacket(by, x, ia, 8888);
                ds.send(dp);
                count++;
            }
            str = "end";
            dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ia, 8888);
            ds.send(dp); 
            dis.close();
            ds.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
}
