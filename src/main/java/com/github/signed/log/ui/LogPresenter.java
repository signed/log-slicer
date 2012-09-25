package com.github.signed.log.ui;

import com.github.signed.log.LogEntry;
import com.github.signed.log.LoggedThread;
import lang.ArgumentClosure;

import java.util.List;

public class LogPresenter {

    private final LogModel logModel;
    private final LogView logView;

    public LogPresenter(LogModel logModel, LogView logView) {
        this.logModel = logModel;
        this.logView = logView;
    }

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
                logModel.provideThreadChoicesTo(new ArgumentClosure<List<LoggedThread>>() {
                    @Override
                    public void excecute(List<LoggedThread> loggedThreads) {
                        logView.displayAvailableThreads(loggedThreads);
                    }
                });
            }
        });

    }
}
