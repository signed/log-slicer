package com.github.signed.log.ui.list;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.thread.LoggedThread;
import com.github.signed.log.thread.ui.LoggedThreadProvider;
import com.github.signed.log.timestamp.ui.TimeStampProvider;
import com.github.signed.log.ui.ViewOrphanage;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.util.List;

public class LogView {
    private final BorderPane borderPane = new BorderPane();
    private final TableView<LogEntry> table = new TableView<>();
    private final ComboBox<LoggedThread> availableThreads = new ComboBox<>();

    public LogView() {
        table.getStyleClass().add("no-scroll-bars");
        createColumns();
        createThreadsComboBox();
        borderPane.setCenter(table);
        borderPane.setTop(availableThreads);
    }

    public void display(List<LogEntry> entries) {
        table.setItems(new ObservableListWrapper<>(entries));
    }

    public void displayAvailableThreads(List<LoggedThread> threads) {
        availableThreads.setItems(new ObservableListWrapper<>(threads));
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

    private void createThreadsComboBox() {
        availableThreads.setPromptText("threads");
        availableThreads.setCellFactory(new Callback<ListView<LoggedThread>, ListCell<LoggedThread>>() {
            @Override
            public ListCell<LoggedThread> call(ListView<LoggedThread> loggedThreadListView) {
                return new ListCell<LoggedThread>() {
                    @Override
                    protected void updateItem(LoggedThread loggedThread, boolean b) {
                        super.updateItem(loggedThread, b);
                        if (null == loggedThread) {
                            setText("null");
                            return;
                        }
                        StringBuilder builder = new StringBuilder();
                        loggedThread.dumpInto(builder);
                        setText(builder.toString());
                    }
                };
            }
        });
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

}