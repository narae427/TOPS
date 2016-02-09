package tops.main;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;

import tops.design.*;
import tops.struct.*;
import net.rudp.*;

public class TOPS_Client implements Runnable {
	public static Socket sock = null;
	String line = null;
	OutputStream out = null;
	InputStream in = null;
	BufferedReader br = null;
	static PrintWriter pw = null;

	private String getPatternfromMSG(String message, Pattern p) {
		Pattern pattern = p;
		Matcher m = pattern.matcher(message);

		boolean find = false;

		String rstr = null;

		while (m.find()) {
			rstr = m.group();
			find = true;
		}

		if (find)
			rstr = rstr.substring(1, rstr.length() - 1);
		if (rstr == null)
			return "-1";
		else
			return rstr;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		line = null;
		while (true) {
			
			try {
				line = br.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (line == null)
				continue;

			Pattern commandPattern = Pattern.compile("'.*'");
			String commandMessage = getPatternfromMSG(line, commandPattern);

			if (commandMessage.equals("dm_RequestAddFriend")) {
				Pattern idPattern = Pattern.compile("!.*!");
				String idMessage = getPatternfromMSG(line, idPattern);
				addFreindDialog FD = new addFreindDialog(idMessage);

				FD.setSize(300, 150);
				Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension frm = FD.getSize();

				int xpos = (int) (screen.getWidth() / 2 - frm.getWidth() / 2);
				int ypos = (int) (screen.getHeight() / 2 - frm.getHeight() / 2);

				FD.setLocation(xpos, ypos);
				FD.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

				FD.setVisible(true);
			} else if (commandMessage.equals("dm_FriendUpdate")) {
				try {
					TOPS.setFreindList();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (commandMessage.equals("dm_ListUpdate")) {

				TOPS_Client.sendMessage("'dm_Sync'");

				DM_Sync sync;
				try {
					sync = new DM_Sync();
					sync.DoSynchronize_Recieve();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ListSetting LS = new ListSetting();
				LS.setList(TOPS.mainList, TOPS.myFolderPath);
			}else if(commandMessage.equals("dm_Sync")){
				DM_Sync sync;
				try {
					sync = new DM_Sync();
					sync.DoSynchronize_Send();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else if (commandMessage.equals("dm_ShowCmnFriend")) {
				Pattern fidPattern = Pattern.compile("@.*@");
				String fidMessage = getPatternfromMSG(line, fidPattern);

				StringTokenizer st = new StringTokenizer(fidMessage, ";");
				String freindId = "";
				Vector<String> v = new Vector<String>();
				while (st.hasMoreTokens()) {
					freindId = st.nextToken();
					v.add(freindId);
				}
				CommonFriendDialog.cmn_list.setListData(v);
			}

		}
	}

	public int connectDaemon() throws UnknownHostException, IOException {
		TOPS. dm_pn = TOPS_Server.ServerPN; 
		TOPS. dm_filepn = TOPS_Server.FilePN;
		 if(TOPS. dm_pn == -1) return -1;
		sock = new Socket(TOPS.dm_ip, TOPS.dm_pn); 

		out = sock.getOutputStream();
		in = sock.getInputStream();
		br = new BufferedReader(new InputStreamReader(in));
		pw = new PrintWriter(new OutputStreamWriter(out));

		sendMessage("'dm_Login'" + "!" + TOPS.myID + "!");
		return 0;
	}

	public static void sendMessage(String line) {
		pw.println(line);
		pw.flush();

	}

	public void checkUpdateFiles() {
		File[] myUpdateFile = null;
		File myUpdateFilePath = new File(TOPS.myFolderPath
				+ System.getProperty("file.separator") + "UpdateFile");
		myUpdateFile = myUpdateFilePath.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.contains("UpdateFile");
			}

		});

		if (myUpdateFile == null || myUpdateFile.length == 0) {
			return;
		}

		for (File f : myUpdateFile) {
			String[] fileNameTokens = f.getName().split("_");
			String id = fileNameTokens[0];
			int version = Integer.valueOf(fileNameTokens[2]);

			TOPS.freindVerHT.put(id, version);
		}
		return;
	}
}
