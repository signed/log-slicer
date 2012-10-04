package com.github.signed.log.filter;

import javafx.OrphanView;
import javafx.VBoxControlledOrphanage;
import javafx.ViewOrphanage;
import javafx.scene.layout.VBox;

public class MultipleLogPartFilterView implements OrphanView {

    private final VBox vBox = new VBox();

    public MultipleLogPartFilterView() {
        vBox.setMaxWidth(200);
    }

    @Override
    public void addTo(ViewOrphanage viewOrphanage) {
        viewOrphanage.add(vBox);
    }

    public void clearExistingFilterViews(){
        vBox.getChildren().clear();
    }

    public LogPartFilterView newPartFilter(String name) {
        LogPartFilterView filterView = new LogPartFilterView(name);
        VBoxControlledOrphanage orphanage = new VBoxControlledOrphanage(vBox);
        filterView.addTo(orphanage);
        return filterView;
    }
}