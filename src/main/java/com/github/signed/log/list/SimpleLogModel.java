package com.github.signed.log.list;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.thread.LoggedThread;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lang.Announcer;
import lang.ArgumentClosure;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleLogModel implements LogModel {
    private final Announcer<Runnable> changeListener = new Announcer<>(Runnable.class);
    private final List<LogEntry> logEntries = Lists.newArrayList();

    public void addEntriesFrom(Iterable<LogEntry> logEntries) {
        Iterables.addAll(this.logEntries, logEntries);
        announceChange();
    }

    private void announceChange() {
        changeListener.announce().run();
    }

    @Override
    public void onChange(Runnable runnable) {
        changeListener.addListener(runnable);
    }

    @Override
    public void provideElementsTo(ArgumentClosure<List<LogEntry>> argumentClosure) {
       argumentClosure.excecute(Lists.newArrayList(logEntries));
    }

    public void provideThreadChoicesTo(ArgumentClosure<List<LoggedThread>> closure) {
        List<LoggedThread> transform = Lists.transform(logEntries, new Function<LogEntry, LoggedThread>() {
            @Override
            public LoggedThread apply(LogEntry input) {
                return input.getDerivedPart(LoggedThread.class);
            }
        });

        Set<LoggedThread> unique = Sets.newHashSet(transform);
        List<LoggedThread> list = Lists.newArrayList(unique);
        Collections.sort(list);
        closure.excecute(list);
    }

    @Override
    public void describeTo(Authority authority) {
        HashSet<Descriptor> authority1 = new HashSet<>();
        for (LogEntry logEntry : logEntries) {
            logEntry.describeTo(authority1);
        }
        for (Descriptor descriptor : authority1) {
            authority.accept(descriptor);
        }
    }
}
