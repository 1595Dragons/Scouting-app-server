package javacode.UI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javacode.Core.Debugger;
import javacode.Core.NodeHelper;
import javacode.FileManager.Database;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ViewData {

	private static boolean isVisible = false;
	private static Stage stage;

	private Label SQLReturn;
	private Button ExecuteSQL;
	private TextField SQLField;

	public Scene ViewDataScene() throws IOException {

		Parent root = null;

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

			// Get a table view object for the raw data
			TableView<String[]> rawDataTable = null;

			ArrayList<Node> Nodes = new NodeHelper().getAllNodesFromParent(root);
			for (Node node : Nodes) {
				if (node.getId() != null) {
					if (node.getId().equals("sqlQuery")) {
						SQLField = (TextField) node;
					} else if (node.getId().equals("execute")) {
						ExecuteSQL = (Button) node;
					} else if (node.getId().equals("returnedSQL")) {
						SQLReturn = (Label) node;
					} else if (node.getId().equals("table")) {
						rawDataTable = (TableView<String[]>) node;
					} else {
						Debugger.d(getClass(),
								String.format("Unused node: (type %s) %s", node.getClass().getName(), node.getId()));
					}
				}
			}

			if (rawDataTable != null) {
				this.setupRawDataView(rawDataTable);
			}
			this.setupCalculatedDataView();

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

	private void setupRawDataView(TableView<String[]> table) {
		// TODO

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
		// TODO: Flip X and Y

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
				twod[x][y] = rows[x].replace("\t|\t", "<>").split("<>")[y];
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

	}

	private void setupCalculatedDataView() {
		// TODO
	}

}
