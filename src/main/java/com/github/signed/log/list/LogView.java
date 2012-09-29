package com.github.signed.log.list;

import com.github.signed.log.ViewOrphanage;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.ui.KeyBasedLogPartProvider;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class LogView {
    private final TableView<LogEntry> table = new TableView<>();

    public LogView() {
        table.getStyleClass().add("no-scroll-bars");
        table.setRowFactory(new LogEntryRowFactory());
    }

    public void display(List<LogEntry> entries) {
        table.setItems(new ObservableListWrapper<>(entries));
    }

    public void scrollTo(int index) {
        table.scrollTo(index);
    }

    public void addTo(ViewOrphanage viewOrphanage) {
        viewOrphanage.add(table);
    }

    public void showLogPart(String name, Identification identification) {
        table.getColumns().add(createColumn(name, identification));
    }

    private TableColumn<LogEntry, LogPart> createColumn(String columnName, Identification identification) {
        TableColumn<LogEntry, LogPart> timestampColumn = new TableColumn<>(columnName);
        timestampColumn.setCellValueFactory(new LogPartCellValueFactory(new KeyBasedLogPartProvider(identification)));
        timestampColumn.setCellFactory(new LogPartCellFactory());
        return timestampColumn;
    }

    public void clearDisplayedLogParts() {
        table.getColumns().clear();
    }

}