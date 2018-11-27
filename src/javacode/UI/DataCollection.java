package javacode.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javacode.Core.Debugger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

			// Create an array of all the nodes
			ArrayList<Node> Nodes = new ArrayList<Node>();
			Nodes = getAllNodes(root.getChildrenUnmodifiable());
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
				
				this.autoScaleNumber.getEditor().textProperty().addListener(new ChangeListener<String>() {
				    @Override
				    public void changed(ObservableValue<? extends String> observable, String oldValue, 
				        String newValue) {
				        if (!newValue.matches("\\d*")) {
				            autoScaleNumber.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
				        }
				    }
				});
				
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
						// TODO: Write to SQL database
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

	private ArrayList<Node> getAllNodes(ObservableList<Node> parent) {
		ArrayList<Node> Nodes = new ArrayList<Node>();
		for (Node node : parent) {
			if (node instanceof HBox) {
				Nodes.addAll(getAllNodes(((HBox) node).getChildrenUnmodifiable()));
			} else if (node instanceof VBox) {
				Nodes.addAll(getAllNodes(((VBox) node).getChildrenUnmodifiable()));
			} else if (node.getClass().toString().contains("ScrollPane")) {
				Node ScrollPaneNode = ((ScrollPane) node).getContent();
				if (ScrollPaneNode instanceof HBox) {
					Nodes.addAll(getAllNodes(((HBox) ScrollPaneNode).getChildrenUnmodifiable()));
				} else if (ScrollPaneNode instanceof VBox) {
					Nodes.addAll(getAllNodes(((VBox) ScrollPaneNode).getChildrenUnmodifiable()));
				} else {
					Nodes.add(ScrollPaneNode);
				}
			} else {
				Nodes.add(node);
			}
		}
		return Nodes;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		DataCollection.stage = stage;
	}
}
