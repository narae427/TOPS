package tops.design;

import javax.swing.JDialog;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Color;


@SuppressWarnings("serial")
public class CommonFriendDialog extends JDialog{
	JScrollPane scrollPane;
	@SuppressWarnings("rawtypes")
	public  static JList cmn_list;
	@SuppressWarnings("rawtypes")
	public CommonFriendDialog() {		
		this.setResizable(false);
		
		
		
		cmn_list = new JList();
		JLabel lblNewLabel = new JLabel("Common Friend List");
		lblNewLabel.setBackground(Color.WHITE);
		
		
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
//		getContentPane().add(cmn_list, BorderLayout.NORTH);
		
		JScrollPane scrollPane_1 = new JScrollPane(cmn_list,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(scrollPane_1, BorderLayout.CENTER);
	}

}
