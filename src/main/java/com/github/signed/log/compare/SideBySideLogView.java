package com.github.signed.log.compare;

import com.github.signed.log.filteredlisting.LogEntryListingWithFilterInputView;
import javafx.HBoxControlledOrphanage;
import javafx.ViewOrphanage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lang.ArgumentClosure;

public class SideBySideLogView {
    private final HBox container = new HBox();
    private final ScrollBar scrollBar = new ScrollBar();

    public SideBySideLogView(LogEntryListingWithFilterInputView left, LogEntryListingWithFilterInputView right) {
        HBoxControlledOrphanage viewOrphanage = new HBoxControlledOrphanage(container);
        viewOrphanage.nextGrabHorizontalSpace(Priority.ALWAYS);
        left.addTo(viewOrphanage);
        container.getChildren().add(scrollBar);
        viewOrphanage.nextGrabHorizontalSpace(Priority.ALWAYS);
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
                closure.execute(number1.intValue());
            }
        });
    }
}
