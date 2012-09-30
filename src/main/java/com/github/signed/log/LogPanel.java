package com.github.signed.log;

import com.github.signed.log.core.ui.Presenter;
import com.github.signed.log.filter.LogPartFilterModel;
import com.github.signed.log.filter.LogPartFilterPresenter;
import com.github.signed.log.filter.LogPartFilterView;
import com.github.signed.log.filteredlisting.LogEntryListingWithFilterInputView;
import com.github.signed.log.list.LogPresenter;
import com.github.signed.log.list.LogView;
import com.github.signed.log.list.SimpleLogModel;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class LogPanel {
    public final LogPartFilterModel logPartFilterModel;
    public final LogPartFilterView logPartFilterView;
    public final LogPartFilterPresenter logPartFilterPresenter;

    public final LogView logView = new LogView();
    public final LogPresenter logPresenter;

    public final LogEntryListingWithFilterInputView completeView;

    public final List<Presenter> allPresenters;

    public LogPanel(SimpleLogModel logModel) {
        logPartFilterModel = new LogPartFilterModel(logModel);
        logPartFilterView = new LogPartFilterView();
        logPartFilterPresenter = new LogPartFilterPresenter(logPartFilterView, logPartFilterModel);
        logPresenter = new LogPresenter(logPartFilterModel, logView);
        completeView = new LogEntryListingWithFilterInputView(this.logPartFilterView, this.logView);

        allPresenters = ImmutableList.of(logPartFilterPresenter, logPresenter);
    }


}
