package com.github.signed.log.ui;

import com.github.signed.log.LogEntry;
import com.github.signed.log.LoggedThread;
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
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.joda.time.DateTime;

import java.util.List;

public class LogView {
    private final BorderPane borderPane = new BorderPane();
    private final TableView<LogEntry> table = new TableView<>();
    private final ComboBox<LoggedThread> availableThreads = new ComboBox<>();

    public LogView(){
        TableColumn<LogEntry, DateTime> timestampColumn = new TableColumn<>();
        timestampColumn.setText("timestamp");
        timestampColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LogEntry, DateTime>, ObservableValue<DateTime>>() {
            @Override
            public ObservableValue<DateTime> call(TableColumn.CellDataFeatures<LogEntry, DateTime> logEntryDateTimeCellDataFeatures) {
                return new SimpleObjectProperty<>(logEntryDateTimeCellDataFeatures.getValue().taken());
            }
        });
        timestampColumn.setCellFactory(new Callback<TableColumn<LogEntry, DateTime>, TableCell<LogEntry, DateTime>>() {
            @Override
            public TableCell<LogEntry, DateTime> call(TableColumn<LogEntry, DateTime> logEntryDateTimeTableColumn) {
                return new TableCell<LogEntry, DateTime>(){
                    @Override
                    protected void updateItem(DateTime dateTime, boolean b) {
                        if(null == dateTime){
                            setText("null");
                            return;
                        }
                        setText(dateTime.toString());
                    }
                };
            }
        });
        table.getColumns().add(timestampColumn);

        availableThreads.setPromptText("threads");
        availableThreads.setCellFactory(new Callback<ListView<LoggedThread>, ListCell<LoggedThread>>() {
            @Override
            public ListCell<LoggedThread> call(ListView<LoggedThread> loggedThreadListView) {
                return new ListCell<LoggedThread>(){
                    @Override
                    protected void updateItem(LoggedThread loggedThread, boolean b) {
                        super.updateItem(loggedThread, b);
                        if(null == loggedThread){
                            setText("null");
                            return;
                        }
                        setText(loggedThread.toString());
                    }
                };
            }
        });


        borderPane.setCenter(table);
        borderPane.setTop(availableThreads);

    }

    public void display(List<LogEntry> entries){
        table.setItems(new ObservableListWrapper<>(entries));
    }

    public void displayAvailableThreads(List<LoggedThread> threads){
        availableThreads.setItems(new ObservableListWrapper<>(threads));
    }

    public void addTo(Pane pane) {
        pane.getChildren().add(borderPane);
    }

}
