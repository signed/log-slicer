package com.github.signed.log.list;

import com.github.signed.log.RawLogEntry;
import com.github.signed.log.core.LogEntry;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

public class LogEntryRowFactory implements Callback<TableView<LogEntry>, TableRow<LogEntry>> {
    @Override
    public TableRow<LogEntry> call(TableView<LogEntry> logEntryTableView) {
        return new TableRow<LogEntry>(){
            @Override
            protected void updateItem(LogEntry logEntry, boolean b) {
                super.updateItem(logEntry, b);
                if(null == logEntry){
                    setTooltip(null);
                }else{
                    RawLogEntry part = logEntry.getPart(RawLogEntry.class);
                    StringBuilder builder = new StringBuilder();
                    part.dumpInto(builder);
                    setTooltip(new Tooltip(builder.toString()));
                }
            }
        };
    }
}
