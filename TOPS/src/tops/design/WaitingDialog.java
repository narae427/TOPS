package tops.design;

import java.awt.*;
import javax.swing.JDialog;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class WaitingDialog extends JDialog implements Runnable {
	private JTextField txtKkk;
	JLabel lblNewLabel_1;
	String exitStr = "종료 대기중 ... ";

	public WaitingDialog() {
		setMinimumSize(new Dimension(300, 150));
		setMaximumSize(new Dimension(300, 150));
		setPreferredSize(new Dimension(300, 150));
		setAlwaysOnTop(true);

		lblNewLabel_1 = new JLabel(exitStr + "5");
		getContentPane().add(lblNewLabel_1, BorderLayout.CENTER);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 4; i > -1; i--) {
			try {
				Thread.sleep(1000);
				lblNewLabel_1.setText(exitStr + String.valueOf(i));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.exit(0);
	}

}
