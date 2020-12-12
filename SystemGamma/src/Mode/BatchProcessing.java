package Mode;

import java.io.File;
import java.util.Scanner;

public class BatchProcessing extends WordCountProcessing {

	public BatchProcessing(String option) {
		this.option = option;
	}

	public void BPmain() {
		File dir = new File("C:\\SystemGamma\\Data");
		File files[] = dir.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			int pos = files[i].getName().lastIndexOf(".");
			String ext = files[i].getName().substring(pos + 1);
			if (ext.equals("txt")) {
				System.out.println(files[i].getName());
				Scanner fileSc = fileOpen("C:\\SystemGamma\\Data\\"+files[i].getName());
				WordCount(fileSc);
				fileSc.close();
			}
		}
		writeCount();
	}

	private String option;
}
