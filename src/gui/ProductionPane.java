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
	}
	
	public JComponent createTopPanel(){
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("Create new pallets:"));
		
		JPanel p2 = new JPanel();
		//Could check with db.isConnected(); but what should I do if it is not connected, nothing should work properly anyway in that case.
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
		return new ButtonAndMessagePanel(button, messageLabel, new ProducePalletHandler(optionList,this));
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
		ProductionPane pPane;
		
		public ProducePalletHandler(JComboBox<String> comboBox, ProductionPane pPane){
			this.comboBox = comboBox;
			this.pPane = pPane;
		}
		
		public void actionPerformed(ActionEvent e) {
			String cookie = String.valueOf(comboBox.getSelectedItem());
			if(db.createPallet(cookie)){
				pPane.displayMessage("A new pallet with " + cookie + " cookies has been added to the database.");
			} else {
				pPane.displayMessage("The pallet with " + cookie + " could not be added to the database.");
			}
		}
	}
}
