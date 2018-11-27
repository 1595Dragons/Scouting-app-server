package javacode.UI;

import java.io.IOException;
import java.net.URL;

import javacode.Core.Debugger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TeamNumberDialog {

	private static boolean isVisible = false;
	public static int teamNumber = 0;

	private static Stage stage;

	public Scene createTeamNumberDialog() throws IOException {
		Parent root = null;

		// Get the path of the main panel's FXML file
		URL path = getClass().getClassLoader().getResource("javacode/fxml/TeamNumberDialog.fxml");
		Debugger.d(getClass(), "Path: " + path.toString());

		// Load the FXML from the layout file
		try {
			root = FXMLLoader.load(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (root != null) {

			Button start = null, cancel = null;

			TextField teamNumberInput = null;

			// Setup each of the nodes that are important
			for (Node node : root.getChildrenUnmodifiable()) {

				// Check if there is a nested view (HBox)
				if (node instanceof HBox) {
					for (Node childNode : ((HBox) node).getChildren()) {
						// If the given node of importance, set it to an object
						if (childNode.getId().equals("cancel")) {
							cancel = ((Button) childNode);
						} else if (childNode.getId().equals("start")) {
							start = ((Button) childNode);
						} else {
							Debugger.d(getClass(), String.format("Unused childNode: (%s) %s", childNode.getClass(),
									childNode.getId()));
						}
					}
				} else {
					// If the given node of importance, set it to an object
					if (node.getId().equals("teamnumber")) {
						teamNumberInput = ((TextField) node);
					} else {
						Debugger.d(getClass(), String.format("Unused node: (%s) %s", node.getClass(), node.getId()));
					}
				}
			}

			if (teamNumberInput != null && start != null) {

				final TextField TNI = teamNumberInput;
				start.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						try {
							TeamNumberDialog.teamNumber = Integer.parseInt(TNI.getText());
						} catch (NumberFormatException InvalidNumber) {
							return;
						} catch (Exception e) {
							MainPanel.logError(e);
						}
						
						try {
							Stage data = new Stage();
							data.setScene(new DataCollection(teamNumber).createDataCollectionPage());
							data.setTitle("Data collection");
							data.show();
						} catch (IOException e) {
							MainPanel.logError(e);
							return;
						}

						isVisible = false;
						stage.close();
					}
				});
			}

			if (cancel != null) {
				cancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						isVisible = false;
						stage.close();
					}
				});
			}

			Scene scene = new Scene(root);
			return scene;

		} else {
			throw new IOException("Cannot load team number dialog FXML");
		}

	}

	public boolean getIsVisible() {
		Debugger.d(getClass(), "Is team number dialog visible: " + isVisible);
		return isVisible;
	}

	public void setIsVisible(boolean value) {
		Debugger.d(getClass(), "Setting team number dialog visibility: " + value);
		isVisible = value;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		TeamNumberDialog.stage = stage;
	}
}
