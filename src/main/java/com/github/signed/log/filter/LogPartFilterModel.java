package com.github.signed.log.filter;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.list.LogModel;
import com.github.signed.log.thread.LoggedThread;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sun.istack.internal.Nullable;
import lang.Announcer;
import lang.ArgumentClosure;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class LogPartFilterModel implements LogModel {

    private final Announcer<Runnable> availableThreadChangeListener = new Announcer<>(Runnable.class);
    private final Announcer<Runnable> changeListener = new Announcer<>(Runnable.class);
    private final LogModel logModel;
    private final Set<LoggedThread> loggedThreadsToDisplay = Sets.newLinkedHashSet();

    public LogPartFilterModel(LogModel logModel) {
        this.logModel = logModel;
        logModel.onChange(new Runnable() {
            @Override
            public void run() {
                announceThreadSelectionChanged();
                announceChange();
            }
        });
    }

    private void announceThreadSelectionChanged() {
        availableThreadChangeListener.announce().run();
    }

    @Override
    public void onChange(Runnable runnable) {
        changeListener.addListener(runnable);
    }

    public void matches(LoggedThread loggedThread) {
        loggedThreadsToDisplay.add(loggedThread);
        announceThreadSelectionChanged();
        announceChange();
    }

    private void announceChange() {
        changeListener.announce().run();
    }

    @Override
    public void provideElementsTo(final ArgumentClosure<List<LogEntry>> argumentClosure) {
        logModel.provideElementsTo(new ArgumentClosure<List<LogEntry>>() {
            @Override
            public void excecute(List<LogEntry> logEntries) {
                filterAndForwardTo(logEntries, argumentClosure);
            }
        });
    }

    @Override
    public void provideThreadChoicesTo(final ArgumentClosure<List<LoggedThread>> argumentClosure) {
        ArgumentClosure<List<LoggedThread>> filterClosure=  new ArgumentClosure<List<LoggedThread>>() {
                @Override
                public void excecute(List<LoggedThread> loggedThreads) {
                    Collection<LoggedThread> filtered = Collections2.filter(loggedThreads, new Predicate<LoggedThread>() {
                        @Override
                        public boolean apply(@Nullable LoggedThread input) {
                            return !loggedThreadsToDisplay.contains(input);
                        }
                    });
                    argumentClosure.excecute(ImmutableList.copyOf(filtered));
                }
            };
        logModel.provideThreadChoicesTo(filterClosure);
    }

    @Override
    public void describeTo(Authority authority) {
        logModel.describeTo(authority);
    }

    private void filterAndForwardTo(List<LogEntry> logEntries, ArgumentClosure<List<LogEntry>> argumentClosure) {
        List<LogEntry> forward = logEntries;
        if (!this.loggedThreadsToDisplay.isEmpty()) {
            forward = filterBySelectedThreads(logEntries);
        }
        argumentClosure.excecute(ImmutableList.copyOf(forward));
    }

    private List<LogEntry> filterBySelectedThreads(List<LogEntry> logEntries) {
        return  Lists.transform(logEntries, new Function<LogEntry, LogEntry>() {
            @Override
            public LogEntry apply(@Nullable LogEntry input) {
                if ( loggedThreadsToDisplay.contains(input.getDerivedPart(LoggedThread.class))) {
                    return input;
                }
                return LogEntry.Null;
            }
        });
    }

    public void provideSelectedThreadsTo(ArgumentClosure<List<LoggedThread>> argumentClosure) {
        argumentClosure.excecute(ImmutableList.copyOf(loggedThreadsToDisplay));
    }

    public void onAvailableThreadsChanges(Runnable runnable) {
        this.availableThreadChangeListener.addListener(runnable);
    }

    public void discardFilter(LoggedThread loggedThread) {
        this.loggedThreadsToDisplay.remove(loggedThread);
        announceChange();
        announceThreadSelectionChanged();
    }
}
