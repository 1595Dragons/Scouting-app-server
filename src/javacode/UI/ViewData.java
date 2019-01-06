package javacode.UI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javacode.Core.Debugger;
import javacode.Core.NodeHelper;
import javacode.FileManager.Database;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ViewData {

	private static boolean isVisible = false;
	private static Stage stage;

	private Label SQLReturn;
	private Button ExecuteSQL;
	private TextField SQLField;

	public Scene ViewDataScene() throws IOException {

		TabPane root = null;

		// Get the path of the main panel's FXML file
		URL path = getClass().getClassLoader().getResource("javacode/fxml/ViewData.fxml");
		Debugger.d(getClass(), "Path: " + path.toString());

		// Load the FXML from the layout file
		try {
			root = FXMLLoader.load(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (root != null) {

			ArrayList<Node> Nodes = new NodeHelper().getAllNodesFromParent(root);
			for (Node node : Nodes) {
				if (node.getId() != null) {
					if (node.getId().equals("sqlQuery")) {
						SQLField = (TextField) node;
					} else if (node.getId().equals("execute")) {
						ExecuteSQL = (Button) node;
					} else if (node.getId().equals("returnedSQL")) {
						SQLReturn = (Label) node;
					} else {
						Debugger.d(getClass(),
								String.format("Unused node: (type %s) %s", node.getClass().getName(), node.getId()));
					}
				}
			}

			final Tab rawDataTab = root.getTabs().get(0), calculatedDataTab = root.getTabs().get(1);

			root.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				@Override
				public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
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
				}
			});

			// Get the refresh button for the raw data tab
			((Button) (((HBox) ((BorderPane) rawDataTab.getContent()).getBottom()).getChildren().get(1)))
					.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							// Refresh the table
							Debugger.d(this.getClass(), "Refreshing raw data");
							unloadDataTable((BorderPane) rawDataTab.getContent());
							generateRawDataTable((BorderPane) rawDataTab.getContent());
						}
					});

			// Get the refresh button for the calculated data tab
			((Button) ((BorderPane) calculatedDataTab.getContent()).getBottom())
					.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							// Refresh the table
							Debugger.d(this.getClass(), "Refreshing raw data");
							unloadDataTable((BorderPane) calculatedDataTab.getContent());
							generateCalculatedDataTable((BorderPane) calculatedDataTab.getContent());
						}
					});

			if (ExecuteSQL != null && SQLField != null && SQLReturn != null) {
				ExecuteSQL.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						Database sql = new Database();
						try {
							String result = sql.executeSQL(SQLField.getText());
							SQLReturn.setText(result);
						} catch (SQLException e) {
							SQLReturn.setText(e.getMessage());
							return;
						}
					}
				});
			}

			Scene scene = new Scene(root);
			return scene;

		} else {
			throw new IOException("Cannot view data dialog FXML");
		}
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		ViewData.stage = stage;
	}

	public boolean getIsVisible() {
		Debugger.d(getClass(), "Is view data dialog visible: " + isVisible);
		return isVisible;
	}

	public void setIsVisible(boolean value) {
		Debugger.d(getClass(), "Setting view data dialog visibility: " + value);
		isVisible = value;
	}

	private void generateRawDataTable(BorderPane pane) {
		TableView<String[]> table = new TableView<>();

		Database db = new Database();

		// Load all the data from the database
		String result = "";
		try {
			result = db.executeSQL("SELECT * FROM data");
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
			col.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
					return new SimpleStringProperty((p.getValue()[colNo]));
				}
			});
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
			result = db.executeSQL("SELECT DISTINCT \"Team number\" FROM data ORDER BY \"Team number\" ASC");
		} catch (SQLException e) {
			MainPanel.logError(e);
		}

		// Load all the data from the database
		String rawResult = "";
		try {
			rawResult = db.executeSQL("SELECT * FROM data");
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
								String str = db.executeSQL(query).split("\n")[1];
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
			col.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
					return new SimpleStringProperty((p.getValue()[colNo]));
				}
			});
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
