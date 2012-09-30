package com.github.signed.log;

import com.github.signed.log.core.ui.Presenter;
import com.github.signed.log.filter.LogPartFilterModel;
import com.github.signed.log.filter.LogPartFilterPresenter;
import com.github.signed.log.filter.LogPartFilterView;
import com.github.signed.log.filteredlisting.LogEntryListingWithFilterInputView;
import com.github.signed.log.list.LogModel;
import com.github.signed.log.list.LogPresenter;
import com.github.signed.log.list.LogView;
import com.google.common.collect.ImmutableList;
import javafx.OrphanView;

import java.util.List;

public class LogPanel {

    public static LogPanel PositionFilterLeft(LogModel model){
        return new LogPanel(model, new SideStrategy() {
            @Override
            public LogEntryListingWithFilterInputView create(OrphanView orphanView, LogView logView) {
                return LogEntryListingWithFilterInputView.putFilterLeftOfLogs(orphanView, logView);
            }
        });
    }

    public static LogPanel PositionFilterRight(LogModel model){
        return new LogPanel(model, new SideStrategy() {
            @Override
            public LogEntryListingWithFilterInputView create(OrphanView orphanView, LogView logView) {
                return LogEntryListingWithFilterInputView.putFilterRightOfLogs(orphanView, logView);
            }
        });
    }

    private static interface SideStrategy{
        LogEntryListingWithFilterInputView create(OrphanView orphanView, LogView logView);
    }

    public final LogPartFilterModel logPartFilterModel;
    public final LogPartFilterView logPartFilterView;
    public final LogPartFilterPresenter logPartFilterPresenter;

    public final LogView logView = new LogView();
    public final LogPresenter logPresenter;

    public final LogEntryListingWithFilterInputView completeView;

    public final List<Presenter> allPresenters;

    public LogPanel(LogModel logModel, SideStrategy sideStrategy) {
        logPartFilterModel = new LogPartFilterModel(logModel);
        logPartFilterView = new LogPartFilterView();
        logPartFilterPresenter = new LogPartFilterPresenter(logPartFilterView, logPartFilterModel);
        logPresenter = new LogPresenter(logPartFilterModel, logView);
        completeView = sideStrategy.create(logPartFilterView, logView);

        allPresenters = ImmutableList.of(logPartFilterPresenter, logPresenter);
    }
}