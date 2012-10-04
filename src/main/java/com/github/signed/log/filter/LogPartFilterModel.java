package com.github.signed.log.filter;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.parser.LogEntryParser;
import com.github.signed.log.list.LogModel;
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
    private final Set<LogPart> loggedThreadsToDisplay = Sets.newLinkedHashSet();

    public LogPartFilterModel(LogModel logModel) {
        this.logModel = logModel;
        logModel.onLogEntryChange(new Runnable() {
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
    public void onLogEntryChange(Runnable runnable) {
        changeListener.addListener(runnable);
    }

    @Override
    public void onDescriptorChange(Runnable runnable) {
        logModel.onDescriptorChange(runnable);
    }

    public void matches(LogPart loggedThread) {
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
    public void provideRemainingChoicesTo(Identification identification, final ArgumentClosure<List<LogPart>> argumentClosure) {
        ArgumentClosure<List<LogPart>> filterClosure=  new ArgumentClosure<List<LogPart>>() {
                @Override
                public void excecute(List<LogPart> logParts) {
                    Collection<LogPart> filtered = Collections2.filter(logParts, new Predicate<LogPart>() {
                        @Override
                        public boolean apply(@Nullable LogPart input) {
                            return !loggedThreadsToDisplay.contains(input);
                        }
                    });
                    argumentClosure.excecute(ImmutableList.copyOf(filtered));
                }
            };
        logModel.provideRemainingChoicesTo(identification, filterClosure);
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
                if ( loggedThreadsToDisplay.contains(input.getPart(LogEntryParser.LoggedThreadIdentification))) {
                    return input;
                }
                return LogEntry.Null;
            }
        });
    }

    public void provideSelectedThreadsTo(ArgumentClosure<List<LogPart>> argumentClosure) {
        argumentClosure.excecute(ImmutableList.copyOf(loggedThreadsToDisplay));
    }

    public void onAvailableThreadsChanges(Runnable runnable) {
        this.availableThreadChangeListener.addListener(runnable);
    }

    public void discardFilter(LogPart loggedThread) {
        this.loggedThreadsToDisplay.remove(loggedThread);
        announceChange();
        announceThreadSelectionChanged();
    }
}
