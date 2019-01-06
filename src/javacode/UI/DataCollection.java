package javacode.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.json.JsonObject;

import javacode.ScoutingApp;
import javacode.Core.Debugger;
import javacode.Core.Match.Autonomous;
import javacode.Core.Match.Endgame;
import javacode.Core.Match.TeleOp;
import javacode.Core.NodeHelper;
import javacode.FileManager.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
		URL path = getClass().getClassLoader().getResource("javacode/fxml/DataPage.fxml");
		Debugger.d(getClass(), "Path: " + path.toString());

		try {
			root = FXMLLoader.load(path);
		} catch (IOException e) {
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

		((Button) buttons.getChildren().get(0)).setOnAction((event) -> {
			stage.close();
		});

		((Button) buttons.getChildren().get(1)).setOnAction(new EventHandler<ActionEvent>() {
			@SuppressWarnings("unchecked")
			@Override
			public void handle(ActionEvent arg0) {
				VBox content = (VBox) (contentpane.getContent());

				// Create an array of all the nodes
				ArrayList<Node> Nodes = new ArrayList<Node>(), validNodes = new ArrayList<Node>();
				Nodes = new NodeHelper().getAllNodes(content.getChildrenUnmodifiable());
				for (Node node : Nodes) {
					if (node.getId() != null) {
						Debugger.d(getClass(), String.format("Registered node for submission: (%s) %s", node.getClass(),
								node.getId()));
						validNodes.add(node);

					}
				}

				// Create a string 2d array for the data, the first array is the name, the
				// second is the value
				String[][] data = new String[validNodes.size()][2];

				for (int index = 0; index < validNodes.size(); index++) {
					Node node = validNodes.get(index);

					// Apply the name
					data[index][0] = node.getId();

					// Apply the value
					if (node instanceof CheckBox) {
						data[index][1] = ((CheckBox) node).isSelected() ? "1" : "0";
					} else if (node instanceof Spinner) {
						data[index][1] = (String) Integer.toString(((Spinner<Integer>) node).getValue());
					} else if (node instanceof RadioButton) {
						data[index][1] = ((RadioButton) node).isSelected() ? "1" : "0";
					} else if (node instanceof TextArea) {
						data[index][1] = String.format("\"%s\"", ((TextArea) node).getText());
					} else {
						Debugger.d(this.getClass(), "Unknown class for node " + node.getId());
					}
				}

				// Update the database
				new Database().updateDatabase(teamNumber, data);

				stage.close();
			}
		});

		Scene scene = new Scene(root);
		return scene;
	}

	public Stage getStage() {
		return this.stage;
	}

	private void generateFromConfig(ScrollPane scrollpane) {

		// Get the VBox that will house all of the dynamically loaded data
		VBox pane = (VBox) (scrollpane.getContent());

		// Create the autonomous header label
		Label autonomousHeader = new Label("Autonomous:");
		autonomousHeader.setFont(new Font("Arial", 20));
		autonomousHeader.setAlignment(Pos.CENTER);
		autonomousHeader.setContentDisplay(ContentDisplay.CENTER);
		autonomousHeader.setMaxWidth(Double.MAX_VALUE);
		VBox.setMargin(autonomousHeader, new Insets(5, 0, 5, 0));
		pane.getChildren().add(autonomousHeader);

		// Get the autonomous fields from the config
		for (Autonomous autonomous : ScoutingApp.config.matchData.autonomousData) {

			switch (autonomous.datatype) {
			case Boolean:
				// Create the check box
				CheckBox auto = new CheckBox(autonomous.name);
				auto.setSelected(Boolean.parseBoolean(autonomous.value.get(0).toString()));
				auto.setFont(new Font("Arial", 15));
				auto.setAlignment(Pos.CENTER);
				auto.setContentDisplay(ContentDisplay.CENTER);
				auto.setMaxWidth(Double.MAX_VALUE);
				auto.setId(autonomous.name);
				VBox.setMargin(auto, new Insets(5, 0, 5, 0));
				pane.getChildren().add(auto);
				break;
			case BooleanGroup:
				// Create the header for the group
				Label booleanGroupHeader = new Label(autonomous.name);
				booleanGroupHeader.setFont(new Font("Arail", 15));
				booleanGroupHeader.setAlignment(Pos.CENTER);
				booleanGroupHeader.setContentDisplay(ContentDisplay.CENTER);
				booleanGroupHeader.setMaxWidth(Double.MAX_VALUE);
				VBox.setMargin(booleanGroupHeader, new Insets(5, 0, 0, 0));
				pane.getChildren().add(booleanGroupHeader);

				// Create a group for the radio buttons
				ToggleGroup group = new ToggleGroup();

				// Get the checkboxes from the value of the datatype
				JsonObject checkBoxes = autonomous.value.get(0).asJsonObject();
				String[] keys = checkBoxes.keySet().toArray(new String[checkBoxes.size()]);
				for (int index = 0; index < checkBoxes.size(); index++) {
					// Get the current key at the index, and get the object value. This is the name
					// of the checkbox, and if this checked
					String key = keys[index];
					boolean checked = Boolean.parseBoolean(checkBoxes.get(key).toString());

					RadioButton autoGroup = new RadioButton(key);
					autoGroup.setSelected(checked);
					autoGroup.setFont(new Font("Arial", 15));
					autoGroup.setAlignment(Pos.CENTER);
					autoGroup.setContentDisplay(ContentDisplay.CENTER);
					autoGroup.setMaxWidth(Double.MAX_VALUE);
					autoGroup.setToggleGroup(group);
					autoGroup.setId(key);
					VBox.setMargin(autoGroup, new Insets(5, 0, 5, 0));
					pane.getChildren().add(autoGroup);
				}
				break;
			case Number:
				// Create the label header for the spinner
				Label spinnerHeader = new Label(autonomous.name);
				spinnerHeader.setFont(new Font("Arial", 15));
				spinnerHeader.setAlignment(Pos.CENTER);
				spinnerHeader.setContentDisplay(ContentDisplay.CENTER);
				spinnerHeader.setMaxWidth(Double.MAX_VALUE);

				VBox.setMargin(spinnerHeader, new Insets(5, 0, 0, 0));
				pane.getChildren().add(spinnerHeader);

				// Create the actual spinner
				Spinner<Integer> spinner = new Spinner<Integer>(Integer.parseInt(autonomous.value.get(1).toString()),
						Integer.parseInt(autonomous.value.get(2).toString()),
						Integer.parseInt(autonomous.value.get(0).toString()),
						Integer.parseInt(autonomous.value.get(3).toString()));
				spinner.setEditable(true);
				spinner.setMaxWidth(Double.MAX_VALUE);
				spinner.setMinHeight(Double.NEGATIVE_INFINITY);
				spinner.setMinWidth(Double.NEGATIVE_INFINITY);

				// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
				spinner.getEditor().textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) {
							spinner.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
						}
					}
				});
				spinner.setId(autonomous.name);
				VBox.setMargin(spinner, new Insets(5, 0, 5, 0));
				pane.getChildren().add(spinner);
				break;
			case Text:
				TextField text = new TextField(autonomous.value.get(0).toString());
				text.setFont(new Font("Arial", 15));
				text.setEditable(true);
				text.setPromptText(autonomous.name);
				text.setAlignment(Pos.CENTER);
				text.setMaxWidth(Double.MAX_VALUE);
				text.setId(autonomous.name);
				VBox.setMargin(text, new Insets(5, 0, 5, 0));
				pane.getChildren().add(text);
				break;
			default:
				Debugger.d(this.getClass(), "Unknown datatype for " + autonomous.name);
				break;

			}
		}

		// Create the teleop header label
		Label teleopHeader = new Label("TeleOp:");
		teleopHeader.setFont(new Font("Arial", 20));
		teleopHeader.setAlignment(Pos.CENTER);
		teleopHeader.setContentDisplay(ContentDisplay.CENTER);
		teleopHeader.setMaxWidth(Double.MAX_VALUE);
		VBox.setMargin(teleopHeader, new Insets(20, 0, 5, 0));
		pane.getChildren().add(teleopHeader);

		// Get the teleop fields from the config
		for (TeleOp teleop : ScoutingApp.config.matchData.teleopData) {

			switch (teleop.datatype) {
			case Boolean:
				// Create the check box
				CheckBox box = new CheckBox(teleop.name);
				box.setSelected(Boolean.parseBoolean(teleop.value.get(0).toString()));
				box.setFont(new Font("Arial", 15));
				box.setAlignment(Pos.CENTER);
				box.setContentDisplay(ContentDisplay.CENTER);
				box.setMaxWidth(Double.MAX_VALUE);
				VBox.setMargin(box, new Insets(5, 0, 5, 0));
				box.setId(teleop.name);
				pane.getChildren().add(box);
				break;
			case BooleanGroup:
				// Create the header for the group
				Label booleanGroupHeader = new Label(teleop.name);
				booleanGroupHeader.setFont(new Font("Arail", 15));
				booleanGroupHeader.setAlignment(Pos.CENTER);
				booleanGroupHeader.setContentDisplay(ContentDisplay.CENTER);
				booleanGroupHeader.setMaxWidth(Double.MAX_VALUE);
				VBox.setMargin(booleanGroupHeader, new Insets(5, 0, 0, 0));
				pane.getChildren().add(booleanGroupHeader);

				// Create a group for the radio buttons
				ToggleGroup group = new ToggleGroup();

				// Get the checkboxes from the value of the datatype
				JsonObject checkBoxes = teleop.value.get(0).asJsonObject();
				String[] keys = checkBoxes.keySet().toArray(new String[checkBoxes.size()]);
				for (int index = 0; index < checkBoxes.size(); index++) {
					// Get the current key at the index, and get the object value. This is the name
					// of the checkbox, and if this checked
					String key = keys[index];
					boolean checked = Boolean.parseBoolean(checkBoxes.get(key).toString());

					RadioButton teleGroup = new RadioButton(key);
					teleGroup.setSelected(checked);
					teleGroup.setFont(new Font("Arial", 15));
					teleGroup.setAlignment(Pos.CENTER);
					teleGroup.setContentDisplay(ContentDisplay.CENTER);
					teleGroup.setMaxWidth(Double.MAX_VALUE);
					teleGroup.setToggleGroup(group);
					teleGroup.setId(key);
					VBox.setMargin(teleGroup, new Insets(5, 0, 5, 0));
					pane.getChildren().add(teleGroup);
				}
				break;
			case Number:
				// Create the label header for the spinner
				Label spinnerHeader = new Label(teleop.name);
				spinnerHeader.setFont(new Font("Arial", 15));
				spinnerHeader.setAlignment(Pos.CENTER);
				spinnerHeader.setContentDisplay(ContentDisplay.CENTER);
				spinnerHeader.setMaxWidth(Double.MAX_VALUE);

				VBox.setMargin(spinnerHeader, new Insets(5, 0, 0, 0));
				pane.getChildren().add(spinnerHeader);

				// Create the actual spinner
				Spinner<Integer> spinner = new Spinner<Integer>(Integer.parseInt(teleop.value.get(1).toString()),
						Integer.parseInt(teleop.value.get(2).toString()),
						Integer.parseInt(teleop.value.get(0).toString()),
						Integer.parseInt(teleop.value.get(3).toString()));
				spinner.setEditable(true);
				spinner.setMaxWidth(Double.MAX_VALUE);
				spinner.setMinHeight(Double.NEGATIVE_INFINITY);
				spinner.setMinWidth(Double.NEGATIVE_INFINITY);

				// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
				spinner.getEditor().textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) {
							spinner.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
						}
					}
				});
				spinner.setId(teleop.name);
				VBox.setMargin(spinner, new Insets(5, 0, 5, 0));
				pane.getChildren().add(spinner);
				break;
			case Text:
				TextField text = new TextField(teleop.value.get(0).toString());
				text.setFont(new Font("Arial", 15));
				text.setEditable(true);
				text.setPromptText(teleop.name);
				text.setAlignment(Pos.CENTER);
				text.setMaxWidth(Double.MAX_VALUE);
				text.setId(teleop.name);
				VBox.setMargin(text, new Insets(5, 0, 5, 0));
				pane.getChildren().add(text);
				break;
			default:
				Debugger.d(this.getClass(), "Unknown datatype for " + teleop.name);
				break;

			}
		}

		// Create the endgame header label
		Label endgameHeader = new Label("End Game:");
		endgameHeader.setFont(new Font("Arial", 20));
		endgameHeader.setAlignment(Pos.CENTER);
		endgameHeader.setContentDisplay(ContentDisplay.CENTER);
		endgameHeader.setMaxWidth(Double.MAX_VALUE);
		VBox.setMargin(endgameHeader, new Insets(20, 0, 5, 0));
		pane.getChildren().add(endgameHeader);

		// Get the endgame fields from the config
		for (Endgame endgame : ScoutingApp.config.matchData.endgameData) {

			switch (endgame.datatype) {
			case Boolean:
				// Create the check box
				CheckBox endbox = new CheckBox(endgame.name);
				endbox.setSelected(Boolean.parseBoolean(endgame.value.get(0).toString()));
				endbox.setFont(new Font("Arial", 15));
				endbox.setAlignment(Pos.CENTER);
				endbox.setContentDisplay(ContentDisplay.CENTER);
				endbox.setMaxWidth(Double.MAX_VALUE);
				endbox.setId(endgame.name);
				VBox.setMargin(endbox, new Insets(5, 0, 5, 0));
				pane.getChildren().add(endbox);
				break;
			case BooleanGroup:
				// Create the header for the group
				Label booleanGroupHeader = new Label(endgame.name);
				booleanGroupHeader.setFont(new Font("Arail", 17));
				booleanGroupHeader.setAlignment(Pos.CENTER);
				booleanGroupHeader.setContentDisplay(ContentDisplay.CENTER);
				booleanGroupHeader.setMaxWidth(Double.MAX_VALUE);
				VBox.setMargin(booleanGroupHeader, new Insets(5, 0, 0, 0));
				pane.getChildren().add(booleanGroupHeader);

				// Create a group for the radio buttons
				ToggleGroup group = new ToggleGroup();

				// Get the checkboxes from the value of the datatype
				JsonObject checkBoxes = endgame.value.get(0).asJsonObject();
				String[] keys = checkBoxes.keySet().toArray(new String[checkBoxes.size()]);
				for (int index = 0; index < checkBoxes.size(); index++) {
					// Get the current key at the index, and get the object value. This is the name
					// of the checkbox, and if this checked
					String key = keys[index];
					boolean checked = Boolean.parseBoolean(checkBoxes.get(key).toString());

					RadioButton endGroup = new RadioButton(key);
					endGroup.setSelected(checked);
					endGroup.setFont(new Font("Arial", 15));
					endGroup.setAlignment(Pos.CENTER);
					endGroup.setContentDisplay(ContentDisplay.CENTER);
					endGroup.setMaxWidth(Double.MAX_VALUE);
					endGroup.setToggleGroup(group);
					endGroup.setId(key);
					VBox.setMargin(endGroup, new Insets(5, 0, 5, 0));
					pane.getChildren().add(endGroup);
				}
				break;
			case Number:
				// Create the label header for the spinner
				Label spinnerHeader = new Label(endgame.name);
				spinnerHeader.setFont(new Font("Arial", 15));
				spinnerHeader.setAlignment(Pos.CENTER);
				spinnerHeader.setContentDisplay(ContentDisplay.CENTER);
				spinnerHeader.setMaxWidth(Double.MAX_VALUE);

				VBox.setMargin(spinnerHeader, new Insets(5, 0, 0, 0));
				pane.getChildren().add(spinnerHeader);

				// Create the actual spinner
				Spinner<Integer> spinner = new Spinner<Integer>(Integer.parseInt(endgame.value.get(1).toString()),
						Integer.parseInt(endgame.value.get(2).toString()),
						Integer.parseInt(endgame.value.get(0).toString()),
						Integer.parseInt(endgame.value.get(3).toString()));
				spinner.setEditable(true);
				spinner.setMaxWidth(Double.MAX_VALUE);
				spinner.setMinHeight(Double.NEGATIVE_INFINITY);
				spinner.setMinWidth(Double.NEGATIVE_INFINITY);

				// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
				spinner.getEditor().textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) {
							spinner.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
						}
					}
				});
				spinner.setId(endgame.name);
				VBox.setMargin(spinner, new Insets(5, 0, 5, 0));
				pane.getChildren().add(spinner);
				break;
			case Text:
				TextField text = new TextField(endgame.value.get(0).toString());
				text.setFont(new Font("Arial", 15));
				text.setEditable(true);
				text.setPromptText(endgame.name);
				text.setAlignment(Pos.CENTER);
				text.setMaxWidth(Double.MAX_VALUE);
				text.setId(endgame.name);
				VBox.setMargin(text, new Insets(5, 0, 5, 0));
				pane.getChildren().add(text);
				break;
			default:
				Debugger.d(this.getClass(), "Unknown datatype for " + endgame.name);
				break;

			}
		}

		// Add the comments field, start with the header
		Label commentHeader = new Label("Additional Feedback (Optional):");
		commentHeader.setFont(new Font("Arial", 20));
		commentHeader.setAlignment(Pos.CENTER);
		commentHeader.setContentDisplay(ContentDisplay.CENTER);
		commentHeader.setMaxWidth(Double.MAX_VALUE);
		VBox.setMargin(commentHeader, new Insets(20, 0, 5, 0));
		pane.getChildren().add(commentHeader);

		// Now add the comment box
		TextArea comments = new TextArea();
		comments.setFont(new Font("Arial", 15));
		comments.setCursor(Cursor.TEXT);
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

}
