package com.github.signed.log.list;

import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import lang.ArgumentClosure;

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
                    logEntry.dumpPartInto(new Identification("Complete Line"), new ArgumentClosure<String>() {
                        @Override
                        public void execute(String s) {
                            setTooltip(new Tooltip(s));
                        }
                    });
                }
            }
        };
    }
}
