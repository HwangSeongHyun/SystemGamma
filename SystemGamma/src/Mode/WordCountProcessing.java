package Mode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class WordCountProcessing implements Processing {
	static HashMap<String, Integer> count = new HashMap<String, Integer>();

	public static Scanner fileOpen(String filename) {
		File file = null;
		Scanner sc = null;
		try {
			file = new File(filename);
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sc;
	}

	public static void WordCount(Scanner fileSc) {
		String temp = "";
		while (fileSc.hasNext()) {
			temp = fileSc.next();
			temp = temp.replaceAll("[^a-zA-Z]", "");
			temp = temp.toLowerCase();
			if (!count.containsKey(temp)) {
				count.put(temp, 1);
			} else {
				int value = count.get(temp);
				count.replace(temp, value, value + 1);
			}
		}
	}

	public void readCount(String filename) {
		File file = null;
		Scanner fs = null;
		try {
			file = new File(filename);
			fs = new Scanner(file);
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}
		while (fs.hasNext()) {
			String temp = fs.nextLine();
			if (!fs.hasNext())
				break;
			String key = temp.split("=")[0];
			String value = temp.split("=")[1];
			count.put(key, Integer.parseInt(value));
		}
		fs.close();
	}

//	public void sort(int option) {
//		TreeMap<String, Integer> countSortMap = null;
//		File file = null;
//		Scanner fs = null;
//		FileOutputStream fos = null;
//		if (option != 1) {
//			count.clear();
//			try {
//				file = new File("C:\\SystemGamma\\count_result.txt");
//				fs = new Scanner(file);
//			} catch (FileNotFoundException fe) {
//				fe.printStackTrace();
//			}
//			while (fs.hasNext()) {
//				String temp = fs.nextLine();
//				if (!fs.hasNext())
//					break;
//				String key = temp.split("=")[0];
//				String value = temp.split("=")[1];
//				count.put(key, Integer.parseInt(value));
//			}
//			fs.close();
//		}
//		countSortMap = new TreeMap<String, Integer>(count);
//		file = null;
//		try {
//			file = new File("finish.txt");
//			fos = new FileOutputStream(file);
//		} catch (FileNotFoundException fe) {
//			fe.printStackTrace();
//		}
//		for (String key : countSortMap.keySet()) {
//			try {
//				fos.write((key + " = " + countSortMap.get(key) + "\n").getBytes());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		try {
//			fos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public void writeCount() {
		File file = null;
		FileOutputStream fos = null;
		try {
			file = new File("C:\\SystemGamma\\Result\\count_result.txt");
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}
		for (String key : count.keySet()) {
			try {
				fos.write((key + "=" + count.get(key) + "\n").getBytes());
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}
}
