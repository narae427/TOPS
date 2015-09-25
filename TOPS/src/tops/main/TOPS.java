package tops.main;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.*;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.Color;

import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.Cursor;

import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;

import tops.design.*;
import tops.struct.*;
import net.rudp.*;

import java.awt.BorderLayout;

//TOPS : Triangle oriented P2P SNS
public class TOPS implements KeyListener {

	public static String dm_ip = "127.0.0.1";
	public static int dm_pn = 9626;
	public static int dm_filepn = 9262;
	public static String myID = "";
	JFrame frame;
	JTextField textField;
	CardLayout cl_cardpanel, cl2_cardpanel;
	JPanel cardpanel, cardpanel2;
	public static JList<?> freindsList = new JList();
	static JList<?> mainList = new JList();
	JList myList = new JList();
	static JList myFreindList = new JList();
	JTextArea textArea = new JTextArea();
	ListSetting LS = new ListSetting();

	DefaultMutableTreeNode root2;

	String topDirPath = null;
	public static String myHomePath = null;
	public static String myFolderPath = null;
	FreindTreeDialog FTD = null;
	public static JTextField textField_1;
	private JTextField textField_2;
	public static String myOS = null;

	static int DHP = 7001;
	static int DHG = 7;
	
	public static TOPS_Client top_client = null;
	static Hashtable<String, Integer> freindVerHT = new Hashtable<String, Integer>();

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myOS = System.getProperty("os.name");
					TOPS window = new TOPS();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TOPS() throws IOException {
		
		initialize();
		
	}

	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 670);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);

		frame.addWindowListener(new WindowAdapter() // Window Event구현
		{
			public void windowClosing(WindowEvent e) {
				System.out.println("dm_Logout 메시지 전달");
				WaitingDialog WD = new WaitingDialog();
				WD.setSize(300, 150);
				Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension frm = WD.getSize();

				int xpos = (int) (screen.getWidth() / 2 - frm.getWidth() / 2);
				int ypos = (int) (screen.getHeight() / 2 - frm.getHeight() / 2);

				WD.setLocation(xpos, ypos);
				WD.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

				WD.setVisible(true);
				
				new Thread(WD).start();
				TOPS.top_client.sendMessage("'dm_Logout'");
				
			}
		});

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = frame.getSize();
		int xpos = (int) (screen.getWidth() / 2 - frm.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - frm.getHeight() / 2);
		frame.setLocation(xpos, ypos);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		cardpanel = new JPanel();
		frame.getContentPane().add(cardpanel);

		cl_cardpanel = new CardLayout(0, 0);
		cardpanel.setLayout(cl_cardpanel);

		JPanel panel_1 = new JPanel();
		cardpanel.add(panel_1, "name_43955059437844");

		Box verticalBox = Box.createVerticalBox();
		panel_1.add(verticalBox);

		Component verticalStrut = Box.createVerticalStrut(270);
		verticalBox.add(verticalStrut);

		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(
				lblNewLabel.getFont().getStyle() | Font.BOLD));
		horizontalBox_1.add(lblNewLabel);

		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		horizontalBox_1.add(horizontalStrut_1);

		textField = new JTextField();
		horizontalBox_1.add(textField);
		textField.setColumns(10);

		Component horizontalStrut_2 = Box.createHorizontalStrut(10);
		horizontalBox_1.add(horizontalStrut_2);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = myFreindList.locationToIndex(e.getPoint());
					System.out.println("Double clicked on Item " + index + " "
							+ myFreindList.getSelectedValue().toString());
					CommonFriendDialog CFD = new CommonFriendDialog();

					CFD.setSize(130, 670);

					int frameX = frame.getX();
					int frameY = frame.getY();
					int xpos = (int) frameX - 130;
					int ypos = (int) frameY;

					CFD.setLocation(xpos, ypos);
					CFD.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					CFD.setVisible(true);
					
					String fn = myFreindList.getSelectedValue().toString();
					TOPS_Client.sendMessage("'dm_CmnFriend'"+"@"+fn+"@");
				}
			}
		};

		myFreindList.addMouseListener(mouseListener);

		final JButton LoginBtn = new JButton("\uC811\uC18D");
		LoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myID = textField.getText();
//				TOPS_Daemon daemon = new TOPS_Daemon(); ///////////////////////////////////////////////////////////////////////////HNR
//				daemon.executeServer(); //HNR
				try {
					top_client = new TOPS_Client();
					int suc = -1;
					while(suc == -1){
						suc = top_client.connectDaemon();
					}
					new Thread(top_client).start();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String HomeDir = System.getProperty("user.home");
				myHomePath = HomeDir;
				System.out.println("Home Directory :" + HomeDir);
				File MyDir = new File(HomeDir
						+ System.getProperty("file.separator") + "TOPS"
						+ System.getProperty("file.separator") + myID);
				if (!MyDir.exists()) {
					MyDir.mkdirs();
				}

				myFolderPath = MyDir.getPath();
				System.out.println("My Folder Path : " + myFolderPath);
				System.out.println();

				top_client.checkUpdateFiles();
				System.out.println("checkUpdateFiles==========================");
//				Client_DaemonThread CDT;
//				try {
//					CDT = new Client_DaemonThread();
//					CDT.readyForReceiveUpdateFile_UPDATE();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
				
				LS.setList(mainList, myFolderPath);

				cl_cardpanel.next(cardpanel);
				LoginBtn.setEnabled(false);

			}
		});
		horizontalBox_1.add(LoginBtn);
		frame.getRootPane().setDefaultButton(LoginBtn);

		JPanel panel_2 = new JPanel();
		cardpanel.add(panel_2, "name_43973260633683");
		panel_2.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		panel_2.add(toolBar, BorderLayout.NORTH);
		toolBar.setPreferredSize(new Dimension(13, 22));
		toolBar.setMaximumSize(new Dimension(500, 50));

		JButton btnNewButton_2 = new JButton("\uBAA8\uC544\uBCF4\uAE30");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // ////////////////////////////모아보기
															// 버튼
				cl2_cardpanel.show(cardpanel2, "one");

				LS.setList(mainList, myFolderPath);
			}
		});
		
		btnNewButton_2.setMaximumSize(new Dimension(100, 23));
		toolBar.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("내 스토리");
		btnNewButton_3.addActionListener(new ActionListener() { // //////////////////////////////내
																// 스토리 버튼
					public void actionPerformed(ActionEvent e) {
						cl2_cardpanel.show(cardpanel2, "two");

						myList.setName("MYLIST");
						LS.setList(
								myList,
								myFolderPath
										+ System.getProperty("file.separator")
										+ myID);
					}
				});
		btnNewButton_3.setMaximumSize(new Dimension(100, 23));
		toolBar.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("친구 스토리");
		btnNewButton_4.addActionListener(new ActionListener() { // ///////////////////////////////친구
					// 스토리 버튼
					public void actionPerformed(ActionEvent e) {
						cl2_cardpanel.show(cardpanel2, "three");
						if (FTD == null) {
							FTD = new FreindTreeDialog();
							FTD.setSize(130, 670);

							int frameX = frame.getX();
							int frameY = frame.getY();
							int xpos = (int) frameX - 130;
							int ypos = (int) frameY;

							FTD.setLocation(xpos, ypos);
							FTD.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							// FTD.setVisible(true);
						}

						FTD.setVisible(true);

					}
				});
		btnNewButton_4.setMaximumSize(new Dimension(100, 23));
		toolBar.add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("***");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl2_cardpanel.show(cardpanel2, "four");
				try {
					setFreindList();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_5.setMaximumSize(new Dimension(100, 23));
		toolBar.add(btnNewButton_5);

		cardpanel2 = new JPanel();
		panel_2.add(cardpanel2);
		cl2_cardpanel = new CardLayout(10, 10);
		cardpanel2.setLayout(cl2_cardpanel);

		Box one = Box.createVerticalBox();
		cardpanel2.add(one, "one");
		one.setAlignmentX(Component.CENTER_ALIGNMENT);

		Box horizontalBox_3 = Box.createHorizontalBox();
		horizontalBox_3.setMaximumSize(new Dimension(500, 100));
		horizontalBox_3.setAlignmentY(Component.CENTER_ALIGNMENT);
		one.add(horizontalBox_3);

		textField_1 = new JTextField();
		horizontalBox_3.add(textField_1);
		textField_1.setColumns(10);

		Component horizontalStrut_8 = Box.createHorizontalStrut(10);
		horizontalBox_3.add(horizontalStrut_8);

		JButton btnNewButton_6 = new JButton("사진");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreviewDialog previewD = new PreviewDialog();
				previewD.setSize(300, frame.getHeight());

				int xpos = (int) (frame.getX() + frame.getWidth());
				int ypos = (int) frame.getY();

				previewD.setLocation(xpos, ypos);
				previewD.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

				previewD.setVisible(true);

				addPictureDialog PD = new addPictureDialog(previewD);
				PD.setSize(300, 150);
				Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension frm = PD.getSize();

				// int frameX = frame.getX();
				// int frameY = frame.getY();
				xpos = (int) (screen.getWidth() / 2 - frm.getWidth() / 2);
				ypos = (int) (screen.getHeight() / 2 - frm.getHeight() / 2);

				PD.setLocation(xpos, ypos);
				PD.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				// FTD.setVisible(true);

				PD.setVisible(true);

			}
		});
		btnNewButton_6.setPreferredSize(new Dimension(69, 24));
		horizontalBox_3.add(btnNewButton_6);

		Component verticalStrut_4 = Box.createVerticalStrut(20);
		horizontalBox_3.add(verticalStrut_4);

		Component verticalStrut_5 = Box.createVerticalStrut(5);
		one.add(verticalStrut_5);

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setMaximumSize(new Dimension(500, 0));
		horizontalBox.setPreferredSize(new Dimension(0, 60));
		horizontalBox.setAlignmentY(Component.CENTER_ALIGNMENT);
		one.add(horizontalBox);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setAutoscrolls(true);
		scrollPane_1.setPreferredSize(new Dimension(3, 50));
		scrollPane_1.setMinimumSize(new Dimension(22, 50));
		horizontalBox.add(scrollPane_1);
		textArea.setMinimumSize(new Dimension(0, 50));
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		scrollPane_1.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setBorder(new LineBorder(new Color(128, 128, 128), 1, true));

		textArea.addKeyListener(this);

		Component horizontalStrut = Box.createHorizontalStrut(10);
		horizontalStrut.setPreferredSize(new Dimension(10, 0));
		horizontalStrut.setMinimumSize(new Dimension(10, 0));
		horizontalBox.add(horizontalStrut);

		JButton btnNewButton = new JButton("Write");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fileName = myID + "_" + getTime();
				// File f = new File(dirPath + fileName);

				try {
					File IDFile = new File(myFolderPath
							+ System.getProperty("file.separator") + myID);
					if (!IDFile.exists()) {
						IDFile.mkdirs();
					}
					FileWriter fw = new FileWriter(myFolderPath
							+ System.getProperty("file.separator") + myID
							+ System.getProperty("file.separator") + fileName);

					BufferedWriter bw = new BufferedWriter(fw);

					if (textField_1.getText().length() > 17) {
						StringTokenizer st = new StringTokenizer(textField_1
								.getText());
						String picName = "";
						while (st.hasMoreTokens()) {
							picName = st.nextToken(System
									.getProperty("file.separator"));
						}

						File picPathFile = new File(myFolderPath
								+ System.getProperty("file.separator") + myID
								+ System.getProperty("file.separator")
								+ "Pictures"
								+ System.getProperty("file.separator") + myID
								+ "_" + picName);
						String picPath = picPathFile.getPath();
						bw.write(picPath + "\n");
					} else {
						bw.write("[No Picture]" + "\n");
					}

					bw.write(textArea.getText());
					bw.close();
					fw.close();

					writeUpdateFile("[Write]" + fileName + "\n");
					textArea.setText("");
					textField_1.setText("");
					LS.setList(mainList, myFolderPath);

//					Hashtable<String, Integer> tempIDM = new Hashtable<String, Integer>();
//					if(IDM.get(myID) == null || IDM.get(myID).get(myID) == null) tempIDM.put(myID, 1);
//					else	tempIDM.put(myID, IDM.get(myID).get(myID)+1);
//					IDM.put(myID, tempIDM);

//					Client.CallPushData(fileName);
					TOPS.top_client.sendMessage("'dm_PushData'" + ":" + fileName + ":");
					System.out.println("dm_PushData");
					
//					DM_Sync sync = new DM_Sync();
//					sync.DoSynchronize_Send();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("파일 생성 실패");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		horizontalBox.add(btnNewButton);

		Component verticalStrut_2 = Box.createVerticalStrut(10);
		one.add(verticalStrut_2);

		Box horizontalBox_2 = Box.createHorizontalBox();
		horizontalBox_2.setAlignmentY(Component.CENTER_ALIGNMENT);
		one.add(horizontalBox_2);

		JScrollPane scrollPane = new JScrollPane();
		horizontalBox_2.add(scrollPane);

		// JList mainList = new JList();
		scrollPane.setViewportView(mainList);

		JPanel two = new JPanel();
		cardpanel2.add(two, "two");
		two.setLayout(new BorderLayout(0, 5));

		JScrollPane scrollPane_2 = new JScrollPane();
		two.add(scrollPane_2);
		myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// JList myList = new JList();
		scrollPane_2.setViewportView(myList);

		JPanel panel = new JPanel();
		two.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JButton editButton = new JButton("Edit");
		panel.add(editButton);
		editButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				Writing w = ListCell.selectedW;

				File f = new File(myFolderPath
						+ System.getProperty("file.separator") + myID
						+ System.getProperty("file.separator") + w.getTitle());
				if (f.exists()) {
					f.delete();

				}

				writeUpdateFile("[Delete]" + w.getTitle() + "\n");

				LS.setList(myList,
						myFolderPath + System.getProperty("file.separator")
								+ myID);
				LS.setList(mainList, myFolderPath);

				// Client c = new Client(false);
//				Client.CallAdvertisement_UPDATE();
				TOPS.top_client.sendMessage("'dm_AdvUpdate'");

			}
		});
		panel.add(deleteButton);
		deleteButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JScrollPane three = new JScrollPane();
		cardpanel2.add(three, "three");
		three.setViewportView(freindsList);

		freindsList
				.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		root2 = new DefaultMutableTreeNode("Freind List");

		JPanel four = new JPanel();
		cardpanel2.add(four, "four");
		four.setLayout(new BorderLayout(10, 10));

		Box horizontalBox_4 = Box.createHorizontalBox();
		four.add(horizontalBox_4, BorderLayout.NORTH);

		JLabel lblNewLabel_1 = new JLabel("친구 ID");
		horizontalBox_4.add(lblNewLabel_1);

		Component horizontalStrut_11 = Box.createHorizontalStrut(10);
		horizontalBox_4.add(horizontalStrut_11);

		textField_2 = new JTextField();
		horizontalBox_4.add(textField_2);
		textField_2.setColumns(10);

		Component horizontalStrut_10 = Box.createHorizontalStrut(10);
		horizontalBox_4.add(horizontalStrut_10);

		JButton btnNewButton_7 = new JButton("추가");
		btnNewButton_7.addActionListener(new ActionListener() {
			@SuppressWarnings("resource")
			public void actionPerformed(ActionEvent e) {
				if (textField_2.getText().equals("")) {
					System.out.println("ID를 입력하세요.");
					return;
				}

				File freindListFile = new File(myFolderPath
						+ System.getProperty("file.separator") + myID
						+ "_FreindList");

				LineNumberReader reader;
				if (freindListFile.exists()) {
					try {
						reader = new LineNumberReader(new FileReader(
								freindListFile));
						while (true) {
							String str = reader.readLine();
							if (str == null) {
								break;
							}
							if (str.equals(textField_2.getText())) {
								textField_2.setText("");
								System.out.println("이미 등록된 친구 입니다.");
								return;
							}
						}
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

//				try {
//					Client_LoginServer MMS = new Client_LoginServer();
//					// MMS.sendMessageToMainServer(msg.CheckOnlineFreindMSG("127.0.0.1",
//					// String.valueOf(myPortNumber), textField_2.getText()));
//					MMS.sendMSGtoLoginServer(msg.RequestAddFreindMSG(myID,
//							textField_2.getText()));
//					textField_2.setText("");
//					setFreindList();
//
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				TOPS.top_client.sendMessage("'dm_AddFriend'" + "@"+textField_2.getText()+"@");
				textField_2.setText("");
				try {
					setFreindList();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		horizontalBox_4.add(btnNewButton_7);

		Box verticalBox_1 = Box.createVerticalBox();
		four.add(verticalBox_1, BorderLayout.CENTER);

		JLabel lblNewLabel_2 = new JLabel("친구 목록");
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox_1.add(lblNewLabel_2);

		JScrollPane scrollPane_3 = new JScrollPane();
		verticalBox_1.add(scrollPane_3);

		scrollPane_3.setViewportView(myFreindList);
	}

	/*
	 * write 하거나 delete 할떄 UpdateFile에 기록해줌.
	 */

	public void writeUpdateFile(String msg) {
		File updateFilePath = new File(myFolderPath
				+ System.getProperty("file.separator") + "UpdateFile");

		File[] myUpdateFile = null;
		myUpdateFile = updateFilePath.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.contains(myID) && name.contains("UpdateFile");
			}

		});

		if (myUpdateFile == null || myUpdateFile.length == 0) {
			if (!updateFilePath.exists()) {
				updateFilePath.mkdir();
			}
			myUpdateFile = new File[1];
			myUpdateFile[0] = new File(updateFilePath
					+ System.getProperty("file.separator") + myID + "_"
					+ "UpdateFile" + "_" + String.valueOf(0));

		}
		String temp = myID + "_" + "UpdateFile" + "_";
		int ver = Integer.valueOf(myUpdateFile[0].getName().substring(
				temp.length())); // //////////////////////////////////////////////////////////////////////
		ver++;

		File oldUpdateFile = new File(myUpdateFile[0].getPath());
		File newUpdateFile = new File(updateFilePath
				+ System.getProperty("file.separator")
				+ myUpdateFile[0].getName().substring(0, temp.length())
				+ String.valueOf(ver));

		oldUpdateFile.renameTo(newUpdateFile);
		if (oldUpdateFile.exists()) {
			System.out.println("DELETE : " + oldUpdateFile.delete());
		}

		try {
			FileWriter fw = new FileWriter(newUpdateFile, true);
			fw.write("VERSION " + ver + "\n");
			fw.write(msg);
			fw.flush();
			fw.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	public static void setFreindList() throws IOException {
		File freindListFile = new File(myFolderPath
				+ System.getProperty("file.separator") + myID + "_FreindList");
		if (!freindListFile.exists())
			return;
		ArrayList<String> freindListArr = new ArrayList<String>();
		LineNumberReader reader = new LineNumberReader(new FileReader(
				freindListFile));
		while (true) {
			String freindID = reader.readLine();
			if (freindID == null)
				break;
			freindListArr.add(freindID);
		}
		myFreindList.setListData((String[]) freindListArr
				.toArray(new String[0]));

	}

	public String getTime() {
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String strTime = dayTime.format(new Date(time));
		return strTime;
	}

	static public int getAvailablePortNumber() {
		int availablePortNumber = 0;
		for (int pn = 1024; pn < 65535; pn++) {
			try {
				ReliableServerSocket socket = new ReliableServerSocket(pn);
				socket.setReuseAddress(true);
				availablePortNumber = pn;
				socket.close();
				return availablePortNumber;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				continue;
			}

		}

		return -1;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		String s = textArea.getText();
		PreviewDialog.previewD_textArea.setText(s);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	

}
