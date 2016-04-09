package gui;

import javax.swing.*;
import javax.swing.event.*;

import database.Database;

import java.awt.*;
import java.awt.event.*;

/**
 * MovieGUI is the user interface to the movie database. It sets up the main
 * window and connects to the database.
 */
public class CookieGUI {
	/**
	 * db is the database object
	 */
	private Database db;

	/**
	 * tabbedPane is the contents of the window. It consists of two panes, User
	 * login and Book tickets.
	 */
	private JTabbedPane tabbedPane;

	/**
	 * Create a GUI object and connect to the database.
	 * 
	 * @param db
	 *            The database.
	 */
	public CookieGUI(Database db) {
		this.db = db;
		boolean connected = db.openConnection("db08", "zartacus");

		JFrame frame = new JFrame("Cookies!");
		tabbedPane = new JTabbedPane();

		ProductionPane productionPane = new ProductionPane(db);
		tabbedPane.addTab("Production", null, productionPane, "This is where you can add a new pallet.");
		
		PalletPane palletPane = new PalletPane(db);
		tabbedPane.addTab("Pallets", null, palletPane, "Here you can search through existing pallets, block pallets and unblock them again.");

		tabbedPane.setSelectedIndex(0);

		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addChangeListener(new ChangeHandler());
		frame.addWindowListener(new WindowHandler());

		frame.setSize(600, 500);
		frame.setVisible(true);
		
		/* --- change code here --- */
		/* --- change xxx to your user name, yyy to your password --- */
		if (connected) {
			//System.out.println("Yay!");
			productionPane.displayMessage("Connected to database");
		} else {
			//System.out.println("nooooooo");
			productionPane.displayMessage("Could not connect to database");
		}
	}

	/**
	 * ChangeHandler is a listener class, called when the user switches panes.
	 */
	class ChangeHandler implements ChangeListener {
		/**
		 * Called when the user switches panes. The entry actions of the new
		 * pane are performed.
		 * 
		 * @param e
		 *            The change event (not used).
		 */
		public void stateChanged(ChangeEvent e) {
			/*BasicPane selectedPane = (BasicPane) tabbedPane
					.getSelectedComponent();
			selectedPane.entryActions();*/
		}
	}

	/**
	 * WindowHandler is a listener class, called when the user exits the
	 * application.
	 */
	class WindowHandler extends WindowAdapter {
		/**
		 * Called when the user exits the application. Closes the connection to
		 * the database.
		 * 
		 * @param e
		 *            The window event (not used).
		 */
		public void windowClosing(WindowEvent e) {
			db.closeConnection();
			System.exit(0);
		}
	}
}