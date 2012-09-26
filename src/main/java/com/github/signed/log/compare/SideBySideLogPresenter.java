package com.github.signed.log.compare;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.list.LogModel;
import com.github.signed.log.list.LogPresenter;
import com.github.signed.log.list.Presenter;
import lang.ArgumentClosure;

import java.util.List;

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
        leftLogPresenter.initialize();
        rightLogPresenter.initialize();

        logModel.onChange(new Runnable() {
            @Override
            public void run() {
                logModel.provideElementsTo(new ArgumentClosure<List<LogEntry>>() {
                    @Override
                    public void excecute(List<LogEntry> logEntries) {
                        logView.adjustToDisplay(logEntries.size());
                    }
                });

            }
        });

        logView.onScroll(new ArgumentClosure<Integer>() {
            @Override
            public void excecute(Integer index) {
                leftLogPresenter.scrollTo(index);
                rightLogPresenter.scrollTo(index);
            }
        });
    }
}
