package com.github.signed.log.ui.compare;

import com.github.signed.log.LogEntry;
import com.github.signed.log.LoggedThread;
import com.github.signed.log.ui.LogView;
import com.github.signed.log.ui.ViewOrphanage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.HBox;

import java.util.List;

public class SideBySideLogView {
    private final HBox container = new HBox();
    private final LogView left = new LogView();
    private final ScrollBar scrollBar = new ScrollBar();
    private final LogView right = new LogView();

    public SideBySideLogView() {
        ViewOrphanage viewOrphanage = new ViewOrphanage(container);
        left.addTo(viewOrphanage);
        container.getChildren().add(scrollBar);
        right.addTo(viewOrphanage);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setMin(0);
        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number1) {
                left.scrollTo(number1.intValue());
                right.scrollTo(number1.intValue());
            }
        });
    }

    public void displayLeft(List<LogEntry> entries){
        scrollBar.setMax(entries.size());
        left.display(entries);

    }

    public void displayRight(List<LogEntry> entries){
        scrollBar.setMax(entries.size());
        right.display(entries);
    }

    public void addTo(ViewOrphanage orphanage){
        orphanage.add(container);
    }

    public void displayAvailableThreadsLeft(List<LoggedThread> loggedThreads) {
        left.displayAvailableThreads(loggedThreads);
    }

    public void displayAvailableThreadsRight(List<LoggedThread> loggedThreads) {
        right.displayAvailableThreads(loggedThreads);
    }
}
