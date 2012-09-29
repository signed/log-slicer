package com.github.signed.log.filter;

import com.github.signed.log.core.ui.Presenter;
import com.github.signed.log.thread.LoggedThread;
import lang.ArgumentClosure;
import lang.ArgumentClosureToggle;

import java.util.List;

public class LogPartFilterPresenter implements Presenter {
    private final LogPartFilterView view;
    private final LogPartFilterModel model;
    private final ArgumentClosureToggle<LoggedThread> updateModelWithSelection;


    public LogPartFilterPresenter(LogPartFilterView view, LogPartFilterModel model) {
        this.view = view;
        this.model = model;
        updateModelWithSelection = ArgumentClosureToggle.toggleAround(new UpdateModelWithSelection(this.model));

    }

    @Override
    public void initialize() {
        model.onLogEntryChange(new PassSelectedFiltersToView(model, view));
        model.onAvailableThreadsChanges(new Runnable() {
            @Override
            public void run() {
                updateModelWithSelection.suspend();
                model.provideThreadChoicesTo(new ArgumentClosure<List<LoggedThread>>() {
                    @Override
                    public void excecute(List<LoggedThread> loggedThreads) {
                        view.displayAvailableThreads(loggedThreads);
                    }
                });
                updateModelWithSelection.activate();
            }
        });

        view.onSelectedThreadChanges(updateModelWithSelection);
        view.onDiscardFilter(new DiscardFilterInModel(model));

    }


    private static class UpdateModelWithSelection implements ArgumentClosure<LoggedThread> {
        private final LogPartFilterModel model;

        public UpdateModelWithSelection(LogPartFilterModel model) {
            this.model = model;
        }

        @Override
        public void excecute(LoggedThread loggedThread) {
                model.matches(loggedThread);

        }
    }

    private static class DiscardFilterInModel implements ArgumentClosure<LoggedThread> {
        private final LogPartFilterModel model;

        public DiscardFilterInModel(LogPartFilterModel model) {
            this.model = model;
        }

        @Override
        public void excecute(LoggedThread loggedThread) {
            model.discardFilter(loggedThread);
        }
    }

    private class PassSelectedFiltersToView implements Runnable {
        private final LogPartFilterModel model;
        private final LogPartFilterView view;

        public PassSelectedFiltersToView(LogPartFilterModel model, LogPartFilterView view) {
            this.model = model;
            this.view = view;
        }

        @Override
        public void run() {
            model.provideSelectedThreadsTo(new ArgumentClosure<List<LoggedThread>>() {
                @Override
                public void excecute(List<LoggedThread> loggedThread) {
                    view.displaySelectedFilter(loggedThread);
                }
            });
        }
    }
}
