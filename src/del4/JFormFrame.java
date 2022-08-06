package del4;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class JFormFrame extends JFrame{
	
	JPanel mainPanel; 
	
	public JFormFrame(ArrayList<String> args) { 

		setUpFrame(); 
		
		addComponentsUsing(args); 
	}
	
	private void addComponentsUsing(ArrayList<String> args) {
		
		for(int i = 0; i < args.size(); i++)
			addLabelAndTextField(args.get(i)); 
		
		
	}

	private void addLabelAndTextField(String str) {
		
		JLabel label = new JLabel(str); 
		JTextField tf = new JTextField(); 
	
		addComponents(mainPanel, label, tf); 
	}

	private void setUpFrame() {
		
		mainPanel = new JPanel();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);  
		
		Border padding = BorderFactory.createEmptyBorder(10, 40, 10, 40);

		mainPanel.setBorder(padding);
		
		this.setBounds(100, 100, 200, 200);

		this.add(mainPanel); 
		
	}
	
	// TODO put this in utility
	private void addComponents(JComponent sup, JComponent ...components) {
		
		for (JComponent component : components) {
		      sup.add(component); 
		 }
	}
	
	
}
