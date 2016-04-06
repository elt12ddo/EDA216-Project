package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import database.Database;

public class ProductionPane extends BasicPane {
	private static final long serialVersionUID = 1;
	public ProductionPane(Database db) {
		super(db);
	}
	
	public JComponent createTopPanel(){
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("Search for pallet made between:"));
		
		JPanel p2 = new JPanel();
		String[] options = {"Aaron", "Bbron","Ccron","Ddron","Eeron"};
		JComboBox<String> optionList = new JComboBox<String>(options);
		optionList.addActionListener(new OptionListHandler());
		
		p2.add(optionList);
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(p1);
		p.add(p2);
		return p;
		//return new JLabel("FML");
	}
	
	public JComponent createBottomPanel() {
		JButton button = new JButton("Produce pallet");
		ActionListener actionHandler = null;
		return new ButtonAndMessagePanel(button, messageLabel, actionHandler);
	}
	
	class OptionListHandler implements ActionListener {
		/**
		 * Called when the user clicks the login button. Checks with the
		 * database if the user exists, and if so notifies the CurrentUser
		 * object.
		 * 
		 * @param e
		 *            The event object (not used).
		 */
		public void actionPerformed(ActionEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cBox = (JComboBox<String>) e.getSource();
			String selected = (String) cBox.getSelectedItem();
			System.out.println(selected);
		}
	}
}
