package tops.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.JLabel;

import java.awt.Dimension;

import javax.swing.SwingConstants;

import tops.struct.*;

import java.awt.FlowLayout;
import java.awt.Font;


public class ListCell extends JPanel implements ListCellRenderer<Object>{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextArea textLabel = new JTextArea();
	 JLabel writerLabel = new JLabel("0000");
	 JLabel dateLabel = new JLabel("  0000년 0월 0일 오전 00시 00분");
	 public static Writing selectedW = null;
	 JLabel imgLabel = new JLabel("");
	 Box verticalBox = Box.createVerticalBox();
	  
	  public ListCell() {
	    super(new BorderLayout());
	    setEnabled(false);
	    setBorder(BorderFactory.createEmptyBorder(5,5,5,0));
	    setOpaque(true);
	    
	   
	    verticalBox.setAlignmentX(Component.CENTER_ALIGNMENT);
	    add(verticalBox, BorderLayout.NORTH);	   
	    
	    
	    JPanel panel = new JPanel();
	    panel.setPreferredSize(new Dimension(10, 30));
	    panel.setOpaque(false);
	    verticalBox.add(panel);
	    panel.setLayout(null);
	    writerLabel.setBounds(12, 0, 57, 30);
	    writerLabel.setHorizontalTextPosition(SwingConstants.LEFT);
	    writerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    
	    writerLabel.setHorizontalAlignment(SwingConstants.LEFT);
	    
	    panel.add(writerLabel);
	    dateLabel.setHorizontalTextPosition(SwingConstants.CENTER);
	    dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    dateLabel.setEnabled(false);
	    dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    
	    dateLabel.setFont(new Font("Dialog", Font.ITALIC, 12));
	    dateLabel.setBounds(206, 1, 180, 30);
	    panel.add(dateLabel);
	    
	    imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel_1.setOpaque(false);
	    
	    add(panel_1, BorderLayout.CENTER);
	    panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    textLabel.setMinimumSize(new Dimension(0, 0));
	    panel_1.add(textLabel);
	    
	    
	    textLabel.setAutoscrolls(false);
	    textLabel.setEditable(false);
	    //verticalBox.add(textLabel);
	    textLabel.setOpaque(false);/*이거중요*/	
	
	  }
	 
	  private final Color evenColor = new Color(230,240,255);
	  private final JPanel panel_1 = new JPanel();
	  @Override public Component getListCellRendererComponent(
	      JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {		  
		  
		  textLabel.setText((value==null)?"":((Writing)value).getText());
		  writerLabel.setText(((Writing)value).getWriter());
		  
		  if(!((Writing)value).getPicturePath().equals("[No Picture]"));{
			  ImageIcon image = new ImageIcon(((Writing)value).getPicturePath());
			  Image ii = image.getImage();
			  imgLabel.setIcon(new ImageIcon(ii.getScaledInstance(150, -1, 0)));
			  verticalBox.add(imgLabel);
		  }
		  
		  
		  String date = ((Writing)value).getDate();
		  String year = date.substring(0,4);
		  String month = date.substring(4,6);
		  String day = date.substring(6,8);
		  
		  String time = ((Writing)value).getTime();
		  String hour = time.substring(0,2);
		  String ampm = "AM";
		  if(Integer.valueOf(hour) >=12 && Integer.valueOf(hour) < 24){
			  ampm = "PM";
		  }
		  String min = time.substring(2,4);
		  
		  String DT = year + "년 " + month + "월 " + day + "일 " + ampm + " " + hour + "시 " + min + "분";
		  dateLabel.setText(DT);
		 // textLabel.setText((value==null)?"":value.toString());
		  	      
	    if(isSelected) {
	      setBackground(list.getSelectionBackground());
	      textLabel.setForeground(list.getSelectionForeground());
	      selectedW = (Writing)value;
	    }else{
	      setBackground(index%2==0 ? evenColor : list.getBackground());
	      textLabel.setForeground(list.getForeground());
	    }
	
	    return this;
	  }
	  
	  
}