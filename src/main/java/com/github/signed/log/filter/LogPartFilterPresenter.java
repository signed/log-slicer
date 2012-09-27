package com.github.signed.log.filter;

import com.github.signed.log.list.Presenter;
import com.github.signed.log.list.SimpleLogModel;
import com.github.signed.log.thread.LoggedThread;
import lang.ArgumentClosure;

import java.util.List;

public class LogPartFilterPresenter implements Presenter {
    private final LogPartFilterView view;
    private final LogPartFilterModel model;
    private final SimpleLogModel logModel;

    public LogPartFilterPresenter(LogPartFilterView view, LogPartFilterModel model, SimpleLogModel logModel) {
        this.view = view;
        this.model = model;
        this.logModel = logModel;
    }

    @Override
    public void initialize() {
        model.onAvailableThreadsChanges(new Runnable() {
            @Override
            public void run() {
                logModel.provideThreadChoicesTo(new ArgumentClosure<List<LoggedThread>>() {
                    @Override
                    public void excecute(List<LoggedThread> loggedThreads) {
                        view.displayAvailableThreads(loggedThreads);
                    }
                });
            }
        });

        view.onSelectedThreadChanges(new ArgumentClosure<LoggedThread>() {
            @Override
            public void excecute(LoggedThread loggedThread) {
                model.matches(loggedThread);
            }
        });

        model.onChange(new Runnable() {
            @Override
            public void run() {
                model.provideSelectedThreadTo(new ArgumentClosure<LoggedThread>() {
                    @Override
                    public void excecute(LoggedThread loggedThread) {
                        view.displaySelectedFilter(loggedThread);
                    }
                });

            }
        });



    }
}
