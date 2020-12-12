//package Mode;
//
//import java.io.File;
//import java.util.Scanner;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import TCPConnection.*;
//
//public class DataImporting extends WordCountProcessing {
//	
//	public DataImporting(String option) {
//		this.option = option;
//	}
//	
//	public void DImain() {
//		File dir = new File("text");
//		File files[] = dir.listFiles();
//		
//		start = System.currentTimeMillis();
//		switch(option) {
//		case "One Master One Node":
//			Client node1_1c = new Client("203.249.22.142", 9999);
//			node1_1c.requestData(MODE, 1);
//			ExecutorService executorService1 = Executors.newFixedThreadPool(250);
//			for (i=(files.length)/2 ; i<=files.length ; i++) {
//				Thread thread = new Thread() {
//					@Override
//					public void run() {
//						Server node = new Server(9999+i);
//						node.receiveFile("text//"+i+".txt");
//						System.out.println(i+"complete");
//					}
//				};
//				executorService1.execute(thread);
//			}
//			while(!executorService1.isShutdown()) {
//				Thread.yield();
//			}
//			waitThreadState = true;
//			break;
//		case "One Master Two Node":
//			Thread thread1 = new Thread() {
//				@Override
//				public void run() {
//					Client node2_1c = new Client("203.249.22.142", 9999);
//					node2_1c.requestData(MODE, 2);
//					ExecutorService executorService1 = Executors.newFixedThreadPool(250);
//					for (i=(files.length)/3 ; i<=(files.length/3)*2 ; i++) {
//						Thread thread = new Thread() {
//							@Override
//							public void run() {
//								Server node = new Server(9999+i);
//								node.receiveFile("text//"+i+".txt");
//							}
//						};
//						executorService1.execute(thread);
//					}
//					while(!executorService1.isShutdown()) {
//						Thread.yield();
//					}
//				}
//			};
//			Thread thread2 = new Thread() {
//				@Override
//				public void run() {
//					Client node2_1c = new Client("203.249.22.143", 11111);
//					node2_1c.requestData(MODE, 2);
//					ExecutorService executorService2 = Executors.newFixedThreadPool(250);
//					for (j=(files.length/3)*2 ; j<=files.length ; j++) {
//						Thread thread = new Thread() {
//							@Override
//							public void run() {
//								Server node = new Server(11111+j);
//								node.receiveFile("text//"+j+".txt");
//							}
//						};
//						executorService2.execute(thread);
//					}
//					while(!executorService2.isShutdown()) {
//						Thread.yield();
//					}
//				}
//			};
//			thread1.start();
//			thread2.start();
//			break;
//			
//		}
//		while(!(waitThreadState || (waitThreadState1 && waitThreadState2))) {
//			Thread.yield();
//		};
//		wait = System.currentTimeMillis();
//		for (k=1 ; k<=files.length ; k++) {
//			Scanner fileSc = fileOpen("text\\"+k+".txt");
//			WordCount(fileSc);
//			fileSc.close();
//		}
//		read = System.currentTimeMillis();
//		sort(MODE);
//		sort = System.currentTimeMillis();
//		end = System.currentTimeMillis();
//	}
//	
//	
//	private final static int MODE = 3;
//	private String option = "";
//	private String re = "";
//	private double start = 0;
//	private double read = 0;
//	private double wait = 0;
//	private double sort = 0;
//	private double end = 0;
//	private boolean waitThreadState = false;
//	private boolean waitThreadState1 = false;
//	private boolean waitThreadState2 = false;
//	private int i = 0;
//	private int j = 0;
//	private int k = 0;
//	
//}
