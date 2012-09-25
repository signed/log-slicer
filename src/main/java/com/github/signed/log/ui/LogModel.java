package com.github.signed.log.ui;

import com.github.signed.log.LogEntry;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lang.Announcer;
import lang.ArgumentClosure;

import java.util.List;

public class LogModel {
    private final Announcer<Runnable> changeListener = new Announcer<>(Runnable.class);
    private final List<LogEntry> logEntries = Lists.newArrayList();

    public void takeEntriesFrom(Iterable<LogEntry> logEntries) {
        Iterables.addAll(this.logEntries, logEntries);
        announceChange();
    }

    private void announceChange() {
        changeListener.announce().run();
    }

    public void onChange(Runnable runnable) {
        changeListener.addListener(runnable);
    }

    public void provideElementsTo(ArgumentClosure<List<LogEntry>> argumentClosure) {
       argumentClosure.excecute(Lists.newArrayList(logEntries));
    }
}
