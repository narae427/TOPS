package tops.design;

import javax.swing.JDialog;
import javax.swing.JTree;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.UIManager;

import java.awt.Component;

import javax.swing.ScrollPaneConstants;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;

import tops.main.*;
import tops.struct.*;

import javax.swing.JList;


public class CommonFriendDialog extends JDialog{
	JScrollPane scrollPane;
	public  static JList cmn_list;
	public CommonFriendDialog() {		
		this.setResizable(false);
		
		cmn_list = new JList();
		getContentPane().add(cmn_list, BorderLayout.NORTH);
		
	}

}
