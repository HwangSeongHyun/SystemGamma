//package Mode;
//
//import java.io.File;
//import java.util.Scanner;
//
//import TCPConnection.Client;
//import TCPConnection.Server;
//
//public class MigrationMerging extends WordCountProcessing {
//	
//	public MigrationMerging(String option) {
//		this.option = option;
//	}
//	
//	public void MMmain() {
//		File dir = new File("text");
//		File files[] = dir.listFiles();
//		
//		start = System.currentTimeMillis();
//		switch(option) {
//		case "One Master One Node":
//			for (int i=1 ; i<files.length/2 ; i++) {
//				Scanner fileSc = fileOpen("text//"+i+".txt");
//				WordCount(fileSc);
//				fileSc.close();
//			}
//			read = System.currentTimeMillis();
//			writeCount(1);
//			Client c1 = new Client("203.249.22.142", 9999);
//			c1.requestData(4, 1);
//			c1.sendData("count.txt");
//			Server s1 = new Server(9999);
//			s1.receiveFile("node1.txt");
//			readCount("node1.txt");
//			break;
//		case "One Master Two Node":
//			for (int i=1 ; i<files.length/3 ; i++) {
//				Scanner fileSc = fileOpen("text//"+i+".txt");
//				WordCount(fileSc);
//				fileSc.close();
//			}
//			read = System.currentTimeMillis();
//			writeCount(1);
//			Client c2 = new Client("203.249.22.142", 9999);
//			c2.requestData(4, 2);
//			c2.sendData("count.txt");
//			Server s2 = new Server(11111);
//			s2.receiveFile("node2.txt");
//			wait = System.currentTimeMillis();
//			readCount("node2.txt");
//			break;
//		}
//		sorts = System.currentTimeMillis();
//		sort(MODE);
//		sorte = System.currentTimeMillis();
//		end = System.currentTimeMillis();
//		re += "Reading File Time : "+((read-start)/1000)+"second(s)/";
//		re += "Waiting File Time : "+((wait-start)/1000)+"second(s)/";
//		re += "Sorting Time : "+((sorts-sorte)/1000)+"second(s)/";
//		re += "Total Data Processing Time : "+((end-start)/1000)+"second(s)";
//		System.out.println(re);
//		System.out.println("프로세스완료");
//		
//		return re;
//	}
//	
//	private final static int MODE = 4;
//	private String option = "";
//	private String re = "";
//	private double start = 0;
//	private double read = 0;
//	private double wait = 0;
//	private double sorts = 0;
//	private double sorte = 0;
//	private double end = 0;
//	
//}
