package tops.design;

import javax.swing.JDialog;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JList;


@SuppressWarnings("serial")
public class CommonFriendDialog extends JDialog{
	JScrollPane scrollPane;
	@SuppressWarnings("rawtypes")
	public  static JList cmn_list;
	@SuppressWarnings("rawtypes")
	public CommonFriendDialog() {		
		this.setResizable(false);
		
		cmn_list = new JList();
		getContentPane().add(cmn_list, BorderLayout.NORTH);
		
	}

}
