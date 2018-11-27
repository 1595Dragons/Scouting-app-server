package javacode.Core;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NodeHelper {
	public ArrayList<Node> getAllNodes(ObservableList<Node> parent) {
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
