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
	private JComboBox<String> optionList; //If anyone know a better solution that doesn't require an rewrite of the entire program this would be the time to inform me.
	
	public ProductionPane(Database db) {
		super(db);
		optionList = new JComboBox<String>(db.getCookieTypes()); // Because I prefer to create global things in the constructor to be sure that we don't get strange nullpointers.
	}
	
	public JComponent createTopPanel(){
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("Create new pallets:"));
		
		JPanel p2 = new JPanel();
		optionList = new JComboBox<String>(db.getCookieTypes()); // Global, DO NOT CREATE IN THE CONSTRUCTOR IT WILL CAUSE NULLPOINTER (super is always called first, so this would execute before anything in the constructor).
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
		return new ButtonAndMessagePanel(button, messageLabel, new ProducePalletHandler(optionList));
	}
	
	class OptionListHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cBox = (JComboBox<String>) e.getSource();
			String selected = String.valueOf(cBox.getSelectedItem());
			System.out.println(selected);
		}
	}
	
	class ProducePalletHandler implements ActionListener {
		JComboBox<String> comboBox; //DO NOT CREATE A NEW OPTIONLIST IN PRODUCTIONPANE, update the OptionList instead or I will have to implement a method to update this and call it every time we want to update the selection.
		
		public ProducePalletHandler(JComboBox<String> comboBox){
			this.comboBox = comboBox;
		}
		
		public void actionPerformed(ActionEvent e) {
			db.createPallet(String.valueOf(comboBox.getSelectedItem()));
		}
	}
}
