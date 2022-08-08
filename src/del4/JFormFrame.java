package del4;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class JFormFrame extends JFrame{
	
	public JPanel mainPanel; 
	public JButton goButton; 
	
	private String tableName; 
	
	private ArrayList<JComponent> components; 
	Database database; 
	
	public JFormFrame(ArrayList<String> args, String tableName) { 

		setUpFrame(); 
		
		components = new ArrayList<JComponent>();  
		
		this.tableName = tableName; 
		
		setUpAllComponents(args); 
	}
	
	private void setUpAllComponents(ArrayList<String> args) {
		

		for(int i = 0; i < args.size(); i++) {
			
			// TODO - make this better, not at all dynamic 
			// but fixes one off case 
			if(this.tableName==QueryStatements.ITEM_TABLE && (i==1 || i==2))
				addCheckBox(args.get(i)); 
			else
				addLabelAndTextField(args.get(i)); 
		}

		
		goButton = new JButton("go");
		
		addComponents(mainPanel, goButton); 
		
	}

	private void addCheckBox(String str) {
		JCheckBox cb = CustomSwing.getCustomrCheckBox(str); 
		components.add(cb);
		
		addComponents(mainPanel, cb); 
	}

	private void addLabelAndTextField(String str) {
		
		JLabel label = new JLabel(str); 
		JTextField tf = new JTextField(); 
		
		components.add(tf);
	
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

	public ArrayList<Object> getTextFieldArgs() {
		
		ArrayList<Object> args = new ArrayList<Object>(); 
		
		for(int i = 0; i < components.size(); i++)
			args.add(parseArgsFromJComponent(components.get(i))); 
		
		return args;
	}
	
	
	private Object parseArgsFromJComponent(JComponent component){
		
		if(component instanceof JTextField)
			return ((JTextField)component).getText(); 
		
		// 1=true 0=false
		if(component instanceof JCheckBox)
			return ((JCheckBox)component).isSelected() ? 1 : 0;;
		
		return null; 
	}
	
}
