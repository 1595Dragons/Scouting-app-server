package javacode.UI;

import java.io.IOException;
import java.net.URL;

import javacode.Core.Debugger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TeamNumberDialog {

	public int teamNumber = 0;

	private Stage stage = new Stage();

	public Scene createTeamNumberDialog() {
		VBox root = null;

		// Get the path of the main panel's FXML file
		URL path = getClass().getClassLoader().getResource("javacode/fxml/TeamNumberDialog.fxml");
		Debugger.d(getClass(), "Path: " + path.toString());

		// Load the FXML from the layout file
		try {
			root = FXMLLoader.load(path);
		} catch (IOException e) {
			MainPanel.logError(e);
			return null;
		}

		// Get the bottom row that contains the 2 buttons
		final HBox buttons = (HBox) root.getChildrenUnmodifiable().get(2);

		// Setup the team number input box
		final TextField teamNumberInput = (TextField) root.getChildrenUnmodifiable().get(1);

		// Setup the start button
		((Button) buttons.getChildren().get(1)).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					teamNumber = Integer.parseInt(teamNumberInput.getText());
				} catch (NumberFormatException InvalidNumber) {
					return;
				}

				DataCollection data = new DataCollection(teamNumber);
				data.getStage().setScene(data.createDataCollectionPage());
				data.getStage().setTitle("Data collection");
				data.getStage().show();

				stage.close();
			}
		});

		// Setup the cancel button
		((Button) buttons.getChildren().get(0)).setOnAction((event) -> {
			stage.close();
		});

		Scene scene = new Scene(root);
		return scene;

	}

	public Stage getStage() {
		return this.stage;
	}
}
