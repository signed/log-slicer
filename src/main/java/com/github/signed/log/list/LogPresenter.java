package com.github.signed.log.list;

import com.github.signed.log.core.LogEntry;
import lang.ArgumentClosure;

import java.util.List;

public class LogPresenter implements Presenter {

    private final LogView logView;
    private final LogModel logModel;

    public LogPresenter(LogModel logModel, LogView logView) {
        this.logModel = logModel;
        this.logView = logView;
    }

    @Override
    public void initialize() {
        logModel.onChange(new Runnable() {
            @Override
            public void run() {
                logModel.provideElementsTo(new ArgumentClosure<List<LogEntry>>() {
                    @Override
                    public void excecute(List<LogEntry> logEntries) {
                        logView.display(logEntries);
                    }
                });
            }
        });
    }

    public void scrollTo(Integer index) {
        logView.scrollTo(index);
    }
}