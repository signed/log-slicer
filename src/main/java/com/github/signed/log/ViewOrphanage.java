package com.github.signed.log;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class ViewOrphanage {

    private final Pane pane;

    public ViewOrphanage(Pane pane) {
        this.pane = pane;
    }

    public void add(Node node) {
        HBox.setHgrow(node, Priority.ALWAYS);
        pane.getChildren().add(node);
    }
}
