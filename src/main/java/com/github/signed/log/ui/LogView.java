package com.github.signed.log.ui;

import com.github.signed.log.LogEntry;
import com.github.signed.log.LoggedThread;
import com.github.signed.log.TimeStamp;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.util.List;

public class LogView {
    private final BorderPane borderPane = new BorderPane();
    private final TableView<LogEntry> table = new TableView<>();
    private final ComboBox<LoggedThread> availableThreads = new ComboBox<>();

    public LogView(){
        table.getStyleClass().add("no-scroll-bars");
        createColumns();
        createThreadsComboBox();
        borderPane.setCenter(table);
        borderPane.setTop(availableThreads);
    }

    public void display(List<LogEntry> entries){
        table.setItems(new ObservableListWrapper<>(entries));
    }

    public void displayAvailableThreads(List<LoggedThread> threads){
        availableThreads.setItems(new ObservableListWrapper<>(threads));
    }

    public void addTo(ViewOrphanage pane) {
        pane.add(borderPane);
    }


    private void createColumns() {
        table.getColumns().add(createTimeStampColumn());
        table.getColumns().add(createThreadColumn());
    }

    private TableColumn<LogEntry, LoggedThread> createThreadColumn() {
        TableColumn<LogEntry, LoggedThread> threadColumn = new TableColumn<>("thread");
        threadColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LogEntry, LoggedThread>, ObservableValue<LoggedThread>>() {
            @Override
            public ObservableValue<LoggedThread> call(TableColumn.CellDataFeatures<LogEntry, LoggedThread> logEntryLoggedThreadCellDataFeatures) {
                LogEntry value = logEntryLoggedThreadCellDataFeatures.getValue();
                return new SimpleObjectProperty<>(value.thread());
            }
        });

        threadColumn.setCellFactory(new Callback<TableColumn<LogEntry, LoggedThread>, TableCell<LogEntry, LoggedThread>>() {
            @Override
            public TableCell<LogEntry, LoggedThread> call(TableColumn<LogEntry, LoggedThread> logEntryLoggedThreadTableColumn) {
                return new TableCell<LogEntry, LoggedThread>() {
                    @Override
                    protected void updateItem(LoggedThread loggedThread, boolean b) {
                        super.updateItem(loggedThread, b);
                        if (loggedThread == null) {
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

    private TableColumn<LogEntry, TimeStamp> createTimeStampColumn() {
        TableColumn<LogEntry, TimeStamp> timestampColumn = new TableColumn<>();
        timestampColumn.setText("timestamp");
        timestampColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LogEntry, TimeStamp>, ObservableValue<TimeStamp>>() {
            @Override
            public ObservableValue<TimeStamp> call(TableColumn.CellDataFeatures<LogEntry, TimeStamp> logEntryDateTimeCellDataFeatures) {
                return new SimpleObjectProperty<>(logEntryDateTimeCellDataFeatures.getValue().taken());
            }
        });
        timestampColumn.setCellFactory(new Callback<TableColumn<LogEntry, TimeStamp>, TableCell<LogEntry, TimeStamp>>() {
            @Override
            public TableCell<LogEntry, TimeStamp> call(TableColumn<LogEntry, TimeStamp> logEntryDateTimeTableColumn) {
                return new TableCell<LogEntry, TimeStamp>(){
                    @Override
                    protected void updateItem(TimeStamp timeStamp, boolean b) {
                        if(null == timeStamp){
                            setText("null");
                            return;
                        }
                        StringBuilder builder = new StringBuilder();
                        timeStamp.dumpInto(builder);
                        setText(builder.toString());
                    }
                };
            }
        });
        return timestampColumn;
    }

    public void scrollTo(int index) {
        table.scrollTo(index);
    }
}