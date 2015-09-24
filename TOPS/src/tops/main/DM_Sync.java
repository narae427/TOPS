package tops.main;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DM_Sync {
	Socket socket = TOPS_Client.sock;
	static String myFolderPath = TOPS.myFolderPath;
	File myUpdateFilePath = new File(myFolderPath
			+ System.getProperty("file.separator") + "UpdateFile");

	public DM_Sync() {
	}

	public void DoSynchronize() throws IOException {
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		while (true) {

			String filePath = dis.readUTF();
			String fileName = dis.readUTF();
			
			String[] filePathTokens = filePath.split("TOPS_Daemon");
			final String newFile = filePathTokens[1];

			if (fileName.equals("FINISH")) {
				break;
			}

			File newFilePath = new File(TOPS.myHomePath
					+ System.getProperty("file.separator") + "TOPS" + System.getProperty("file.separator")  + newFile); // updatefile
			// °æ·Î
			// File myUpdateFilePath = new File(myFolderPath);

			System.out.println("newFilePath : " + newFilePath);
			System.out.println("parentFilePath : " + newFilePath.getParent());

			if(!newFilePath.exists()){
				newFilePath.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(newFilePath);
			bos = new BufferedOutputStream(fos);
			long fileSize = dis.readLong();
			byte[] buffer = new byte[(int) fileSize];
			int data = 0;

			data = dis.read(buffer);
			bos.write(buffer, 0, data);
			bos.flush();

			try {
				bos.close();
				fos.close();
			} catch (Exception e) {

			}

		}

	}

}
