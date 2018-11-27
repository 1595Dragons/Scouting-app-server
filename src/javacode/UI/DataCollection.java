package javacode.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javacode.Core.Debugger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DataCollection {

	private int teamNumber, autoSwitchCube;
	
	private CheckBox hasAutoCheck, canClimb;
	


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
			
			Spinner<Integer> autoSwitchNumber = null, autoScaleNumber = null;

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
						autoSwitchNumber = (Spinner<Integer>) node;
						autoSwitchNumber.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0, 1));
					} else if (node.getId().equals("autoScaleNumber")) {
						autoScaleNumber = (Spinner<Integer>) node;
						autoScaleNumber.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0, 1));
					} else {
						Debugger.d(getClass(), String.format("Unused node: (%s) %s", node.getClass(), node.getId()));
					}
				}
			}
			
			if (this.hasAutoCheck != null && autoScaleNumber != null && autoSwitchNumber != null) {
				final Spinner<Integer>  aSCn = autoScaleNumber, aSWn = autoSwitchNumber;
				this.hasAutoCheck.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						boolean isNotChecked = !hasAutoCheck.isSelected();
						aSCn.setDisable(isNotChecked);
						aSWn.setDisable(isNotChecked);
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
}
