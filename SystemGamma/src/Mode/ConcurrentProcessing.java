package Mode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import TCPConnection.Client;
import TCPConnection.Server;

public class ConcurrentProcessing extends WordCountProcessing{
	private String option;
	
	public ConcurrentProcessing(String option) {
		this.option = option;
	}
	
	public void CPmain() {
		
	}
}
