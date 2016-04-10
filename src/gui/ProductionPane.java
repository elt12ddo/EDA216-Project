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
	private JComboBox<String> optionList;
	
	public ProductionPane(Database db) {
		super(db);
	}
	
	public JComponent createTopPanel(){
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("Create new pallets:"));
		
		JPanel p2 = new JPanel();
		optionList = new JComboBox<String>(db.getCookieTypes());
		p2.add(optionList);
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(p1);
		p.add(p2);
		return p;
	}

	public JComponent createBottomPanel() {
		JButton button = new JButton("Produce pallet");
		return new ButtonAndMessagePanel(button, messageLabel, new ProducePalletHandler());
	}
	
	private class ProducePalletHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cookie = String.valueOf(optionList.getSelectedItem());
			if(db.createPallet(cookie)){
				displayMessage("A new pallet with " + cookie + " cookies has been added to the database.");
			} else {
				displayMessage("The pallet with " + cookie + " could not be added to the database.");
			}
		}
	}
}
