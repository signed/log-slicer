package com.github.signed.log.ui.compare;

import com.github.signed.log.LogEntry;
import com.github.signed.log.LoggedThread;
import com.github.signed.log.ui.LogView;
import com.github.signed.log.ui.ViewOrphanage;
import javafx.scene.layout.HBox;

import java.util.List;

public class SideBySideLogView {
    private final HBox container = new HBox();
    private final LogView left = new LogView();
    private final LogView right = new LogView();

    public SideBySideLogView() {
        ViewOrphanage viewOrphanage = new ViewOrphanage(container);
        left.addTo(viewOrphanage);
        right.addTo(viewOrphanage);
    }

    public void displayLeft(List<LogEntry> entries){
        left.display(entries);
    }

    public void displayRight(List<LogEntry> entries){
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
