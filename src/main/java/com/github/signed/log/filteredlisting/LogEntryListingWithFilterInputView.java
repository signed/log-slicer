package com.github.signed.log.filteredlisting;

import com.github.signed.log.BorderPaneControlledOrphanage;
import com.github.signed.log.ViewOrphanage;
import com.github.signed.log.filter.LogPartFilterView;
import com.github.signed.log.list.LogView;
import javafx.scene.layout.BorderPane;

public class LogEntryListingWithFilterInputView {
    private final BorderPane borderPane = new BorderPane();

    public LogEntryListingWithFilterInputView(LogPartFilterView filterView, LogView logView) {
        BorderPaneControlledOrphanage orphanage = new BorderPaneControlledOrphanage(borderPane);
        orphanage.nextAtCenter();
        logView.addTo(orphanage);
        orphanage.nextAtTop();
        filterView.addTo(orphanage);
    }

    public void addTo(ViewOrphanage pane) {
        pane.add(borderPane);
    }
}