package javacode.UI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import javacode.Core.Debugger;
import javacode.Core.NodeHelper;
import javacode.FileManager.Database;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
			
			setupRawDataView();
			setupCalculatedDataView();

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
	
	private void setupRawDataView() {
		// TODO
	}
	
	private void setupCalculatedDataView() {
		// TODO
	}

}
