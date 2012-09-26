package com.github.signed.log;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class HboxControledOrphanage implements ViewOrphanage {

    private final HBox hBox;

    public HboxControledOrphanage(HBox hBox) {
        this.hBox = hBox;
    }

    @Override
    public void add(Node node) {
        HBox.setHgrow(node, Priority.ALWAYS);
        hBox.getChildren().add(node);
    }
}
