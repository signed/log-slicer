package com.github.signed.log.filter;

import com.github.signed.log.list.Presenter;
import com.github.signed.log.list.SimpleLogModel;
import com.github.signed.log.thread.LoggedThread;
import lang.ArgumentClosure;

import java.util.List;

public class LogPartFilterPresenter implements Presenter {
    private final LogPartFilterView logPartFilterView;
    private final LogPartFilterModel model;
    private final SimpleLogModel logModel;

    public LogPartFilterPresenter(LogPartFilterView logPartFilterView, LogPartFilterModel model, SimpleLogModel logModel) {
        this.logPartFilterView = logPartFilterView;
        this.model = model;
        this.logModel = logModel;
    }

    @Override
    public void initialize() {
        logModel.onChange(new Runnable() {
            @Override
            public void run() {
                logModel.provideThreadChoicesTo(new ArgumentClosure<List<LoggedThread>>() {
                    @Override
                    public void excecute(List<LoggedThread> loggedThreads) {
                        logPartFilterView.displayAvailableThreads(loggedThreads);
                    }
                });
            }
        });

        logPartFilterView.onSelectedThreadChanges(new ArgumentClosure<LoggedThread>() {
            @Override
            public void excecute(LoggedThread loggedThread) {
                model.matches(loggedThread);
            }
        });
    }
}
