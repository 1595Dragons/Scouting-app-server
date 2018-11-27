package javacode.Core;

import java.sql.Connection;
import java.sql.Statement;

import javacode.UI.DataCollection;

public class Database {

	private Connection connection;
	private Statement statement;
	
	private DataCollection data;
	
	
	public void updateDatabase() {
		// TODO: Update the data in the database (Basically add new data, like a write to)
	}
	
	public void createDatabase() {
		// TODO: Create/Recreate the database
	}
	
	public boolean databaseExists(boolean autoCreate) {
		// TODO: Check if database exists
		
		return false;
	}
	
	public void readDatabase() {
		// TODO: Read from database
	}
	
	public Object executeSQL(String query) {
		// TODO: Execute a SQL query on the database, return whatever is appropriate...
		return null;
	}
	
}
