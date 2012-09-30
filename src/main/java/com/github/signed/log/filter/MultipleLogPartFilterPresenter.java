package com.github.signed.log.filter;

import com.github.signed.log.core.ui.Presenter;

public class MultipleLogPartFilterPresenter implements Presenter{

    private final LogPartFilterModel filterModel;
    private final MultipleLogPartFilterView view;

    public MultipleLogPartFilterPresenter(LogPartFilterModel logPartFilterModel, MultipleLogPartFilterView multipleLogPartFilterView) {
        this.filterModel = logPartFilterModel;
        this.view = multipleLogPartFilterView;
    }


    @Override
    public void initialize() {
        filterModel.onAvailableThreadsChanges(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
