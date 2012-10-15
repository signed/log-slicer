package com.github.signed.log.sidebyside;

import com.github.signed.log.LogPanel;
import com.github.signed.log.compare.SideBySideLogPresenter;
import com.github.signed.log.compare.SideBySideLogView;
import com.github.signed.log.core.LogEntryView;
import com.github.signed.log.core.ui.Presenter;
import com.github.signed.log.list.LogModel;
import javafx.ViewOrphanage;

import java.util.Collection;

public class SideBySide implements LogEntryView {
    private final SideBySideLogPresenter sideBySideLogPresenter;
    private final LogPanel left;
    private final LogPanel right;
    private final SideBySideLogView sideBySideLogView;

    public SideBySide(LogModel logModel){
        left = LogPanel.PositionFilterLeft(logModel);
        right = LogPanel.PositionFilterRight(logModel);
        sideBySideLogView = new SideBySideLogView(left.completeView, right.completeView);
        sideBySideLogPresenter = new SideBySideLogPresenter(logModel, sideBySideLogView, left.logPresenter, right.logPresenter);
    }


    @Override
    public void contributeTo(Collection<Presenter> presenters) {
        presenters.addAll(left.allPresenters);
        presenters.addAll(right.allPresenters);
        presenters.add(sideBySideLogPresenter);
    }

    @Override
    public void addTo(ViewOrphanage orphanage) {
        sideBySideLogView.addTo(orphanage);
    }
}
