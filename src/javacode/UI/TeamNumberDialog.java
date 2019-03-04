package javacode.UI;

import javacode.Core.Debugger;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TeamNumberDialog {

	public int teamNumber = 0;

	private Stage stage = new Stage();

	public Scene createTeamNumberDialog() {
		javafx.scene.layout.VBox root = null;

		// Get the path of the main panel's FXML file
		java.net.URL path = getClass().getClassLoader().getResource("javacode/fxml/TeamNumberDialog.fxml");
		javacode.Core.Debugger.d(getClass(), "Path: " + path.toString());

		// Load the FXML from the layout file
		try {
			root = javafx.fxml.FXMLLoader.load(path);
		} catch (java.io.IOException e) {
			MainPanel.logError(e);
			return null;
		}

		// Get the bottom row that contains the 2 buttons
		final HBox buttons = (HBox) root.getChildrenUnmodifiable().get(2);

		// Setup the team number input box
		final TextField teamNumberInput = (TextField) root.getChildrenUnmodifiable().get(1);

		// Setup the start button
		((Button) buttons.getChildren().get(1)).setOnAction((event) -> {
			try {
				teamNumber = Integer.parseInt(teamNumberInput.getText());
			} catch (NumberFormatException InvalidNumber) {
				return;
			}

			DataCollection data = new DataCollection(teamNumber);
			data.getStage().setScene(data.createDataCollectionPage());
			data.getStage().setTitle("Data collection");

			data.getStage().show();

			// Check if the current height is too long
			double screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			Debugger.d(this.getClass(), "Built height: " + data.getStage().getHeight());
			Debugger.d(this.getClass(), "Screen height: " + screenHeight);
			if (data.getStage().getHeight() > (screenHeight*.6d)) {
				data.getStage().setHeight(screenHeight*.5d);
				data.getStage().centerOnScreen();
			}

			stage.close();
		});

		// Setup the cancel button
		((Button) buttons.getChildren().get(0)).setOnAction((event) -> stage.close());

		Scene scene = new Scene(root);
		return scene;

	}

	public Stage getStage() {
		return this.stage;
	}
}
