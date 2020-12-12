package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import TCPConnection.*;

@SuppressWarnings("serial")
public class MasterUI extends JFrame {
	
	public MasterUI() {
		setTitle("System Gamma");
		setSize(800, 640);
		c.setLayout(new GridLayout(9, 3));
		initialize();
		addActionListener();

		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void initialize() {
	
		for (int i = 0; i < 2; i++) {
			processingNodeLabel[i] = new JLabel();
			processingNodeLabel[i].setHorizontalAlignment(JLabel.CENTER);
			storageNodeLabel[i] = new JLabel();
			storageNodeLabel[i].setHorizontalAlignment(JLabel.CENTER);
			hybridNodeLabel[i] = new JLabel();
			hybridNodeLabel[i].setHorizontalAlignment(JLabel.CENTER);
		}
		// 1 row
		c.add(selectProfile);
		c.add(detectedNodeLabel);
		c.add(hybridNodeList);
		// 2 row
		c.add(makeProfile);
		c.add(new JLabel());
		c.add(hybridNodeLabel[0]);
		// 3 row
		c.add(filePathLabel);
		c.add(filePathStringLabel);
		c.add(hybridNodeLabel[1]);
		// 4 row
		c.add(optionLabel);
		c.add(optionStringLabel);		
		c.add(processingNodeList);
		// 5 row
		c.add(modeLabel);
		c.add(modeStringLabel);
		c.add(processingNodeLabel[0]);
		// 6 row
		c.add(targetLabel);
		c.add(targetStringLabel);
		c.add(processingNodeLabel[1]);
		// 7 row
		c.add(new JLabel());
		c.add(new JLabel());
		c.add(storageNodeList);
		// 8 row
		c.add(processingTime);
		c.add(processingStringLabel);
		c.add(storageNodeLabel[0]);
		// 9 row
		c.add(new JLabel());
		c.add(start);
		c.add(storageNodeLabel[1]);
		
		selectProfile.setEnabled(false);
		makeProfile.setEnabled(false);
	}

	public void addActionListener() {
		StartActionListener startListener = new StartActionListener();
		start.addActionListener(startListener);
		SelectProfileActionListener selectListener = new SelectProfileActionListener();
		selectProfile.addActionListener(selectListener);
		MakeProfileActionListener makeListener = new MakeProfileActionListener();
		makeProfile.addActionListener(makeListener);
	}

	class StartActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			startFlag = true;
			ProcessingActionListener processListener = new ProcessingActionListener();
			start.setText("Processing Start");
			start.removeActionListener(this);
			start.addActionListener(processListener);
			BroadcastClient c = new BroadcastClient();
			c.searchActiveNode();
			nodeList = new HashMap<InetAddress, String>(c.searchActiveNode());
			if (nodeList.size()>0) {
				Iterator<InetAddress> keys = nodeList.keySet().iterator();
				while (keys.hasNext()) {
					InetAddress key = keys.next();
					String type = nodeList.get(key);
					if (type.equals("Processing")) {
						processingNodeLabel[processingIndex++].setText(key.toString());
						targetList.add(key.toString());
					}
					else if (type.equals("Storage")) {
						storageNodeLabel[storageIndex++].setText(key.toString());
					}	
					else if (type.equals("Hybrid")) {
						hybridNodeLabel[hybridIndex++].setText(key.toString());
						targetList.add(key.toString());
					}
					else {
						
					}
				}
				selectProfile.setEnabled(true);
				makeProfile.setEnabled(true);
				detectedNodeLabel.setText("");
			}
			else {
				detectedNodeLabel.setText("Node not discovered");
			}
		}
	}

	class ProcessingActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			startFlag = true;
			startTime = System.currentTimeMillis();

			InetAddress targetIP = null;
			try {
				String targetStringIP = targetStringLabel.getText();
				if (targetStringIP.equals("EveryNode")) {
					//구현중
					JOptionPane.showMessageDialog(null, "EveryNode Processing is not supported!", 
							"Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				targetIP = InetAddress.getByName(targetStringLabel.getText());
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			String targetType = nodeList.get(targetIP);
			String option = optionStringLabel.getText();
			DataSendClient dsc = new DataSendClient(targetIP.toString(), optionStringLabel.getText());
			DataReceiveServer drs = new DataReceiveServer();
			if (targetType.equals("Storage")) {
				//구현중
				dsc.requestData(processingNodeLabel[(int)(Math.random()*processingIndex)].getText(), 
						option);
				drs.receiveFile();
				
			}
			else if (targetType.equals("Hybrid")) {
				dsc.requestData("BatchProcessing", option);
				drs.receiveFile();
				endTime = System.currentTimeMillis()-startTime;
				processingStringLabel.setText(Double.toString(endTime*1000)+"seconds");
			}
			System.out.println("pass");

		}
	}

	class SelectProfileActionListener implements ActionListener {
		private JFileChooser chooser;
		
		public SelectProfileActionListener() {
			chooser = new JFileChooser("C:\\SystemGamma\\Profile");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String filePath;
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", "txt");
			chooser.setFileFilter(filter);
			
			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "No file selected", 
						"Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else {
				filePath = chooser.getSelectedFile().getPath();
				filePathStringLabel.setText(filePath);
				file = new File(filePath);
				Scanner fileSc = null;
				try {
					fileSc = new Scanner(file);
				} catch (FileNotFoundException fe) {
					fe.printStackTrace();
				}
				String line = fileSc.nextLine();
				String option[] = line.split("/");
				modeStringLabel.setText(option[0]);
				targetStringLabel.setText(option[1]);
				optionStringLabel.setText(option[2]);
			}
		}
	}
	
	class MakeProfileActionListener implements ActionListener {
		private JTextField name = new JTextField();
		private String[] option = {"", "txt", "png", "html"};
		private String[] target;
		private String[] mode = {"", "Word Counting"};
		private String selectOption;
		private String selectTarget;
		private String selectMode;
		@Override
		public void actionPerformed(ActionEvent e) {
			new MakeProfile();
		}
		
		class MakeProfile extends JFrame{
			public MakeProfile() {
				setLayout(new BorderLayout(1, 1));
				JPanel north = new JPanel(new GridLayout(4, 2));
				JPanel south = new JPanel();
				setTitle("Make Profile");
				setSize(450, 300);
				north.add(new JLabel("File Name"));
				north.add(name);
				north.add(new JLabel("option"));
				JComboBox<String> optionBox = new JComboBox<String>(option);
				optionBox.addActionListener(new ActionListener() {
					@SuppressWarnings("unchecked")
					@Override
					public void actionPerformed(ActionEvent e) {
						selectOption = (String)((JComboBox<String>)e.getSource()).getSelectedItem();
					}
				});
				north.add(optionBox);
				target = new String[targetList.size()+1];
				target[0] = "EveryNode";
				for (int i=0 ; i<targetList.size() ; i++) {
					target[i+1] = targetList.get(i).substring(1);
				}
				north.add(new JLabel("target"));
				JComboBox<String> targetBox = new JComboBox<String>(target);
				targetBox.addActionListener(new ActionListener() {
					@SuppressWarnings("unchecked")
					@Override
					public void actionPerformed(ActionEvent e) {
						selectTarget = (String)((JComboBox<String>)e.getSource()).getSelectedItem();
					}
				});
				north.add(targetBox);
				north.add(new JLabel("mode"));
				JComboBox<String> modeBox = new JComboBox<String>(mode);
				modeBox.addActionListener(new ActionListener() {
					@SuppressWarnings("unchecked")
					@Override
					public void actionPerformed(ActionEvent e) {
						selectMode = (String)((JComboBox<String>)e.getSource()).getSelectedItem();
					}
				});
				north.add(modeBox);
				JButton confirm = new JButton("confirm");
				confirm.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if (selectTarget==null) {
								selectTarget = "EveryNode";
							}
							File file = new File("C:\\SystemGamma\\Profile\\"+name.getText()+".txt");
							FileOutputStream fos = new FileOutputStream(file);
							fos.write((selectOption.replaceAll(" ", "")+"/"+
									selectTarget.replaceAll(" ", "")+"/"+
									selectMode.replaceAll(" ", "")).getBytes());
							fos.close();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						dispose();
					}
				});
				south.add(confirm);
				add(north, BorderLayout.CENTER);
				add(south, BorderLayout.SOUTH);
				setLocationRelativeTo(null);
				setVisible(true);
			}
		}
	}
	
	public void setTextAlienment() {
		hybridNodeList.setHorizontalAlignment(JLabel.CENTER);
		processingNodeList.setHorizontalAlignment(JLabel.CENTER);
		storageNodeList.setHorizontalAlignment(JLabel.CENTER);
		processingTime.setHorizontalAlignment(JLabel.CENTER);
		filePathLabel.setHorizontalAlignment(JLabel.CENTER);
		filePathStringLabel.setHorizontalAlignment(JLabel.CENTER);
		modeLabel.setHorizontalAlignment(JLabel.CENTER);
		modeStringLabel.setHorizontalAlignment(JLabel.CENTER);
		targetLabel.setHorizontalAlignment(JLabel.CENTER);
		targetStringLabel.setHorizontalAlignment(JLabel.CENTER);
		optionLabel.setHorizontalAlignment(JLabel.CENTER);
		optionStringLabel.setHorizontalAlignment(JLabel.CENTER);
	}
	
	private JLabel processingNodeList = new JLabel("Processing Node List");
	private JLabel storageNodeList = new JLabel("Storage Node List");
	private JLabel hybridNodeList = new JLabel("Hybrid Node List");
	private JLabel processingTime = new JLabel("ProcessingTime : ");
	private JLabel filePathLabel = new JLabel("File Path : ");
	private JLabel filePathStringLabel = new JLabel();
	private JLabel modeLabel = new JLabel("Mode : ");
	private JLabel modeStringLabel = new JLabel();
	private JLabel targetLabel = new JLabel("Target : ");
	private JLabel targetStringLabel = new JLabel();
	private JLabel optionLabel = new JLabel("Option : ");
	private JLabel optionStringLabel = new JLabel();
	private JLabel detectedNodeLabel = new JLabel();
	private JLabel processingStringLabel = new JLabel();
	private JLabel[] processingNodeLabel = new JLabel[2];
	private JLabel[] storageNodeLabel = new JLabel[2];
	private JLabel[] hybridNodeLabel = new JLabel[2];
	private JButton start = new JButton("System Start");
	private JButton selectProfile = new JButton("Select Profile");
	private JButton makeProfile = new JButton("Make Profile");
	private Container c = getContentPane();
	private boolean startFlag = false;
	private boolean endFlag = false;
	private double startTime;
	private double endTime;
	private int processingIndex = 0;
	private int storageIndex = 0;
	private int hybridIndex = 0;
	private File file = null;
	private ArrayList<String> targetList = new ArrayList<String>();
	private HashMap<InetAddress, String> nodeList = new HashMap<InetAddress, String>();
	
}
