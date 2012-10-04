package com.github.signed.log.filter;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.ui.Presenter;

public class MultipleLogPartFilterPresenter implements Presenter {

    private final LogPartFilterModel filterModel;
    private final MultipleLogPartFilterView view;

    public MultipleLogPartFilterPresenter(LogPartFilterModel logPartFilterModel, MultipleLogPartFilterView multipleLogPartFilterView) {
        this.filterModel = logPartFilterModel;
        this.view = multipleLogPartFilterView;
    }


    @Override
    public void initialize() {
        filterModel.onDescriptorChange(new Runnable() {
            @Override
            public void run() {
                view.clearExistingFilterViews();
                filterModel.describeTo(new Authority() {
                    @Override
                    public void accept(Descriptor descriptor) {
                        if (descriptor.filterable) {
                            LogPartFilterView view = MultipleLogPartFilterPresenter.this.view.newPartFilter(descriptor.name);
                            new LogPartFilterPresenter(view, filterModel, descriptor.identification).initialize();
                        }
                    }
                });
            }
        });
    }
}
