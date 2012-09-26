package com.github.signed.log.filteredlisting;

import com.github.signed.log.ViewOrphanage;
import com.github.signed.log.filter.LogPartFilterView;
import com.github.signed.log.list.BorderPaneControlledOrphanage;
import com.github.signed.log.list.LogView;
import javafx.scene.layout.BorderPane;

public class LogEntryListingWithFilterInputView {
    private final BorderPane borderPane = new BorderPane();
    private final LogPartFilterView filterView = new LogPartFilterView();
    private final LogView logView = new LogView();

    public LogEntryListingWithFilterInputView() {
        BorderPaneControlledOrphanage orphanage = new BorderPaneControlledOrphanage(borderPane);
        orphanage.nextAtCenter();
        logView.addTo(orphanage);
        orphanage.nextAtTop();
        filterView.addTo(orphanage);
    }

    public void addTo(ViewOrphanage pane) {
        pane.add(borderPane);
    }

    public void scrollTo(int index) {
        logView.scrollTo(index);
    }

    public LogPartFilterView filter() {
        return filterView;
    }

    public LogView logView() {
        return logView;
    }
}