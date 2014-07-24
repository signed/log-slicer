package com.github.signed.log.list;

import com.github.signed.log.core.ui.Presenter;

public class LogPresenter implements Presenter {

    private final LogView logView;
    private final LogModel logModel;

    public LogPresenter(LogModel logModel, LogView logView) {
        this.logModel = logModel;
        this.logView = logView;
    }

    @Override
    public void initialize() {
        logModel.onDescriptorChange(() -> {
            logView.clearDisplayedLogParts();
            logModel.describeTo(descriptor -> {
                if (descriptor.display) {
                    logView.showLogPart(descriptor.name, descriptor.identification);
                }
            });
        });

        logModel.onLogEntryChange(() -> logModel.provideElementsTo(logEntries -> logView.display(logEntries)));
    }

    public void scrollTo(Integer index) {
        logView.scrollTo(index);
    }
}