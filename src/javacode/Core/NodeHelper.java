package javacode.Core;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
			} else if (node instanceof javafx.scene.control.ScrollPane) {
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

	
	
	public ArrayList<Node> getAllNodesFromParent(Parent root) {
		Debugger.d(getClass(), "Parent object class: " + root.getClass().getName());
		ArrayList<Node> Nodes = new ArrayList<Node>();
		if (root instanceof TabPane) {
			TabPane TabPaneNode = (TabPane) root;
			ObservableList<Tab> tabs = TabPaneNode.getTabs();
			for (Tab tab : tabs) {
				Node node = tab.getContent();
				if (node != null) {
					if (node instanceof HBox) {
						Nodes.addAll(getAllNodes(((HBox) node).getChildrenUnmodifiable()));
					} else if (node instanceof VBox) {
						Nodes.addAll(getAllNodes(((VBox) node).getChildrenUnmodifiable()));
					} else if (node instanceof javafx.scene.control.ScrollPane) {
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
			}
		}

		return Nodes;
	}
}
