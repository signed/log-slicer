package com.github.signed.log.compare;

import com.github.signed.log.HBoxControlledOrphanage;
import com.github.signed.log.ViewOrphanage;
import com.github.signed.log.filteredlisting.LogEntryListingWithFilterInputView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.HBox;
import lang.ArgumentClosure;

public class SideBySideLogView {
    private final HBox container = new HBox();
    private final ScrollBar scrollBar = new ScrollBar();

    public SideBySideLogView(LogEntryListingWithFilterInputView left, LogEntryListingWithFilterInputView right) {
        ViewOrphanage viewOrphanage = new HBoxControlledOrphanage(container);
        left.addTo(viewOrphanage);
        container.getChildren().add(scrollBar);
        right.addTo(viewOrphanage);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setMin(0);
    }

    public void addTo(ViewOrphanage orphanage){
        orphanage.add(container);
    }

    public void adjustToDisplay(int numberOfEntries) {
        scrollBar.setMax(numberOfEntries);
    }

    public void onScroll(final ArgumentClosure<Integer> closure){
        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number1) {
                closure.excecute(number1.intValue());
            }
        });
    }
}
