package javacode.UI;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;

import javacode.Core.Debugger;
import javacode.FileManager.Database;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewData {

	private Stage stage = new Stage();

	public Scene ViewDataScene() {

		TabPane root = null;

		// Get the path of the main panel's FXML file
		java.net.URL path = getClass().getClassLoader().getResource("javacode/fxml/ViewData.fxml");
		Debugger.d(getClass(), "Path: " + path.toString());

		// Load the FXML from the layout file
		try {
			root = javafx.fxml.FXMLLoader.load(path);
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		}

		final Tab rawDataTab = root.getTabs().get(0), calculatedDataTab = root.getTabs().get(1),
				executeSQLTab = root.getTabs().get(2);
		final HBox submitSQLArea = ((HBox) ((VBox) executeSQLTab.getContent()).getChildren().get(0));

		final TextField SQLField = ((TextField) submitSQLArea.getChildren().get(0));
		final Label SQLReturn = ((Label) ((ScrollPane) ((VBox) executeSQLTab.getContent()).getChildren().get(1))
				.getContent());

		root.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab.equals(rawDataTab)) {
				Debugger.d(this.getClass(), "Raw data tab is selected");
				generateRawDataTable((BorderPane) rawDataTab.getContent());
			} else if (newTab.equals(calculatedDataTab)) {
				Debugger.d(this.getClass(), "Calculated data tab is selected");
				generateCalculatedDataTable((BorderPane) calculatedDataTab.getContent());
			}

			if (oldTab.equals(rawDataTab)) {
				Debugger.d(this.getClass(), "Raw data tab is not selected");
				unloadDataTable((BorderPane) rawDataTab.getContent());
			} else if (oldTab.equals(calculatedDataTab)) {
				Debugger.d(this.getClass(), "Calculated data tab is selected");
				unloadDataTable((BorderPane) calculatedDataTab.getContent());
			}
		});

		// Get the refresh button for the raw data tab
		((Button) (((HBox) ((BorderPane) rawDataTab.getContent()).getBottom()).getChildren().get(1)))
				.setOnAction((event) -> {
					// Refresh the table
					Debugger.d(this.getClass(), "Refreshing raw data");
					unloadDataTable((BorderPane) rawDataTab.getContent());
					generateRawDataTable((BorderPane) rawDataTab.getContent());
				});

		// Get the export button
		((Button) (((HBox) ((BorderPane) rawDataTab.getContent()).getBottom()).getChildren().get(0)))
				.setOnAction((event) -> {
					FileChooser destination = new FileChooser();
					destination.setInitialFileName("scouting-data.csv");
					destination
							.setSelectedExtensionFilter(new javafx.stage.FileChooser.ExtensionFilter("*.csv", "*.csv"));
					destination.setInitialDirectory(new File(System.getProperty("user.dir")));
					File csv = destination.showSaveDialog(stage);
					if (csv != null) {
						Debugger.d(this.getClass(), "Chosen file location: " + csv.getAbsolutePath());
						new Database().exportToCSV(csv);
					}
				});

		// Get the refresh button for the calculated data tab
		((Button) ((BorderPane) calculatedDataTab.getContent()).getBottom()).setOnAction((event) -> {
			// Refresh the table
			Debugger.d(this.getClass(), "Refreshing raw data");
			unloadDataTable((BorderPane) calculatedDataTab.getContent());
			generateCalculatedDataTable((BorderPane) calculatedDataTab.getContent());
		});

		((Button) submitSQLArea.getChildren().get(1)).setOnAction((event) -> {
			Database sql = new Database();
			try {
				String result = Database.resultSetToString(sql.executeSQL(SQLField.getText()));
				SQLReturn.setText(result);
			} catch (SQLException e) {
				SQLReturn.setText(e.getMessage());
				return;
			}
		});

		Scene scene = new Scene(root);
		return scene;

	}

	public Stage getStage() {
		return stage;
	}

	private void generateRawDataTable(BorderPane pane) {
		TableView<String[]> table = new TableView<>();

		Database db = new Database();

		// Load all the data from the database
		String result = "";
		try {
			result = Database.resultSetToString(db.executeSQL("SELECT * FROM data"));
		} catch (SQLException e) {
			MainPanel.logError(e);
		}

		// Dynamically create table headers
		// First clear the current headers
		table.getColumns().clear();

		// Then create a 2d array of the data
		String rows[] = result.split("\n");
		Debugger.d(this.getClass(), "Rows: " + Arrays.toString(rows));
		Debugger.d(this.getClass(), "Row value: " + rows.length);
		String columns[] = rows[0].replace("\t|\t", "<>").split("<>");
		Debugger.d(this.getClass(), "Columns: " + Arrays.toString(columns));
		Debugger.d(this.getClass(), "Column value: " + columns.length);
		String[][] twod = new String[rows.length][columns.length];

		// Setup the content of the 2d array
		for (int x = 0; x < twod.length; x++) {
			for (int y = 0; y < twod[0].length; y++) {
				try {
					String str = rows[x].replace("\t|\t", "<>").split("<>")[y];
					Debugger.d(this.getClass(), "Adding to 2d array: " + str);
					twod[x][y] = str;
				} catch (ArrayIndexOutOfBoundsException e) {
					Debugger.d(this.getClass(), "May be blank!\n" + e.getMessage());
				}
			}
		}

		// https://stackoverflow.com/questions/20769723/populate-tableview-with-two-dimensional-array
		ObservableList<String[]> data = FXCollections.observableArrayList();
		data.addAll(Arrays.asList(twod));
		data.remove(0);// remove titles from data
		for (int i = 0; i < columns.length; i++) {
			TableColumn<String[], String> col = new TableColumn<String[], String>(twod[0][i]);
			final int colNo = i;
			col.setCellValueFactory((callback) -> new SimpleStringProperty((callback.getValue()[colNo])));
			col.setMaxWidth(Double.MAX_VALUE);
			Debugger.d(this.getClass(), "Creating table column: " + col.getText());
			table.getColumns().add(col);
		}

		table.setItems(data);

		pane.setCenter(table);

	}

	private void generateCalculatedDataTable(BorderPane pane) {
		TableView<String[]> table = new TableView<>();

		Database db = new Database();

		// Get a list of team numbers from the database
		String result = "";
		try {
			result = Database.resultSetToString(db.executeSQL("SELECT DISTINCT \"Team number\" FROM data ORDER BY \"Team number\" ASC"));
		} catch (SQLException e) {
			MainPanel.logError(e);
		}

		// Load all the data from the database
		String rawResult = "";
		try {
			rawResult = Database.resultSetToString(db.executeSQL("SELECT * FROM data"));
		} catch (SQLException e) {
			MainPanel.logError(e);
		}

		String[] teamNumbers = result.split("\n");
		// Just know that for for looks, they will now start at 1 to skip the header
		Debugger.d(this.getClass(), "Team numbers: " + Arrays.toString(teamNumbers));

		// Dynamically create table headers
		// First clear the current headers
		table.getColumns().clear();

		// Create a 2d array for the averages
		String rows[] = rawResult.split("\n");
		Debugger.d(this.getClass(), "Rows: " + Arrays.toString(rows));
		Debugger.d(this.getClass(), "Row value: " + rows.length);
		String columns[] = rows[0].replace("\t|\t", "<>").split("<>");
		Debugger.d(this.getClass(), "Columns: " + Arrays.toString(columns));
		Debugger.d(this.getClass(), "Column value: " + columns.length);
		String[][] twod = new String[teamNumbers.length][columns.length - 1];

		// Setup the content of the 2d array
		for (int x = 0; x < twod.length; x++) {
			for (int y = 0; y < twod[0].length; y++) {
				try {
					if (y == 0) {
						if (x == 0) {
							String str = rows[x].replace("\t|\t", "<>").split("<>")[y];
							Debugger.d(this.getClass(), "Adding to 2d array: " + str);
							twod[x][y] = str;
						} else {
							Debugger.d(this.getClass(), "Adding to 2d array: " + teamNumbers[x]);
							twod[x][y] = teamNumbers[x];
						}
					} else {
						if (x == 0) {
							String str = rows[x].replace("\t|\t", "<>").split("<>")[y] + " (Average)";
							Debugger.d(this.getClass(), "Adding to 2d array: " + str);
							twod[x][y] = str;
						} else {
							try {
								String query = "SELECT avg(\"" + columns[y] + "\") FROM data WHERE \"Team number\"="
										+ teamNumbers[x];
								String str = Database.resultSetToString(db.executeSQL(query)).split("\n")[1];
								Debugger.d(this.getClass(), "Adding to 2d array: " + str);
								twod[x][y] = str;
							} catch (SQLException e) {
								Debugger.d(this.getClass(), "May be blank!\n" + e.getMessage());
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					Debugger.d(this.getClass(), "May be blank!\n" + e.getMessage());
				}
			}
		}

		// https://stackoverflow.com/questions/20769723/populate-tableview-with-two-dimensional-array
		ObservableList<String[]> data = FXCollections.observableArrayList();
		data.addAll(Arrays.asList(twod));
		data.remove(0);// remove titles from data
		for (int i = 0; i < columns.length - 1; i++) {
			TableColumn<String[], String> col = new TableColumn<String[], String>(twod[0][i]);
			final int colNo = i;
			col.setCellValueFactory((callback) -> new SimpleStringProperty((callback.getValue()[colNo])));
			col.setMaxWidth(Double.MAX_VALUE);
			Debugger.d(this.getClass(), "Creating table column: " + col.getText());
			table.getColumns().add(col);
		}

		table.setItems(data);

		pane.setCenter(table);

	}

	private void unloadDataTable(BorderPane pane) {
		ProgressIndicator loading = new ProgressIndicator();
		loading.setProgress(-1);
		pane.setCenter(loading);
	}
}
