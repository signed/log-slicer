package com.github.signed.log.list;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class LogPartCellValueFactory implements Callback<TableColumn.CellDataFeatures<LogEntry, LogPart>, ObservableValue<LogPart>> {
    private final LogPartProvider logPartProvider;

    public LogPartCellValueFactory(LogPartProvider logPartProvider) {
        this.logPartProvider = logPartProvider;
    }

    @Override
    public ObservableValue<LogPart> call(TableColumn.CellDataFeatures<LogEntry, LogPart> logEntryLoggedThreadCellDataFeatures) {
        LogEntry value = logEntryLoggedThreadCellDataFeatures.getValue();

        return new SimpleObjectProperty<>(logPartProvider.from(value));
    }
}
