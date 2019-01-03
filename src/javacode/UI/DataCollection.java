package javacode.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.json.JsonObject;

import javacode.ScoutingApp;
import javacode.Core.Debugger;
import javacode.Core.Match.Autonomous;
import javacode.Core.NodeHelper;
import javacode.FileManager.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DataCollection {

	private int teamNumber;
	private Spinner<Integer> autoSwitchNumber, autoScaleNumber, teleSwitchNumber, teleScaleNumber, teleExchangeNumber;
	private CheckBox hasAutoCheck, canClimb;
	private TextArea feedback;
	private Button Submit, Cancel;

	private static Stage stage;

	public DataCollection(int number) {
		this.teamNumber = number;
	}

	public Scene createDataCollectionPage() throws IOException {
		Parent root = null;

		// Get the path of the main panel's FXML file
		URL path = getClass().getClassLoader().getResource("javacode/fxml/DataPage.fxml");
		Debugger.d(getClass(), "Path: " + path.toString());

		// Load the FXML from the layout file
		try {
			root = FXMLLoader.load(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (root != null) {

			Label teamnumberheader = null;

			this.generateFromConfig(root);

			// Create an array of all the nodes
			ArrayList<Node> Nodes = new ArrayList<Node>();
			Nodes = new NodeHelper().getAllNodes(root.getChildrenUnmodifiable());
			Debugger.d(getClass(), "List of all nodes: " + Nodes.toString());

			for (Node node : Nodes) {
				if (node.getId() != null) {
					if (node.getId().equals("teamNumberHeader")) {
						teamnumberheader = (Label) node;
						teamnumberheader.setText("Scouting team: " + this.teamNumber);
					} else if (node.getId().equals("hasAutoCheck")) {
						this.hasAutoCheck = (CheckBox) node;
					} else if (node.getId().equals("canClimb")) {
						this.canClimb = (CheckBox) node;
					} else if (node.getId().equals("autoSwitchNumber")) {
						this.autoSwitchNumber = (Spinner<Integer>) node;
						this.autoSwitchNumber
								.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0, 1));
					} else if (node.getId().equals("autoScaleNumber")) {
						this.autoScaleNumber = (Spinner<Integer>) node;
						this.autoScaleNumber
								.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0, 1));
					} else if (node.getId().equals("teleSwitchNumber")) {
						this.teleSwitchNumber = (Spinner<Integer>) node;
						this.teleSwitchNumber
								.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0, 1));
					} else if (node.getId().equals("teleScaleNumber")) {
						this.teleScaleNumber = (Spinner<Integer>) node;
						this.teleScaleNumber
								.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0, 1));
					} else if (node.getId().equals("exchangeNumber")) {
						this.teleExchangeNumber = (Spinner<Integer>) node;
						this.teleExchangeNumber
								.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0, 1));
					} else if (node.getId().equals("feedback")) {
						this.feedback = (TextArea) node;
					} else if (node.getId().equals("CancelButton")) {
						this.Cancel = (Button) node;
					} else if (node.getId().equals("SubmitButton")) {
						this.Submit = (Button) node;
					} else {
						Debugger.d(getClass(), String.format("Unused node: (%s) %s", node.getClass(), node.getId()));
					}
				}
			}

			if (this.autoScaleNumber != null && this.autoSwitchNumber != null) {
				if (this.hasAutoCheck != null) {
					this.hasAutoCheck.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent arg0) {
							boolean isNotChecked = !hasAutoCheck.isSelected();
							autoScaleNumber.setDisable(isNotChecked);
							autoSwitchNumber.setDisable(isNotChecked);
						}
					});
				}

				// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
				this.autoScaleNumber.getEditor().textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) {
							autoScaleNumber.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
						}
					}
				});

				// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
				this.autoSwitchNumber.getEditor().textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) {
							autoSwitchNumber.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
						}
					}
				});
			}

			if (this.teleSwitchNumber != null) {

				// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
				this.teleSwitchNumber.getEditor().textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) {
							teleSwitchNumber.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
						}
					}
				});
			}

			if (this.teleScaleNumber != null) {

				// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
				this.teleScaleNumber.getEditor().textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) {
							teleScaleNumber.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
						}
					}
				});
			}

			if (this.teleExchangeNumber != null) {

				// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
				this.teleExchangeNumber.getEditor().textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) {
							teleExchangeNumber.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
						}
					}
				});
			}

			if (this.Cancel != null) {
				this.Cancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						getStage().close();
					}
				});
			}

			if (this.Submit != null) {
				this.Submit.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						// TODO: Finish submit function
						// new Database().updateDatabase(teamNumber, hasAutoCheck.isSelected(),
						// autoSwitchNumber.getValue(), autoScaleNumber.getValue(),
						// teleSwitchNumber.getValue(), teleScaleNumber.getValue(),
						// teleExchangeNumber.getValue(), canClimb.isSelected(), feedback.getText());
						getStage().close();
					}
				});
			}

			Scene scene = new Scene(root);
			return scene;

		} else {
			throw new IOException("Cannot load team number dialog FXML");
		}
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		DataCollection.stage = stage;
	}

	private void generateFromConfig(Parent root) {

		// Get the VBox that will house all of the dynamically loaded data
		VBox pane = (VBox) ((ScrollPane) root.getChildrenUnmodifiable().get(1)).getContent();

		// Create the autonomous header label
		Label autonomousHeader = new Label("Autonomous");
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
				VBox.setMargin(text, new Insets(5, 0, 5, 0));
				pane.getChildren().add(text);
				break;
			default:
				Debugger.d(this.getClass(), "Unknown datatype for " + autonomous.name);
				break;

			}

		}

	}

}
