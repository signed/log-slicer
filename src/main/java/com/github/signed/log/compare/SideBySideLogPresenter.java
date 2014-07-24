package com.github.signed.log.compare;

import com.github.signed.log.core.ui.Presenter;
import com.github.signed.log.list.LogModel;
import com.github.signed.log.list.LogPresenter;

public class SideBySideLogPresenter implements Presenter {
    private final LogPresenter leftLogPresenter;
    private final LogPresenter rightLogPresenter;
    private final LogModel logModel;
    private final SideBySideLogView logView;

    public SideBySideLogPresenter(LogModel logModel, SideBySideLogView sideBySideLogView, LogPresenter leftLogPresenter, LogPresenter rightLogPresenter) {
        this.logModel = logModel;
        this.logView = sideBySideLogView;
        this.leftLogPresenter = leftLogPresenter;
        this.rightLogPresenter = rightLogPresenter;
    }

    @Override
    public void initialize() {
        logModel.onLogEntryChange(() -> logModel.provideElementsTo(logEntries -> logView.adjustToDisplay(logEntries.size())));

        logView.onScroll(index -> {
            leftLogPresenter.scrollTo(index);
            rightLogPresenter.scrollTo(index);
        });
    }
}
