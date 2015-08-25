package tops.design;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JWindow;
import javax.swing.ListModel;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;

import tops.design.*;
import tops.struct.*;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class PresentationView extends JFrame implements FocusListener, KeyListener{
	

	private final JLabel imgLabel = new JLabel("");
	private final Box verticalBox = Box.createVerticalBox();
	private final Box verticalBox_1 = Box.createVerticalBox();
	private final JPanel panel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JLabel label = new JLabel("0000");
	private final JLabel label_1 = new JLabel("  0000년 0월 0일 오전 00시 00분");
	Dimension screen = Toolkit.getDefaultToolkit()
			.getScreenSize();
	private final Component verticalStrut = Box.createVerticalStrut(20);
	private final Component verticalStrut_1 = Box.createVerticalStrut(20);
	private final Box verticalBox_2 = Box.createVerticalBox();
	private final JPanel panel_2 = new JPanel();
	private final JButton btnNewButton = new JButton("이전");
	private final JButton btnNewButton_1 = new JButton("다음");
	private final Component verticalStrut_2 = Box.createVerticalStrut(50);
	ListModel wModel = null;
	int itemIndex = 0;
	private final JTextArea textArea = new JTextArea();
	private final JPanel panel_3 = new JPanel();
	public PresentationView(ListModel model, int i) {
		setResizable(false);
		wModel = model;
		this.itemIndex = i;
		//wItem = (Writing)model.getElementAt(itemIndex);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setUndecorated(true);
		setSize(screen.width, screen.height);
		setFocusableWindowState(true);
		setFocusable(true);

		setVisible(true);
			
		addFocusListener(this);
		addKeyListener(this);
		verticalBox.setPreferredSize(new Dimension(screen.width, 100));
		verticalBox_2.setPreferredSize(new Dimension(screen.width, 50));
		verticalBox.setAlignmentX(0.5f);
		getContentPane().add(verticalBox, BorderLayout.NORTH);
		
		verticalBox.add(verticalStrut);
				
		panel.setPreferredSize(new Dimension(10, 30));
		panel.setOpaque(false);
		verticalBox.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		label.setFont(new Font("굴림", Font.BOLD, 20));
				
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(0.5f);
		panel.add(label);
				
		label_1.setHorizontalTextPosition(SwingConstants.CENTER);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Dialog", Font.ITALIC, 20));
		label_1.setEnabled(false);
		label_1.setAlignmentX(0.5f);
		panel.add(label_1);
		
		
		
		getContentPane().add(panel_1, BorderLayout.CENTER);
		
		GridBagLayout gbl_panel_1 = new GridBagLayout();

		gbl_panel_1.rowHeights = new int[] {(int) (screen.height - (verticalBox.getPreferredSize().getHeight() + verticalBox_2.getPreferredSize().getHeight()))};
		gbl_panel_1.columnWidths = new int[] {screen.width};
		gbl_panel_1.columnWeights = new double[]{0.0};
		gbl_panel_1.rowWeights = new double[]{0.0};
		panel_1.setLayout(gbl_panel_1);
		  imgLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		  imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		 //System.out.println("=============================="+((Writing)value).getPicturePath());
		 
		//setView(itemIndex);

		  imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		  verticalBox_1.add(imgLabel);
		  GridBagConstraints gbc_verticalBox_1 = new GridBagConstraints();
		  gbc_verticalBox_1.gridx = 0;
		  gbc_verticalBox_1.gridy = 0;
		  panel_1.add(verticalBox_1, gbc_verticalBox_1);
		  
		  verticalBox_1.add(verticalStrut_1);
		  panel_3.setOpaque(false);
		  
		  verticalBox_1.add(panel_3);
		  panel_3.add(textArea);
		  textArea.setOpaque(false);
		  
		  
		  getContentPane().add(verticalBox_2, BorderLayout.SOUTH);
		  
		  verticalBox_2.add(panel_2);
		  GridBagLayout gbl_panel_2 = new GridBagLayout();
		  gbl_panel_2.columnWidths = new int[] {screen.width/7, screen.width/7, screen.width/7, screen.width/7, screen.width/7, screen.width/7, screen.width/7};
		  gbl_panel_2.rowHeights = new int[] {30};
		  gbl_panel_2.columnWeights = new double[]{0.0};
		  gbl_panel_2.rowWeights = new double[]{0.0};
		  panel_2.setLayout(gbl_panel_2);
		  btnNewButton.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent arg0) {
		  		--itemIndex;
		  		if(itemIndex < 0){
		  			return;
		  		}
		  		
		  		try{
		  			//btnNewButton.setEnabled(true);
		  			btnNewButton_1.setEnabled(true);
		  			setView(itemIndex);
		  		}catch(Exception e){
		  			//btnNewButton.setEnabled(false);
		  		}
		  	}
		  });
		  btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		  
		  GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		  gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		  gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		  gbc_btnNewButton.gridx = 5;
		  gbc_btnNewButton.gridy = 0;
		  panel_2.add(btnNewButton, gbc_btnNewButton);
		  
		  GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		  gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		  gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		  gbc_btnNewButton_1.gridx = 6;
		  gbc_btnNewButton_1.gridy = 0;
		  btnNewButton_1.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent arg0) {
		  		++itemIndex;
		  		if(itemIndex >= wModel.getSize()){
		  			return;
		  		}
		  		
		  		try{
		  			btnNewButton.setEnabled(true);
		  			//btnNewButton_1.setEnabled(true);
		  			setView(itemIndex);
		  		}catch(Exception e){
		  			//btnNewButton_1.setEnabled(false);
		  		}
		  		
		  	}
		  });
		  panel_2.add(btnNewButton_1, gbc_btnNewButton_1);
		  
		  verticalBox_2.add(verticalStrut_2);

		  this.pack();
	}

	public void setView(int itemIndex){
		if(itemIndex == 0){
  			btnNewButton.setEnabled(false);
  		}
		if(itemIndex == wModel.getSize()-1){
  			btnNewButton_1.setEnabled(false);
  		}
		//System.out.println(itemIndex);
		Writing wItem = (Writing)wModel.getElementAt(itemIndex);
		label.setText(wItem.getWriter());
		
		String date = wItem.getDate();
		String year = date.substring(0,4);
		String month = date.substring(4,6);
		String day = date.substring(6,8);
		  
		String time = wItem.getTime();
		String hour = time.substring(0,2);
		String ampm = "AM";
		if(Integer.valueOf(hour) >=12 && Integer.valueOf(hour) < 24){
			ampm = "PM";
		}
		String min = time.substring(2,4);
		  
		String DT = year + "년 " + month + "월 " + day + "일 " + ampm + " " + hour + "시 " + min + "분";
		label_1.setText(DT);
		textArea.setText(wItem.getText());

		if(!wItem.getPicturePath().equals("[No Picture]"));{
			ImageIcon image = new ImageIcon(wItem.getPicturePath());
			Image ii = image.getImage();
			imgLabel.setIcon(new ImageIcon(ii));
			//imgLabel.setIcon(new ImageIcon(ii.getScaledInstance(150, -1, 0)));
			//imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			//verticalBox_1.add(imgLabel);
		}
	}
	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
		if( k.getKeyCode() == KeyEvent.VK_ESCAPE){
			ListSetting.PV = null;
			setVisible(false);
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		requestFocus();
	}

}
