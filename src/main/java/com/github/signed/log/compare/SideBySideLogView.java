package com.github.signed.log.compare;

import com.github.signed.log.HboxControledOrphanage;
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
    private final LogEntryListingWithFilterInputView left = new LogEntryListingWithFilterInputView();
    private final ScrollBar scrollBar = new ScrollBar();
    private final LogEntryListingWithFilterInputView right = new LogEntryListingWithFilterInputView();

    public SideBySideLogView() {
        ViewOrphanage viewOrphanage = new HboxControledOrphanage(container);
        left.addTo(viewOrphanage);
        container.getChildren().add(scrollBar);
        right.addTo(viewOrphanage);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setMin(0);
    }

    public void addTo(ViewOrphanage orphanage){
        orphanage.add(container);
    }

    public LogEntryListingWithFilterInputView left() {
        return left;
    }

    public LogEntryListingWithFilterInputView right() {
        return right;
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
