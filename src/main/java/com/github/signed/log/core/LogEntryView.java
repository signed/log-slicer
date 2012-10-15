package com.github.signed.log.core;

import com.github.signed.log.core.ui.Presenter;
import javafx.ViewOrphanage;

import java.util.Collection;

public interface LogEntryView {
    void contributeTo(Collection<Presenter> presenters);

    void addTo(ViewOrphanage orphanage);
}
