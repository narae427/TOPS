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


public class FreindTreeDialog extends JDialog{
	JScrollPane scrollPane;
	JTree tree;
	DefaultMutableTreeNode root;
	public FreindTreeDialog() {		
		this.setResizable(false);
		root = new DefaultMutableTreeNode("Freind List");
		tree = new JTree(root);
		
		scrollPane = new JScrollPane(tree,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		FreindDirectoryTree EntireDT = new FreindDirectoryTree(tree, root, TOPS.myFolderPath, TOPS.freindsList);
		EntireDT.makeDirectoryTree();
		tree.addTreeWillExpandListener(EntireDT);
		tree.addTreeSelectionListener(EntireDT);
	}

}
