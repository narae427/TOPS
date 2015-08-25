package tops.struct;

import java.io.*;

import tops.main.*;


public class Writing {
	String writer;
	String date;
	String time;
	String text;
	String picturePath;
	
	public Writing(String writer, String date, String time, String text, String picturePath){
		this.writer = writer;
		this.date = date;
		this.time = time;
		this.text = text;
		this.picturePath = picturePath;
	}
	
	public String getWriter(){
		return writer;
	}
	
	public String getDate(){
		return date;
	}
	
	public String getTime(){
		return time;
	}
	
	public String getText(){
		return text;
	}
	
	public String getTitle(){
		return writer + "_" + date + "_" + time;
	}
	
	public String getPicturePath(){
		return picturePath;
	}
	
	static public String getEntireFileList() throws IOException{
		File[] myUpdateFile = null;
		
		File myUpdateFilePath = new File(TOPS.myFolderPath
				+ System.getProperty("file.separator") + "UpdateFile");

		myUpdateFile = myUpdateFilePath.listFiles(new FilenameFilter() { // 내가가지고있는updateFiles

					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						return  name.contains("UpdateFile");
					}

				});
		
		String entireFileList = "";
		System.out.println("Writing getEntireFileList = " + myUpdateFile.length);
		for(int i = 0; i < myUpdateFile.length; i++){
			entireFileList += (myUpdateFile[i].getName() + ";");
		}
				
		System.out.println("ectire File LIST : " + entireFileList);
		return entireFileList;
	}
	
	
}
