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
	public JButton commitDDLButton; 
	
	private String viewingTableName; 
	
	private ArrayList<JComponent> formJComponents; 
	
	private ArrayList<String> serializedPresetText;  
	
	final static String GO_TEXT = "Go"; 
	
	public JFormFrame(ArrayList<String> args, String tableName, ArrayList<String> serializedPresetText) {  

		this.serializedPresetText = serializedPresetText; 
		
		setupGUI(); 
		
		formJComponents = new ArrayList<JComponent>();  
		
		this.viewingTableName = tableName; 
		
		setUpAllComponents(args); 
	
	}
	
	
	private void setUpAllComponents(ArrayList<String> args) {
		

		for(int i = 0; i < args.size(); i++) {
			
			// TODO - make this better, not at all dynamic 
			// but fixes one off case 
			if(this.viewingTableName==Const.ITEM_TABLE && (i==1 || i==2))
				addCheckBox(args.get(i)); 
			else
				addLabelAndTextField(args.get(i)); 
		}

		
		commitDDLButton = new JButton(GO_TEXT);
		
		if(serializedPresetText!=null)
			setupSerializedTextFieldPresets(); 
		
		addComponents(mainPanel, commitDDLButton); 
		
	}

	
	private void addCheckBox(String str) {
		JCheckBox cb = CustomSwing.getCustomrCheckBox(str); 
		formJComponents.add(cb);
		
		addComponents(mainPanel, cb); 
	}

	
	private void addLabelAndTextField(String str) {
		
		JLabel label = new JLabel(str); 
		JTextField tf = new JTextField(); 
		
		formJComponents.add(tf);
	
		addComponents(mainPanel, label, tf); 
		
	
	}

	/**
	 * note : there is no checking here for alignment of text components and 
	 *        number of text fields. would need to implement a method to first 
	 *        check all JComponents that are text fields, or come at this 
	 *        with entirely different approach
	 */
	private void setupSerializedTextFieldPresets() {
		
		int text_count = 0;
		
		for(int i = 0; ((i < formJComponents.size()) && (text_count < serializedPresetText.size())); i++) {
			
			JComponent comp = formJComponents.get(i); 
			
			if(comp instanceof JTextField)
				((JTextField) comp).setText(serializedPresetText.get(text_count++));
		}	
	
	}
	

	/**
	 * set up all of the GUI  components that appear 
	 * on this form including buttons and text fields
	 */
	private void setupGUI() {
		
		mainPanel = new JPanel();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		this.setLocationRelativeTo(null);  
		
		Border padding = BorderFactory.createEmptyBorder(10, 40, 10, 40);

		mainPanel.setBorder(padding);
		
		this.setBounds(100, 100, 200, 200);

		this.add(mainPanel); 
		
	}
	
	
	private void addComponents(JComponent sup, JComponent ...components) {
		
		for (JComponent component : components) {
		      sup.add(component); 
		 }
	}

	public ArrayList<Object> getInputArgs() {
		
		ArrayList<Object> args = new ArrayList<Object>(); 

		for(int i = 0; i < formJComponents.size(); i++)
			args.add(parseArgsFromJComponent(formJComponents.get(i))); 
		
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
