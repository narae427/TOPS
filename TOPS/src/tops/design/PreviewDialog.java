package tops.design;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.GridLayout;

import javax.swing.SwingConstants;
import java.awt.Color;


public class PreviewDialog extends JDialog{
	JLabel lblNewLabel = new JLabel("");
	public static JTextArea previewD_textArea = new JTextArea();
	private final JPanel panel_1 = new JPanel();
	public PreviewDialog() {
		getContentPane().setBackground(Color.WHITE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(2, 2, 10, 10));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);		
		panel_1.setBackground(Color.WHITE);
		
		panel.add(panel_1);
		panel_1.add(previewD_textArea);
		previewD_textArea.setEditable(false);
	}
	
	public void setImageView(String imagePath){
		ImageIcon image = new ImageIcon(imagePath);
		Image ii = image.getImage();
		lblNewLabel.setIcon(new ImageIcon(ii.getScaledInstance(150, -1, 0)));
	}

}
