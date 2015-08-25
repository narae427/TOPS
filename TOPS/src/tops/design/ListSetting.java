package tops.design;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import tops.design.*;
import tops.main.*;
import tops.struct.*;


public class ListSetting {
	static PresentationView PV = null;
	public ListSetting(){
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JList setList(JList list, String path)  {
		ArrayList<Writing> writingList = new ArrayList<Writing>();
		 
		File f = new File(path); 
		File[] directories = f.listFiles(new FileFilter(){
			@Override
			public boolean accept(File d) {
				// TODO Auto-generated method stub
				return d.isDirectory() && !d.getName().equals("Pictures") && !d.getName().equals("UpdateFile");
			}
			
		});
		int numberOfFiles = 0;
		File[] files = null;
		if(directories == null){
			return null;
		}
		if(directories.length == 0){
			if(f.getPath().equals(TOPS.myFolderPath))
				return null;
			
			files = f.listFiles(new FileFilter(){

				@Override
				public boolean accept(File f) {
					// TODO Auto-generated method stub
					return f.isFile() && !f.getName().contains("UpdateFile");
				}
				
			});
		}
		else if(directories.length > 0){
			for(File d : directories){
				String dName = d.getName();
				File fDir = new File(path + System.getProperty("file.separator") + dName);
				File[] tempFiles = fDir.listFiles(new FileFilter(){

					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						return f.isFile();
					}
					
				});
				numberOfFiles += tempFiles.length;
			}
			
			files = new File[numberOfFiles];
			numberOfFiles = 0;
			for(File d : directories){
				String dName = d.getName();
				File fDir = new File(path + System.getProperty("file.separator") + dName);
				
				File[] tempFiles = fDir.listFiles(new FileFilter(){

					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						return f.isFile();
					}
					
				});
				System.arraycopy(tempFiles, 0, files, numberOfFiles, tempFiles.length);
				numberOfFiles += tempFiles.length;
			}
			
		}
		
		
		files = sortFileList(files);
		
		//FileReader reader = null;
		LineNumberReader reader = null;
		for (File file : files) {
			
			String fName = file.getName();
			String[] fileNameTokens = fName.split("_");
			String writer = fileNameTokens[0];
			String date = fileNameTokens[1];
			String time = fileNameTokens[2];
			
			if(directories.length > 0){	 //하위 디렉터리가 필요한 path가 들어왔을때 
				File temp = new File(path + System.getProperty("file.separator") + writer +System.getProperty("file.separator") + fName);
				try {
					reader = new LineNumberReader(new FileReader(temp.getPath()));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				
			else if(directories.length == 0){  //하위 디렉터리가 필요없는 path가 들어왔을때 
				File temp = new File(path + System.getProperty("file.separator") + fName);
				try {
					reader = new LineNumberReader(new FileReader(temp.getPath()));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
			
			String picturePath = null;
			try {
				picturePath = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("====================================== PICTURE PATH : " + picturePath);
			if(!picturePath.equals("[No Picture]")){
				File picturePathFile = new File(picturePath);
				picturePath = picturePathFile.getPath();
			}
			
			String string = "";
			while(true){
				String str = null;
				try {
					str = reader.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(str == null){
					break;
				}
				string += str + "\n";
			}
			Writing w = new Writing(writer, date, time, string, picturePath);
			writingList.add(w);
			
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		list.setListData((Writing[])writingList.toArray(new Writing[0]));
		list.setCellRenderer((ListCellRenderer) new ListCell());
		
		list.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent evt){
				JList list = (JList)evt.getSource();
				if(evt.getClickCount() == 2){
					System.out.println("Double Clicked ! ");
					int index = list.locationToIndex(evt.getPoint());
					ListModel dm = list.getModel();
					Object item = dm.getElementAt(index);
					//System.out.println("Double Clicked : " + ((Writing)item).text);
					if(PV == null){
						PV = new PresentationView(dm, index);
						PV.setView(index);
					}
					
					//PV.setVisible(true);
				}
			}
		});
		
		return list;
	}

	/*
	 * 파일 최신순으로 정렬. 
	 */
	public static File[] sortFileList(File[] files)

	{
		Arrays.sort(files, new Comparator<Object>() {

			@Override
			public int compare(Object object1, Object object2) {

				String s1 = "";
				String s2 = "";

				if (((File) object1).isFile() && ((File) object2).isFile()) {
					String str1 = ((File) object1).getName();
					String[] strTokens1 = str1.split("_");
					String str2 = ((File) object2).getName();
					String[] strTokens2 = str2.split("_");
					s1 = ((File) object1).getName().substring(strTokens1[0].length());
					s2 = ((File) object2).getName().substring(strTokens2[0].length());

				} else {
					s1 = ((File) object1).lastModified() + "";
					s2 = ((File) object2).lastModified() + "";
				}

				return s2.compareTo(s1);
			}

		});
		return files;

	}
}
