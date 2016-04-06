package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import database.Database;

public class ProductionPane extends BasicPane{
	private static final long serialVersionUID = 1;
	private JTextField[] fields;
	
	private JList<String> nameList;
	private DefaultListModel<String> nameListModel;
	private DefaultListModel<String> dateListModel;
	private JList<String> dateList;
	private static final int USER_ID = 0;
	private JLabel currentUserNameLabel;

	public ProductionPane(Database db) {
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
		JScrollPane p2 = new JScrollPane(dateList);

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		p.add(p1);
		p.add(p2);
		return p;
	}
	
	public JComponent createTopPanel(){
		String[] texts = new String[2];
		texts[0] = "Date1";
		texts[1] = "Date2";

		fields = new JTextField[2];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new JTextField(20);
			fields[i].setEditable(false);
		}

		JPanel input = new InputPanel(texts, fields);

		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("Search for pallet made between:"));
		currentUserNameLabel = new JLabel("");
		p1.add(currentUserNameLabel);

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(p1);
		p.add(input);
		return p;
		//return new JLabel("FML");
	}
	
	public JComponent createBottomPanel() {
	/*
		JButton[] buttons = new JButton[1];
		buttons[0] = new JButton("Login");
		ActionHandler actHand = new ActionHandler();
		fields[USER_ID].addActionListener(actHand);/
		return new ButtonAndMessagePanel(buttons, messageLabel, actHand);/*/
		return new JLabel("Hej");
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
			String movieName = nameList.getSelectedValue();
			/* --- insert own code here --- */
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
			String movieName = nameList.getSelectedValue();
			String date = dateList.getSelectedValue();
			/* --- insert own code here --- */
		}
	}
	
	class ActionHandler implements ActionListener {
		/**
		 * Called when the user clicks the login button. Checks with the
		 * database if the user exists, and if so notifies the CurrentUser
		 * object.
		 * 
		 * @param e
		 *            The event object (not used).
		 */
		public void actionPerformed(ActionEvent e) {
			//String userId = fields[USER_ID].getText();
			/* --- insert own code here --- */
		}
	}

}


