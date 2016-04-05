package gui;

import database.Database;

public class Main {

	public static void main(String[] args) {
		Database db = new Database();
		new CookieGUI(db);
	}

}
