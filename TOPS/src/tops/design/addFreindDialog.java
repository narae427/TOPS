package tops.design;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Dimension;

import javax.swing.Box;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JLabel;

import tops.main.*;

import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class addFreindDialog extends JDialog {
	String freindID = "";

	public addFreindDialog(String fID) {
		setMinimumSize(new Dimension(300, 150));
		setMaximumSize(new Dimension(300, 150));
		setPreferredSize(new Dimension(300, 150));
		setAlwaysOnTop(true);

		this.freindID = fID;
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnNewButton_1 = new JButton("\uC218\uB77D");
		btnNewButton_1.setPreferredSize(new Dimension(100, 23));
		btnNewButton_1.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				addFreind();
				TOPS.top_client.sendMessage("'dm_AllowFriend'" +"@"+ freindID+"@"  );

				dispose();
			}
		});
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnNewButton_1);

		Component horizontalStrut = Box.createHorizontalStrut(10);
		panel.add(horizontalStrut);

		JButton btnNewButton = new JButton("\uAC70\uC808");
		btnNewButton.setPreferredSize(new Dimension(100, 23));
		panel.add(btnNewButton);
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Component rigidArea = Box.createRigidArea(new Dimension(10, 220));
		rigidArea.setPreferredSize(new Dimension(10, 70));
		panel_1.add(rigidArea);

		JLabel lblNewLabel = new JLabel(freindID);
		panel_1.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(" \uC758 \uCE5C\uAD6C\uC694\uCCAD !");
		panel_1.add(lblNewLabel_1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
				return;
			}
		});
	}

	public void addFreind() {
		File freindListFile = new File(TOPS.myFolderPath
				+ System.getProperty("file.separator") + TOPS.myID
				+ "_FreindList");

		FileWriter fw;
		try {
			fw = new FileWriter(freindListFile, true);
			String freindId = freindID + "\n";
			fw.write(freindId);
			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
