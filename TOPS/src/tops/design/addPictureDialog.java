package tops.design;

import javax.swing.JDialog;
import javax.swing.JTree;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JButton;

import tops.main.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;


@SuppressWarnings("serial")
public class addPictureDialog extends JDialog{
	JScrollPane scrollPane;
	JTree tree;
	DefaultMutableTreeNode root;
	public addPictureDialog(PreviewDialog previewD) {
		setAlwaysOnTop(true);		
		
		root = new DefaultMutableTreeNode("Pictures");
		tree = new JTree(root);
		
		scrollPane = new JScrollPane(tree,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		PictureDirectoryTree PictureDT = null;
			
		if(TOPS.myOS.equals("Linux")){
			File d = new File(TOPS.myHomePath + System.getProperty("file.separator") + "?��?");
			PictureDT = new PictureDirectoryTree(previewD, tree, root, d.getPath());
		}else{
			//System.out.println("File " + TOPS.myHomePath + System.getProperty("file.separator") + "Pictures");
			//System.out.println("File " +"C:"+ System.getProperty("file.separator") +  "Users" + System.getProperty("file.separator") + System.getProperty("user.name") +System.getProperty("file.separator")+  "Pictures");
			File d = new File("C:"+ System.getProperty("file.separator") +  "Users" + System.getProperty("file.separator") + System.getProperty("user.name") +System.getProperty("file.separator")+  "Pictures");
			PictureDT = new PictureDirectoryTree(previewD, tree, root, d.getPath());
		}
		
		JButton btnNewButton = new JButton("추가");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sourcePath = TOPS.textField_1.getText();
				if(sourcePath.length()<1){
					dispose();
					return;
				}
				
				StringTokenizer st = new StringTokenizer(sourcePath);
				String fileName = "";
				while(st.hasMoreTokens()){
					fileName = st.nextToken(System.getProperty("file.separator"));
				}
				
				/*
				if(TOPS.myOS.equals("Linux")){
					while(st.hasMoreTokens()){
						fileName = st.nextToken("//");
					}
				}
				else{
					while(st.hasMoreTokens()){
						fileName = st.nextToken("\\");
					}
				}
				*/
				File targetPathFile = new File(TOPS.myFolderPath + System.getProperty("file.separator") + String.valueOf(TOPS.myID) + System.getProperty("file.separator") + "Pictures");
				String targetPath = targetPathFile.getPath();
				File f = new File(targetPath);
				if(!f.exists()){
					f.mkdirs();
				}
				try {
					FileCopy(sourcePath, targetPath + System.getProperty("file.separator") + String.valueOf(TOPS.myID) + "_" + fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
				dispose();
			}
		});
		getContentPane().add(btnNewButton, BorderLayout.SOUTH);
		PictureDT.makePictureDirectoryTree();
		tree.addTreeWillExpandListener(PictureDT);
		tree.addTreeSelectionListener(PictureDT);
	}
	
	public static void FileCopy(String source, String target) throws IOException{
    	DataInputStream in = new DataInputStream(new FileInputStream(source));
    	DataOutputStream out = new DataOutputStream(new FileOutputStream(target));
    	
    	byte[] buffer = new byte[1024];
    	int data;
    	
    	while((data = in.read(buffer)) > 0){
    		out.write(buffer, 0, data);
    	}
    	
    	out.close();
    	in.close();
    }

}
