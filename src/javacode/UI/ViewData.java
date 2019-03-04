package javacode.UI;

import javacode.Core.Debugger;
import javacode.FileManager.Database;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;

public class ViewData {

	private Stage stage = new Stage();

	private ProgressBar rawProgress, calculatedProgress;

	private BorderPane rawTabPane, calculatedTabPane;

	private Tab rawDataTab, calculatedDataTab;

	Scene ViewDataScene() {

		TabPane root;

		// Get the path of the main panel's FXML file
		java.net.URL path = getClass().getClassLoader().getResource("javacode/fxml/ViewData.fxml");

		// Load the FXML from the layout file
		try {
			Debugger.d(getClass(), "Path: " + java.util.Objects.requireNonNull(path).toString());
			root = javafx.fxml.FXMLLoader.load(path);
		} catch (java.io.IOException | java.lang.NullPointerException e) {
			MainPanel.logError(e);
			return null;
		}

		// Get the tabs
		this.rawDataTab = root.getTabs().get(0);
		this.calculatedDataTab = root.getTabs().get(1);
		Tab executeSQLTab = root.getTabs().get(2);

		// Get border panes
		this.rawTabPane = (BorderPane) rawDataTab.getContent();
		this.calculatedTabPane = (BorderPane) calculatedDataTab.getContent();

		// Setup the progress bars
		this.rawProgress = (ProgressBar) this.rawTabPane.getCenter();
		this.calculatedProgress = (ProgressBar) this.calculatedTabPane.getCenter();

		// Get the SQL pane stuff
		final HBox submitSQLArea = ((HBox) ((VBox) executeSQLTab.getContent()).getChildren().get(0));
		final TextField SQLField = ((TextField) submitSQLArea.getChildren().get(0));
		final Label SQLReturn = ((Label) ((ScrollPane) ((VBox) executeSQLTab.getContent()).getChildren().get(1))
				.getContent());


		root.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab.equals(this.rawDataTab)) {
				Debugger.d(this.getClass(), "Raw data tab is selected");
				this.generateRawDataTable();
			} else if (newTab.equals(this.calculatedDataTab)) {
				Debugger.d(this.getClass(), "Calculated data tab is selected");
				this.generateCalculatedDataTable();
			}

			if (oldTab.equals(this.rawDataTab)) {
				Debugger.d(this.getClass(), "Raw data tab is not selected");
				this.unloadRawTable();
			} else if (oldTab.equals(this.calculatedDataTab)) {
				Debugger.d(this.getClass(), "Calculated data tab is selected");
				this.unloadCalculatedTable();
			}
		});

		// Get the refresh button for the raw data tab
		((Button) ((HBox) rawTabPane.getBottom()).getChildren().get(1)).setOnAction((event) -> {
			// Refresh the table
			Debugger.d(this.getClass(), "Refreshing raw data");
			this.unloadRawTable();
			this.generateRawDataTable();
		});


		// Get the export button
		((Button) ((HBox) rawTabPane.getBottom()).getChildren().get(0)).setOnAction((event) -> {
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
		((Button) calculatedTabPane.getBottom()).setOnAction((event) -> {
			// Refresh the table
			Debugger.d(this.getClass(), "Refreshing raw data");
			this.unloadCalculatedTable();
			this.generateCalculatedDataTable();
		});

		((Button) submitSQLArea.getChildren().get(1)).setOnAction((event) -> {
			Database sql = new Database();
			try {
				String result = Database.resultSetToString(sql.executeSQL(SQLField.getText()));
				SQLReturn.setText(result);
			} catch (SQLException e) {
				SQLReturn.setText(e.getMessage());
			}
		});

		// Since the raw pane is the first one, load it.
		this.generateRawDataTable();

		return new Scene(root);

	}

	Stage getStage() {
		return stage;
	}

	private void generateRawDataTable() {
		Task task = new Task() {
			@Override
			protected Object call() {

				// Set the progress to 0
				this.updateProgress(0, 1);

				// Load all the data from the database
				String result = "";
				try {
					result = Database.resultSetToString(new Database().executeSQL("SELECT * FROM data"));
				} catch (SQLException e) {
					MainPanel.logError(e);
				}

				// Then create a 2d array of the data
				String[] rows = result.split("\n");
				String[] columns = rows[0].replace("\t|\t", "<>").split("<>");

				Debugger.d(this.getClass(), "Rows: " + Arrays.toString(rows));
				Debugger.d(this.getClass(), "Row value: " + rows.length);
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

						// Update progress
						this.updateProgress((x * twod[0].length) + y, (twod.length * twod[0].length) + columns.length);
					}
				}

				// Generate the table
				TableView<String[]> table = ViewData.twoDArrayToTable(twod, columns.length);

				// Set progress to complete
				this.updateProgress(1, 1);

				return table;
			}
		};

		this.rawProgress.progressProperty().bind(task.progressProperty());

		task.setOnSucceeded((event) -> this.rawTabPane.setCenter((TableView) task.getValue()));

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();

	}

	private void generateCalculatedDataTable() {
		Task task = new Task() {
			@Override
			protected Object call() {

				// Set the progress to 0
				this.updateProgress(0, 1);

				// Get a database object
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

				// Create a 2d array for the averages
				String[] rows = rawResult.split("\n");
				String[] columns = rows[0].replace("\t|\t", "<>").split("<>");
				String[][] twod = new String[teamNumbers.length][columns.length - 1];

				Debugger.d(this.getClass(), "Rows: " + Arrays.toString(rows));
				Debugger.d(this.getClass(), "Row value: " + rows.length);
				Debugger.d(this.getClass(), "Columns: " + Arrays.toString(columns));
				Debugger.d(this.getClass(), "Column value: " + columns.length);

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

				TableView<String[]> table = ViewData.twoDArrayToTable(twod, columns.length - 1);

				// Set the progress to done
				this.updateProgress(1, 1);

				return table;
			}
		};

		this.calculatedProgress.progressProperty().bind(task.progressProperty());

		task.setOnSucceeded((event) -> this.calculatedTabPane.setCenter((TableView) task.getValue()));

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	private void unloadRawTable() {
		this.rawTabPane.setCenter(this.rawProgress);
	}

	private void unloadCalculatedTable() {
		this.calculatedTabPane.setCenter(this.calculatedProgress);
	}

	// https://stackoverflow.com/questions/20769723/populate-tableview-with-two-dimensional-array
	private static TableView<String[]> twoDArrayToTable(String[][] twodArray, int numberOfColumns) {
		TableView<String[]> table = new TableView<>();
		table.getColumns().clear();
		javafx.collections.ObservableList<String[]> data = javafx.collections.FXCollections.observableArrayList();
		data.addAll(Arrays.asList(twodArray));
		data.remove(0);// remove titles from data
		for (int i = 0; i < numberOfColumns; i++) {
			TableColumn<String[], String> col = new TableColumn<>(twodArray[0][i]);
			final int colNo = i;
			col.setCellValueFactory((callback) -> new javafx.beans.property.SimpleStringProperty((callback.getValue()[colNo])));
			col.setMaxWidth(Double.MAX_VALUE);
			Debugger.d(ViewData.class, "Creating table column: " + col.getText());
			table.getColumns().add(col);
		}
		table.setItems(data);
		return table;
	}
}
