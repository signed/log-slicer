package com.github.signed.log.ui;

import com.github.signed.log.LogEntry;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.joda.time.DateTime;

import java.util.List;

public class LogView {
    private final TableView<LogEntry> table = new TableView<>();

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
    }

    public void display(List<LogEntry> entries){
        table.setItems(new ObservableListWrapper<>(entries));
    }

    public void addTo(Pane pane) {
        pane.getChildren().add(table);
    }

}
