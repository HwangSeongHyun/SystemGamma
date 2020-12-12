package TCPConnection;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DataReceiveServer implements Server {
	private static final int PORT = 11000;
	private byte[] msg = new byte[COMMAND_BUFFER_SIZE];
	private String clientIP;

	public String receiveData() {
		String line = "";
		try {
			DatagramPacket dp = new DatagramPacket(msg, msg.length);
			DatagramSocket ds = new DatagramSocket(PORT);
			ds.receive(dp);
			line = new String(msg, 0, msg.length);
			clientIP = dp.getAddress().getHostAddress();
			ds.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	public void receiveFile() {
		try {
			DatagramSocket ds = new DatagramSocket(8888);
			File file = null;
			
			DataOutputStream dos = null;
			while (true) {
				DatagramPacket dp = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				ds.receive(dp);
				String str = new String(dp.getData()).trim();
				if (str.equals("start")) {
					dp = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
					ds.receive(dp);
					str = new String(dp.getData()).trim();
					file = new File("C:\\SystemGamma\\ProcessingResult\\" + str);
					dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				} else if (str.equals("end")) {
					dos.close();
					ds.close();
					break;
				} else {
					dos.write(str.getBytes(), 0, str.getBytes().length);
				}
			}
		}catch(Exception e) {

		}

	}

	public String getClientIP() {
		return this.clientIP;
	}
}
