package com.github.signed.log.filteredlisting;

import com.github.signed.log.list.LogView;
import javafx.BorderPaneControlledOrphanage;
import javafx.OrphanView;
import javafx.ViewOrphanage;
import javafx.scene.layout.BorderPane;

public class LogEntryListingWithFilterInputView {

    public static LogEntryListingWithFilterInputView putFilterLeftOfLogs(OrphanView filterView, LogView logView) {
        return new LogEntryListingWithFilterInputView(filterView, logView, BorderPaneControlledOrphanage::nextLeft);
    }

    public static LogEntryListingWithFilterInputView putFilterRightOfLogs(OrphanView filterView, LogView logView) {
        return new LogEntryListingWithFilterInputView(filterView, logView, BorderPaneControlledOrphanage::nextRight);
    }

    private final BorderPane borderPane = new BorderPane();

    public LogEntryListingWithFilterInputView(OrphanView filterView, LogView logView, FilterPositionStrategy strategy) {
        BorderPaneControlledOrphanage orphanage = new BorderPaneControlledOrphanage(borderPane);
        orphanage.nextAtCenter();
        logView.addTo(orphanage);
        strategy.pickPositionForFilter(orphanage);
        filterView.addTo(orphanage);
    }

    public void addTo(ViewOrphanage pane) {
        pane.add(borderPane);
    }

    public static interface FilterPositionStrategy {
        void pickPositionForFilter(BorderPaneControlledOrphanage orphanage);
    }
}