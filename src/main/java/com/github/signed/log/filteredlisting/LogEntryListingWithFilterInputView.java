package com.github.signed.log.filteredlisting;

import com.github.signed.log.filter.LogPartFilterView;
import com.github.signed.log.list.LogView;
import javafx.BorderPaneControlledOrphanage;
import javafx.ViewOrphanage;
import javafx.scene.layout.BorderPane;

public class LogEntryListingWithFilterInputView {

    public static LogEntryListingWithFilterInputView putFilterLeftOfLogs(LogPartFilterView filterView, LogView logView) {
        return new LogEntryListingWithFilterInputView(filterView, logView, new FilterPositionStrategy() {
            @Override
            public void pickPositionForFilter(BorderPaneControlledOrphanage orphanage) {
                orphanage.nextLeft();
            }
        });
    }

    public static LogEntryListingWithFilterInputView putFilterRightOfLogs(LogPartFilterView filterView, LogView logView) {
        return new LogEntryListingWithFilterInputView(filterView, logView, new FilterPositionStrategy() {
            @Override
            public void pickPositionForFilter(BorderPaneControlledOrphanage orphanage) {
                orphanage.nextRight();
            }
        });
    }

    private final BorderPane borderPane = new BorderPane();

    public LogEntryListingWithFilterInputView(LogPartFilterView filterView, LogView logView, FilterPositionStrategy strategy) {
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