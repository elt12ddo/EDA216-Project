package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import database.Database;

public class PalletPane extends BasicPane{
	private static final long serialVersionUID = 1;
	private JTextField[] searchFields;
	private JTextField[] palletFields;
	
	private JList<String> palletList;
	private DefaultListModel<String> palletListModel;
	public PalletPane(Database db) {
		super(db);
		
	}
	
	public JComponent createLeftPanel() {
		palletListModel = new DefaultListModel<String>();

		palletList = new JList<String>(palletListModel);
		palletList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		palletList.setPrototypeCellValue("123456789012");
		palletList.addListSelectionListener(new PalletSelectionListener());
		JScrollPane p1 = new JScrollPane(palletList);

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		p.add(p1);
		return p;
	}
	
	public JComponent createTopPanel(){
		String[] texts = new String[2];
		texts[0] = "Date1";
		texts[1] = "Date2";

		searchFields = new JTextField[2];
		for (int i = 0; i < searchFields.length; i++) {
			searchFields[i] = new JTextField(20);
		}

		JPanel input = new InputPanel(texts, searchFields);

		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("Search for pallet made between (YYYY-MM-DD):"));
		p1.add(new JLabel(""));
		
		JButton searchButton = new JButton("Search");
		JComboBox<String> typeSelector = new JComboBox<String>(db.getCookieTypes());
		typeSelector.addItem("All");//To look through all the cookie types.
		searchButton.addActionListener(new SearchHandler(typeSelector));
		JPanel searchPanel = new JPanel();
		searchPanel.add(typeSelector);
		searchPanel.add(searchButton);
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(p1);
		p.add(input);
		p.add(searchPanel);
		return p;
	}
	
	public JComponent createMiddlePanel(){
		String[] texts = new String[8];
		texts[0] = "ID";
		texts[1] = "Date";
		texts[2] = "Timestamp";
		texts[3] = "Order";
		texts[4] = "Location";
		texts[5] = "Date delivered";
		texts[6] = "Cookie type";
		texts[7] = "Status";

		palletFields = new JTextField[8];
		for (int i = 0; i < palletFields.length; i++) {
			palletFields[i] = new JTextField(20);
			palletFields[i].setEditable(false);
		}

		JPanel info = new InputPanel(texts, palletFields);
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(info);
		
		return p;
	}
	
	public JComponent createBottomPanel() {
	
		JButton[] buttons = new JButton[2];
		buttons[0] = new JButton("Block");
		buttons[1] = new JButton("Unblock");
		ActionListener[] actionHandlers = new ActionListener[2];
		actionHandlers[0] = new BlockHandler();
		actionHandlers[1] = new UnblockHandler();
		return new ButtonAndMessagePanel(buttons, messageLabel, actionHandlers);
	}
	
	/**
	 * Fetch pallet id from the database and display them in the pallet list.
	 */
	public void fillPalletList(String date1, String date2, String cookieType) {
		palletListModel.removeAllElements();
		palletListModel = db.fetchPallets(date1, date2, cookieType);
		palletList.setModel(palletListModel);
	}
	
	public void updateFields(){
		if (palletList.isSelectionEmpty()) {
			return;
		}
		palletList.getSelectedValue();
		String palletId = palletList.getSelectedValue();
		String[] data = db.getPalletData(palletId);
		palletFields[0].setText(palletId);
		for(int k = 0; k < 7; k++){
			palletFields[k+1].setText(data[k]);
		}
	}

	class PalletSelectionListener implements ListSelectionListener {
		/**
		 * Called when the user selects a name in the date list. Fetches
		 * performance data from the database and displays it in the text
		 * fields.
		 * 
		 * @param e
		 *            The selected list item.
		 */
		public void valueChanged(ListSelectionEvent e) {
			updateFields();
		}
	}
	
	class SearchHandler implements ActionListener {
		private JComboBox<String> comboBox;
		public SearchHandler(JComboBox<String> comboBox){
			this.comboBox = comboBox;
		}
		/**
		 * Called when the user clicks the login button. Checks with the
		 * database if the user exists, and if so notifies the CurrentUser
		 * object.
		 * 
		 * @param e
		 *            The event object (not used).
		 */
		public void actionPerformed(ActionEvent e) {
			fillPalletList(searchFields[0].getText(), searchFields[1].getText(), String.valueOf(comboBox.getSelectedItem()));
			displayMessage("Search complete, " + palletListModel.size() + " pallets found.");
		}
	}
	
	class BlockHandler implements ActionListener {
		/**
		 * Called when the user clicks the login button. Checks with the
		 * database if the user exists, and if so notifies the CurrentUser
		 * object.
		 * 
		 * @param e
		 *            The event object (not used).
		 */
		public void actionPerformed(ActionEvent e) {
			String palletId = palletList.getSelectedValue();
			if(palletList.isSelectionEmpty()){
				displayMessage("No pallet selected.");
				return;
			} else if(db.setBlocked(palletId, true)){
				displayMessage("Pallet " + palletId + " has been successfully blocked.");
			} else {
				displayMessage("Pallet " + palletId + " could not be blocked.");
			}
			updateFields();
		}
	}
	
	class UnblockHandler implements ActionListener {
		/**
		 * Called when the user clicks the login button. Checks with the
		 * database if the user exists, and if so notifies the CurrentUser
		 * object.
		 * 
		 * @param e
		 *            The event object (not used).
		 */
		public void actionPerformed(ActionEvent e) {
			String palletId = palletList.getSelectedValue();
			if(palletList.isSelectionEmpty()){
				displayMessage("No pallet selected.");
				return;
			} else if(db.setBlocked(palletId, false)){
				displayMessage("Pallet " + palletId + " has been successfully unblocked.");
			} else {
				displayMessage("Pallet " + palletId + " could not be unblocked.");
			}
			updateFields();
		}
	}
}