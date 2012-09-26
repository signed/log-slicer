package com.github.signed.log.list;

import com.github.signed.log.ViewOrphanage;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.thread.ui.LoggedThreadProvider;
import com.github.signed.log.timestamp.ui.TimeStampProvider;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class LogView {
    private final TableView<LogEntry> table = new TableView<LogEntry>();

    public LogView() {
        table.getStyleClass().add("no-scroll-bars");
        createColumns();
    }

    public void display(List<LogEntry> entries) {
        table.setItems(new ObservableListWrapper<LogEntry>(entries));
    }

    public void scrollTo(int index) {
        table.scrollTo(index);
    }

    public void addTo(ViewOrphanage viewOrphanage) {
        viewOrphanage.add(table);
    }

    private void createColumns() {
        table.getColumns().add(createTimeStampColumn());
        table.getColumns().add(createThreadColumn());
    }

    private TableColumn<LogEntry, LogPart> createThreadColumn() {
        TableColumn<LogEntry, LogPart> threadColumn = new TableColumn<LogEntry, LogPart>("thread");
        threadColumn.setCellValueFactory(new LogPartCellValueFactory(new LoggedThreadProvider()));
        threadColumn.setCellFactory(new LogPartCellFactory());
        return threadColumn;
    }

    private TableColumn<LogEntry, LogPart> createTimeStampColumn() {
        TableColumn<LogEntry, LogPart> timestampColumn = new TableColumn<LogEntry, LogPart>("timestamp");
        timestampColumn.setCellValueFactory(new LogPartCellValueFactory(new TimeStampProvider()));
        timestampColumn.setCellFactory(new LogPartCellFactory());
        return timestampColumn;
    }

}