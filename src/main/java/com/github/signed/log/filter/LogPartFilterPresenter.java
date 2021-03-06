package com.github.signed.log.filter;

import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.ui.Presenter;

import lang.ArgumentClosure;
import lang.ArgumentClosureToggle;

public class LogPartFilterPresenter implements Presenter {
    private final LogPartFilterView view;
    private final LogPartFilterModel model;
    private final Identification identification;
    private final ArgumentClosureToggle<LogPart> updateModelWithSelection;


    public LogPartFilterPresenter(LogPartFilterView view, LogPartFilterModel model, Identification identification) {
        this.view = view;
        this.model = model;
        this.identification = identification;
        updateModelWithSelection = ArgumentClosureToggle.toggleAround(new UpdateModelWithSelection(this.model));
    }

    @Override
    public void initialize() {
        model.onLogEntryChange(new PassSelectedFiltersToView(model, view));
        PopulateViewFromModel populateViewFromModel = new PopulateViewFromModel();
        model.onAvailableThreadsChanges(populateViewFromModel);
        view.onSelection(updateModelWithSelection);
        view.onDiscardFilter(new DiscardFilterInModel(model));
        populateViewFromModel.run();
    }


    private class UpdateModelWithSelection implements ArgumentClosure<LogPart> {
        private final LogPartFilterModel model;

        public UpdateModelWithSelection(LogPartFilterModel model) {
            this.model = model;
        }

        @Override
        public void execute(LogPart loggedThread) {
                model.matches(identification, loggedThread);

        }
    }

    private class DiscardFilterInModel implements ArgumentClosure<LogPart> {
        private final LogPartFilterModel model;

        public DiscardFilterInModel(LogPartFilterModel model) {
            this.model = model;
        }

        @Override
        public void execute(LogPart loggedThread) {
            model.discardFilter(identification, loggedThread);
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
            model.provideSelectedThreadsTo(identification, view::displaySelectedFilter);
        }
    }

    private class PopulateViewFromModel implements Runnable {
        @Override
        public void run() {
            updateModelWithSelection.suspend();
            model.provideRemainingChoicesTo(identification, view::displayAvailableLogParts);
            updateModelWithSelection.activate();
        }
    }
}
