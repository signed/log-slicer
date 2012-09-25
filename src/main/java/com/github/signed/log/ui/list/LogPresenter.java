package com.github.signed.log.ui.list;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.thread.LoggedThread;
import com.github.signed.log.ui.compare.SideBySideLogView;
import lang.ArgumentClosure;

import java.util.List;

public class LogPresenter {

    private final LogModel logModel;
    private final SideBySideLogView logView;

    public LogPresenter(LogModel logModel, SideBySideLogView logView) {
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
                        logView.displayLeft(logEntries);
                        logView.displayRight(logEntries);
                    }
                });
                logModel.provideThreadChoicesTo(new ArgumentClosure<List<LoggedThread>>() {
                    @Override
                    public void excecute(List<LoggedThread> loggedThreads) {
                        logView.displayAvailableThreadsLeft(loggedThreads);
                        logView.displayAvailableThreadsRight(loggedThreads);
                    }
                });
            }
        });

    }
}
