package com.github.signed.log.filter;

import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.parser.LogEntryParser;
import com.github.signed.log.core.ui.Presenter;
import lang.ArgumentClosure;
import lang.ArgumentClosureToggle;

import java.util.List;

public class LogPartFilterPresenter implements Presenter {
    private final LogPartFilterView view;
    private final LogPartFilterModel model;
    private final ArgumentClosureToggle<LogPart> updateModelWithSelection;


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
                model.provideThreadChoicesTo(new ArgumentClosure<List<LogPart>>() {
                    @Override
                    public void excecute(List<LogPart> loggedThreads) {
                        view.displayAvailableThreads(loggedThreads);
                    }
                }, LogEntryParser.LoggedThreadIdentification);
                updateModelWithSelection.activate();
            }
        });

        view.onSelectedThreadChanges(updateModelWithSelection);
        view.onDiscardFilter(new DiscardFilterInModel(model));
    }


    private static class UpdateModelWithSelection implements ArgumentClosure<LogPart> {
        private final LogPartFilterModel model;

        public UpdateModelWithSelection(LogPartFilterModel model) {
            this.model = model;
        }

        @Override
        public void excecute(LogPart loggedThread) {
                model.matches(loggedThread);

        }
    }

    private static class DiscardFilterInModel implements ArgumentClosure<LogPart> {
        private final LogPartFilterModel model;

        public DiscardFilterInModel(LogPartFilterModel model) {
            this.model = model;
        }

        @Override
        public void excecute(LogPart loggedThread) {
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
            model.provideSelectedThreadsTo(new ArgumentClosure<List<LogPart>>() {
                @Override
                public void excecute(List<LogPart> loggedThread) {
                    view.displaySelectedFilter(loggedThread);
                }
            });
        }
    }
}
