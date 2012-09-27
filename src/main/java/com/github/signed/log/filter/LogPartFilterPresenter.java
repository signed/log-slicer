package com.github.signed.log.filter;

import com.github.signed.log.list.Presenter;
import com.github.signed.log.thread.LoggedThread;
import lang.ArgumentClosure;

import java.util.List;

public class LogPartFilterPresenter implements Presenter {
    private final LogPartFilterView view;
    private final LogPartFilterModel model;

    public LogPartFilterPresenter(LogPartFilterView view, LogPartFilterModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void initialize() {
        model.onAvailableThreadsChanges(new Runnable() {
            @Override
            public void run() {
                model.provideThreadChoicesTo(new ArgumentClosure<List<LoggedThread>>() {
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

        view.onDiscardFilter(new ArgumentClosure<LoggedThread>() {
            @Override
            public void excecute(LoggedThread loggedThread) {
                model.discardFilter(loggedThread);
            }
        });

        model.onChange(new Runnable() {
            @Override
            public void run() {
                model.provideSelectedThreadTo(new ArgumentClosure<List<LoggedThread>>() {
                    @Override
                    public void excecute(List<LoggedThread> loggedThread) {
                        view.displaySelectedFilter(loggedThread);
                    }
                });

            }
        });
    }
}
