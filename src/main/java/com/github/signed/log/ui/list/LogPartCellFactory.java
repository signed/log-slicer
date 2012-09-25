package com.github.signed.log.ui.list;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class LogPartCellFactory implements Callback<TableColumn<LogEntry, LogPart>, TableCell<LogEntry, LogPart>> {
    @Override
    public TableCell<LogEntry, LogPart> call(TableColumn<LogEntry, LogPart> logEntryLoggedThreadTableColumn) {
        return new TableCell<LogEntry, LogPart>() {
            @Override
            protected void updateItem(LogPart logPart, boolean b) {
                super.updateItem(logPart, b);
                if (logPart == null) {
                    setText("null");
                    return;
                }
                StringBuilder builder = new StringBuilder();
                logPart.dumpInto(builder);
                setText(builder.toString());
            }
        };
    }
}
