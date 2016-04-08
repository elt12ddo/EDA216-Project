package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import database.Database;

public class PalletPane extends BasicPane{
	private static final long serialVersionUID = 1;
	private JTextField[] fields;
	
	private JList<String> nameList;
	private DefaultListModel<String> nameListModel;
	private DefaultListModel<String> dateListModel;
	private JList<String> dateList;
	public PalletPane(Database db) {
		super(db);
		
	}
	
	public JComponent createLeftPanel() {
		nameListModel = new DefaultListModel<String>();

		nameList = new JList<String>(nameListModel);
		nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nameList.setPrototypeCellValue("123456789012");
		nameList.addListSelectionListener(new NameSelectionListener());
		JScrollPane p1 = new JScrollPane(nameList);

		dateListModel = new DefaultListModel<String>();

		dateList = new JList<String>(dateListModel);
		dateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dateList.setPrototypeCellValue("123456789012");
		dateList.addListSelectionListener(new DateSelectionListener());
		new JScrollPane(dateList);

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		p.add(p1);
		//p.add(p2);
		return p;
	}
	
	public JComponent createTopPanel(){
		String[] texts = new String[2];
		texts[0] = "Date1";
		texts[1] = "Date2";

		fields = new JTextField[2];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new JTextField(20);
		}

		JPanel input = new InputPanel(texts, fields);

		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("Search for pallet made between:"));
		p1.add(new JLabel(""));

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(p1);
		p.add(input);
		return p;
		//return new JLabel("FML");
	}
	
	public JComponent createBottomPanel() {
	
		JButton[] buttons = new JButton[3];
		buttons[0] = new JButton("Search");
		buttons[1] = new JButton("Block");
		buttons[2] = new JButton("Unblock");
		ActionListener[] actionHandlers = new ActionListener[3];
		actionHandlers[0] = new SearchHandler();
		actionHandlers[1] = new BlockHandler();
		actionHandlers[2] = new UnblockHandler();
		return new ButtonAndMessagePanel(buttons, messageLabel, actionHandlers);
	}
	
	class NameSelectionListener implements ListSelectionListener {
		/**
		 * Called when the user selects a name in the name list. Fetches
		 * performance dates from the database and displays them in the date
		 * list.
		 * 
		 * @param e
		 *            The selected list item.
		 */
		public void valueChanged(ListSelectionEvent e) {
			if (nameList.isSelectionEmpty()) {
				return;
			}
			nameList.getSelectedValue();
		}
	}

	class DateSelectionListener implements ListSelectionListener {
		/**
		 * Called when the user selects a name in the date list. Fetches
		 * performance data from the database and displays it in the text
		 * fields.
		 * 
		 * @param e
		 *            The selected list item.
		 */
		public void valueChanged(ListSelectionEvent e) {
			if (nameList.isSelectionEmpty() || dateList.isSelectionEmpty()) {
				return;
			}
			nameList.getSelectedValue();
			dateList.getSelectedValue();
		}
	}
	
	class SearchHandler implements ActionListener {
		/**
		 * Called when the user clicks the login button. Checks with the
		 * database if the user exists, and if so notifies the CurrentUser
		 * object.
		 * 
		 * @param e
		 *            The event object (not used).
		 */
		public void actionPerformed(ActionEvent e) {
			System.out.println("Do some searching with: " + fields[0].getText() + " and " + fields[1].getText());
			//String userId = fields[USER_ID].getText();
			/* --- insert own code here --- */
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
			System.out.println("Do some blocking with: " + fields[0].getText() + " and " + fields[1].getText());
			//String userId = fields[USER_ID].getText();
			/* --- insert own code here --- */
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
			System.out.println("Do some unblocking with: " + fields[0].getText() + " and " + fields[1].getText());
			//String userId = fields[USER_ID].getText();
			/* --- insert own code here --- */
		}
	}
}