package tops.design;

import java.io.File;
import java.io.FileFilter;
import java.util.StringTokenizer;

import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

public class FreindDirectoryTree implements TreeWillExpandListener,
		TreeSelectionListener {
	JTree tree = null;
	DefaultMutableTreeNode root = null;
	String rootPath = null;
//	int myPortNumber = 0;
	ListSetting LS = null;
	JList<?> list = null;

	public FreindDirectoryTree(JTree tree, DefaultMutableTreeNode root,
			String rootPath, JList<?> list) {
		this.tree = tree;
		this.root = root;
		this.rootPath = rootPath;
//		this.myPortNumber = TOPS.myPublicPN;
		this.list = list;
	}

	@SuppressWarnings("static-access")
	public void makeDirectoryTree() {

		root.removeAllChildren();
		File d = new File(rootPath);
		File[] dirs = d.listFiles(new FileFilter() {

			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				return f.isDirectory() && !f.getName().equals("UpdateFile");
			}

		});
		LS = new ListSetting();
		dirs = LS.sortFileList(dirs);

		for (int i = 0; i < dirs.length; i++) {
			if (dirs[i].isDirectory()) {
				DefaultMutableTreeNode dmt = new DefaultMutableTreeNode(
						dirs[i].getName());
				// dmt.add(new DefaultMutableTreeNode("EMPTY"));
				// dmt.setAllowsChildren(true);
				root.add(dmt);

			}

		}
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		JTree t = null;
		t = (JTree) e.getSource();

		String path = null;
		int pathCount = t.getSelectionPath().getPathCount();

		if (pathCount == 1) { // 猷⑦�����곕━
			path = rootPath;
		} else {
			String parent = t.getSelectionPath().getParentPath()
					.getLastPathComponent().toString();
			String test = t.getLastSelectedPathComponent().toString();
			if (test.equals("EMPTY"))
				return;

			if (pathCount == 2) {
				path = rootPath + System.getProperty("file.separator") + test;
			} else if (pathCount == 3) {
				path = rootPath + System.getProperty("file.separator") + parent
						+ System.getProperty("file.separator") + test;
			}
		}

		list.setName("MAINLIST");
		LS.setList(list, path);

	}

	@Override
	public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
		// TODO Auto-generated method stub
		// JTree t = null;
		if (e.getSource() == tree) {

			tree.setSelectionPath(e.getPath());
			TreePath tp = tree.getSelectionPath();
			StringTokenizer stk = new StringTokenizer(tp.toString(), "[,]");
			stk.nextToken();
			if (stk.hasMoreTokens()) {
				String filePath = stk.nextToken().trim();
				while (stk.hasMoreTokens()) {
					filePath += System.getProperty("file.separator");
					filePath += stk.nextToken().trim();
				}
				filePath = System.getProperty("file.separator") + filePath;
				File dir = new File(rootPath + filePath);
				File[] data = dir.listFiles();

				if (data == null) {
					return;
				}

				DefaultMutableTreeNode temp = (DefaultMutableTreeNode) e
						.getPath().getLastPathComponent();
				temp.removeAllChildren();

				if (data.length == 0) {
					temp.add(new DefaultMutableTreeNode("EMPTY"));

				} else {
					int count = -1;
					for (int i = 0; i < data.length; i++) {
						if (data[i].isDirectory()) {
							DefaultMutableTreeNode dtm = new DefaultMutableTreeNode(
									data[i].getName());
							dtm.add(new DefaultMutableTreeNode("EMPTY"));
							temp.add(dtm);
							count++;

						}

					}
					if (count == -1) {

						temp.add(new DefaultMutableTreeNode("EMPTY"));
						throw new ExpandVetoException(e, "node not expandable");

					}
				}

			}
		}

	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent event)
			throws ExpandVetoException {
		// TODO Auto-generated method stub

	}
}
