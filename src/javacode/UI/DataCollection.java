package javacode.UI;

import java.util.ArrayList;

import javacode.ScoutingApp;
import javacode.Core.Debugger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DataCollection {

	private int teamNumber;

	private Stage stage = new Stage();

	public DataCollection(int number) {
		this.teamNumber = number;
	}

	public Scene createDataCollectionPage() {
		VBox root = null;

		// Get the path of the main panel's FXML file
		java.net.URL path = getClass().getClassLoader().getResource("javacode/fxml/DataPage.fxml");
		Debugger.d(getClass(), "Path: " + path.toString());

		try {
			root = javafx.fxml.FXMLLoader.load(path);
		} catch (java.io.IOException e) {
			MainPanel.logError(e);
			return null;
		}

		// Set the team number at the top to be that which the user is scouting
		((Label) root.getChildren().get(0)).setText("Scouting team: " + this.teamNumber);

		// Get the scrollable pane that will house all of the dynamically generated
		// content
		final ScrollPane contentpane = (ScrollPane) root.getChildren().get(1);

		// Get the bottom row that contains the 2 buttons
		final HBox buttons = (HBox) root.getChildrenUnmodifiable().get(2);

		this.generateFromConfig(contentpane);

		// Setup the cancel button
		((Button) buttons.getChildren().get(0)).setOnAction((event) -> stage.close());

		((Button) buttons.getChildren().get(1)).setOnAction((event) -> {
			VBox content = (VBox) (contentpane.getContent());

			// Create an array of all the nodes
			ArrayList<Node> validNodes = new ArrayList<Node>();
			for (int i = 0; i < content.getChildren().size(); i++) {
				if (content.getChildren().get(i).getId() != null) {
					Debugger.d(getClass(), String.format("Registered node for submission: (%s) %s",
							content.getChildren().get(i).getClass(), content.getChildren().get(i).getId()));
					validNodes.add(content.getChildren().get(i));

				}
			}

			// Create a string 2d array for the data, the first array is the name, the
			// second is the value
			String[][] data = new String[validNodes.size()][2];

			for (int index = 0; index < data.length; index++) {
				Node node = validNodes.get(index);

				// Apply the name
				data[index][0] = node.getId();

				// Apply the value
				if (node instanceof CheckBox) {
					data[index][1] = ((CheckBox) node).isSelected() ? "1" : "0";
				} else if (node instanceof Spinner) {
					@SuppressWarnings("unchecked")
					Spinner<Integer> spinner = (Spinner<Integer>) node;
					data[index][1] = Integer.toString(spinner.getValue());
				} else if (node instanceof RadioButton) {
					data[index][1] = ((RadioButton) node).isSelected() ? "1" : "0";
				} else if (node instanceof TextArea) {
					data[index][1] = String.format("\"%s\"", ((TextArea) node).getText());
				} else if (node instanceof TextField) {
					data[index][1] = String.format("\"%s\"", ((TextField) node).getText());
				} else {
					MainPanel.log("Unknown class for node " + node.getId() + "\n" + node.getClass(), true);
				}
			}

			// Update the database
			new javacode.FileManager.Database().updateDatabase(teamNumber, data);

			stage.close();
		});

		return new Scene(root);
	}

	public Stage getStage() {
		return this.stage;
	}

	private void generateFromConfig(ScrollPane scrollpane) {

		// Get the VBox that will house all of the dynamically loaded data
		VBox pane = (VBox) (scrollpane.getContent());

		// Create the autonomous header label
		pane.getChildren().add(this.createLabel("Autonomous:", 20, new Insets(5, 0, 5, 0)));

		// Get the autonomous fields from the config
		for (javacode.Core.Match.Autonomous autonomous : ScoutingApp.config.matchData.autonomousData) {

			switch (autonomous.datatype) {
			case Boolean:
				// Create the check box
				pane.getChildren().add(this.createCheckBox(autonomous.name,
						Boolean.parseBoolean(autonomous.value.get(0).toString()), 15, new Insets(5, 0, 5, 0)));
				break;
			case BooleanGroup:
				// Create the header for the group
				pane.getChildren().add(this.createLabel(autonomous.name, 17, new Insets(5, 0, 0, 0)));

				// Create a group for the radio buttons
				ToggleGroup group = new ToggleGroup();

				// Get the checkboxes from the value of the datatype
				javax.json.JsonObject checkBoxes = autonomous.value.get(0).asJsonObject();
				String[] keys = checkBoxes.keySet().toArray(new String[checkBoxes.size()]);
				for (int index = 0; index < checkBoxes.size(); index++) {
					// Get the current key at the index, and get the object value.
					// This is the name of the radio button, and if this checked
					String key = keys[index];
					boolean checked = Boolean.parseBoolean(checkBoxes.get(key).toString());
					pane.getChildren().add(this.createRadioButton(key, checked, 15, group, new Insets(5, 0, 5, 0)));
				}
				break;
			case Number:
				// Create the label header for the spinner
				pane.getChildren().add(this.createLabel(autonomous.name, 15, new Insets(5, 0, 0, 0)));

				// Create the actual spinner
				pane.getChildren()
						.add(this.createSpinner(autonomous.name, Integer.parseInt(autonomous.value.get(1).toString()),
								Integer.parseInt(autonomous.value.get(2).toString()),
								Integer.parseInt(autonomous.value.get(3).toString()),
								Integer.parseInt(autonomous.value.get(0).toString()), new Insets(5, 0, 5, 0)));
				break;
			case Text:
				pane.getChildren().add(this.createTextField(autonomous.name,
						autonomous.value.get(0).toString().replace("\"", ""), 15, new Insets(5, 0, 5, 0)));
				break;
			default:
				Debugger.d(this.getClass(), "Unknown datatype for " + autonomous.name);
				break;

			}
		}

		// Create the teleop header label
		pane.getChildren().add(this.createLabel("TeleOp:", 20, new Insets(20, 0, 5, 0)));

		// Get the teleop fields from the config
		for (javacode.Core.Match.TeleOp teleop : ScoutingApp.config.matchData.teleopData) {

			switch (teleop.datatype) {
			case Boolean:
				// Create the check box
				pane.getChildren().add(this.createCheckBox(teleop.name,
						Boolean.parseBoolean(teleop.value.get(0).toString()), 15, new Insets(5, 0, 5, 0)));
				break;
			case BooleanGroup:
				// Create the header for the group
				pane.getChildren().add(this.createLabel(teleop.name, 17, new Insets(5, 0, 0, 0)));

				// Create a group for the radio buttons
				ToggleGroup group = new ToggleGroup();

				// Get the checkboxes from the value of the datatype
				javax.json.JsonObject checkBoxes = teleop.value.get(0).asJsonObject();
				String[] keys = checkBoxes.keySet().toArray(new String[checkBoxes.size()]);
				for (int index = 0; index < checkBoxes.size(); index++) {
					// Get the current key at the index, and get the object value.
					// This is the name of the radio button, and if this checked
					String key = keys[index];
					boolean checked = Boolean.parseBoolean(checkBoxes.get(key).toString());
					pane.getChildren().add(this.createRadioButton(key, checked, 15, group, new Insets(5, 0, 5, 0)));
				}
				break;
			case Number:
				// Create the label header for the spinner
				pane.getChildren().add(this.createLabel(teleop.name, 15, new Insets(5, 0, 0, 0)));

				// Create the actual spinner
				pane.getChildren()
						.add(this.createSpinner(teleop.name, Integer.parseInt(teleop.value.get(1).toString()),
								Integer.parseInt(teleop.value.get(2).toString()),
								Integer.parseInt(teleop.value.get(3).toString()),
								Integer.parseInt(teleop.value.get(0).toString()), new Insets(5, 0, 5, 0)));
				break;
			case Text:
				pane.getChildren().add(this.createTextField(teleop.name,
						teleop.value.get(0).toString().replace("\"", ""), 15, new Insets(5, 0, 5, 0)));
				break;
			default:
				Debugger.d(this.getClass(), "Unknown datatype for " + teleop.name);
				break;

			}
		}

		// Create the endgame header label
		pane.getChildren().add(this.createLabel("End game:", 20, new Insets(20, 0, 5, 0)));

		// Get the endgame fields from the config
		for (javacode.Core.Match.Endgame endgame : ScoutingApp.config.matchData.endgameData) {

			switch (endgame.datatype) {
			case Boolean:
				// Create the check box
				pane.getChildren().add(this.createCheckBox(endgame.name,
						Boolean.parseBoolean(endgame.value.get(0).toString()), 15, new Insets(5, 0, 5, 0)));
				break;
			case BooleanGroup:
				// Create the header for the group
				pane.getChildren().add(this.createLabel(endgame.name, 17, new Insets(5, 0, 0, 0)));

				// Create a group for the radio buttons
				ToggleGroup group = new ToggleGroup();

				// Get the checkboxes from the value of the datatype
				javax.json.JsonObject checkBoxes = endgame.value.get(0).asJsonObject();
				String[] keys = checkBoxes.keySet().toArray(new String[checkBoxes.size()]);
				for (int index = 0; index < checkBoxes.size(); index++) {
					// Get the current key at the index, and get the object value.
					// This is the name of the radio button, and if this checked
					String key = keys[index];
					boolean checked = Boolean.parseBoolean(checkBoxes.get(key).toString());
					pane.getChildren().add(this.createRadioButton(key, checked, 15, group, new Insets(5, 0, 5, 0)));
				}
				break;
			case Number:
				// Create the label header for the spinner
				pane.getChildren().add(this.createLabel(endgame.name, 15, new Insets(5, 0, 0, 0)));

				// Create the actual spinner
				pane.getChildren()
						.add(this.createSpinner(endgame.name, Integer.parseInt(endgame.value.get(1).toString()),
								Integer.parseInt(endgame.value.get(2).toString()),
								Integer.parseInt(endgame.value.get(3).toString()),
								Integer.parseInt(endgame.value.get(0).toString()), new Insets(5, 0, 5, 0)));
				break;
			case Text:
				pane.getChildren().add(this.createTextField(endgame.name,
						endgame.value.get(0).toString().replace("\"", ""), 15, new Insets(5, 0, 5, 0)));
				break;
			default:
				Debugger.d(this.getClass(), "Unknown datatype for " + endgame.name);
				break;

			}
		}

		// Add the comments field, start with the header
		pane.getChildren().add(this.createLabel("Additional Feedback (Optional):", 20, new Insets(20, 0, 5, 0)));

		// Now add the comment box
		TextArea comments = new TextArea();
		comments.setFont(new Font("Arial", 15));
		comments.setCursor(javafx.scene.Cursor.TEXT);
		comments.setWrapText(true);
		comments.setStyle("-fx-background-color: WHITE;");
		comments.setPromptText("Enter comments here");
		comments.setPrefHeight(60.0d);
		comments.minWidth(Double.NEGATIVE_INFINITY);
		comments.minHeight(Double.NEGATIVE_INFINITY);
		comments.maxWidth(Double.MAX_VALUE);
		comments.maxHeight(Double.MAX_VALUE);
		comments.setPadding(new Insets(0, 5, 0, 5));
		comments.setId("Comments");
		VBox.setMargin(comments, new Insets(5, 0, 5, 0));
		pane.getChildren().add(comments);

	}

	private Label createLabel(String text, int fontSize, Insets insets) {
		Label label = new Label(text);
		label.setFont(new Font("Arial", fontSize));
		label.setAlignment(Pos.CENTER);
		label.setContentDisplay(ContentDisplay.CENTER);
		label.setMaxWidth(Double.MAX_VALUE);
		VBox.setMargin(label, insets);
		return label;
	}

	private CheckBox createCheckBox(String text, boolean isSelected, int fontSize, Insets insets) {
		CheckBox checkbox = new CheckBox(text);
		checkbox.setSelected(isSelected);
		checkbox.setFont(new Font("Arial", fontSize));
		checkbox.setAlignment(Pos.CENTER);
		checkbox.setContentDisplay(ContentDisplay.CENTER);
		checkbox.setMaxWidth(Double.MAX_VALUE);
		checkbox.setId(text);
		VBox.setMargin(checkbox, insets);
		return checkbox;
	}

	private Spinner<Integer> createSpinner(String id, int min, int max, int step, int init, Insets insets) {
		Spinner<Integer> spinner = new Spinner<Integer>(min, max, init, step);
		spinner.setEditable(true);
		spinner.setMaxWidth(Double.MAX_VALUE);
		spinner.setMinHeight(Double.NEGATIVE_INFINITY);
		spinner.setMinWidth(Double.NEGATIVE_INFINITY);
		// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
		spinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				spinner.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		spinner.setId(id);
		VBox.setMargin(spinner, insets);
		return spinner;
	}

	private RadioButton createRadioButton(String id, boolean isSelected, int fontSize, ToggleGroup togglegroup,
			Insets insets) {
		RadioButton button = new RadioButton(id);
		button.setSelected(isSelected);
		button.setFont(new Font("Arial", fontSize));
		button.setAlignment(Pos.CENTER);
		button.setContentDisplay(ContentDisplay.CENTER);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setToggleGroup(togglegroup);
		button.setId(id);
		VBox.setMargin(button, insets);
		return button;
	}

	private TextField createTextField(String promptText, String defaultValue, int fontSize, Insets insets) {
		TextField text = new TextField(defaultValue);
		text.setFont(new Font("Arial", fontSize));
		text.setEditable(true);
		text.setPromptText(promptText);
		text.setAlignment(Pos.CENTER);
		text.setMaxWidth(Double.MAX_VALUE);
		text.setId(promptText);
		VBox.setMargin(text, insets);
		return text;
	}
}
