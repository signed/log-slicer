package com.github.signed.log.list;

import com.github.signed.log.ViewOrphanage;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.filter.LogPartFilterView;
import com.github.signed.log.thread.ui.LoggedThreadProvider;
import com.github.signed.log.timestamp.ui.TimeStampProvider;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class LogView {
    private final BorderPane borderPane = new BorderPane();
    private final TableView<LogEntry> table = new TableView<>();
    private final LogPartFilterView filterView = new LogPartFilterView();

    public LogView() {
        table.getStyleClass().add("no-scroll-bars");
        createColumns();

        borderPane.setCenter(table);
        borderPane.setTop(filterView.node());
    }

    public void display(List<LogEntry> entries) {
        table.setItems(new ObservableListWrapper<>(entries));
    }

    public void addTo(ViewOrphanage pane) {
        pane.add(borderPane);
    }

    private void createColumns() {
        table.getColumns().add(createTimeStampColumn());
        table.getColumns().add(createThreadColumn());
    }

    private TableColumn<LogEntry, LogPart> createThreadColumn() {
        TableColumn<LogEntry, LogPart> threadColumn = new TableColumn<>("thread");
        threadColumn.setCellValueFactory(new LogPartCellValueFactory(new LoggedThreadProvider()));
        threadColumn.setCellFactory(new LogPartCellFactory());
        return threadColumn;
    }

    private TableColumn<LogEntry, LogPart> createTimeStampColumn() {
       TableColumn<LogEntry, LogPart> timestampColumn = new TableColumn<>("timestamp");
        timestampColumn.setCellValueFactory(new LogPartCellValueFactory(new TimeStampProvider()));
        timestampColumn.setCellFactory(new LogPartCellFactory());
        return timestampColumn;
    }

    public void scrollTo(int index) {
        table.scrollTo(index);
    }

    public LogPartFilterView filter() {
        return filterView;
    }
}