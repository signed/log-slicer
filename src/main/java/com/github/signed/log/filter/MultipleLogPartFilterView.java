package com.github.signed.log.filter;

import javafx.OrphanView;
import javafx.ViewOrphanage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MultipleLogPartFilterView implements OrphanView {

    private final VBox vBox = new VBox();

    public MultipleLogPartFilterView() {
        vBox.getChildren().add(new Label("Place holder"));
    }

    @Override
    public void addTo(ViewOrphanage viewOrphanage) {
        viewOrphanage.add(vBox);
    }
}