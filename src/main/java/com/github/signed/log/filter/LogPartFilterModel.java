package com.github.signed.log.filter;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.list.LogModel;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sun.istack.internal.Nullable;
import lang.Announcer;
import lang.ArgumentClosure;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LogPartFilterModel implements LogModel {

    private final Announcer<Runnable> availableThreadChangeListener = new Announcer<>(Runnable.class);
    private final Announcer<Runnable> changeListener = new Announcer<>(Runnable.class);
    private final LogModel logModel;
    private final Map<Identification, Set<LogPart>> whiteListedParts = Maps.newHashMap();

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

    public void matches(Identification identification, LogPart logPart) {
        Set<LogPart> logParts = getSetOfWhiteListedParts(identification);
        if(null == logParts) {
            logParts = Sets.newLinkedHashSet();
            whiteListedParts.put(identification, logParts);
        }
        logParts.add(logPart);
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
            public void execute(List<LogEntry> logEntries) {
                filterAndForwardTo(logEntries, argumentClosure);
            }
        });
    }

    @Override
    public void provideRemainingChoicesTo(final Identification identification, final ArgumentClosure<List<LogPart>> argumentClosure) {
        ArgumentClosure<List<LogPart>> filterClosure=  new ArgumentClosure<List<LogPart>>() {
                @Override
                public void execute(List<LogPart> logParts) {
                    Collection<LogPart> filtered = Collections2.filter(logParts, new Predicate<LogPart>() {
                        @Override
                        public boolean apply(@Nullable LogPart input) {
                            return !getSetOfWhiteListedPartsOrDefaultToEmpty(identification).contains(input);
                        }
                    });
                    argumentClosure.execute(ImmutableList.copyOf(filtered));
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

        for (Map.Entry<Identification, Set<LogPart>> entry : whiteListedParts.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                forward = filterBy(entry.getKey(), forward);
            }
        }

        argumentClosure.execute(ImmutableList.copyOf(forward));
    }

    private List<LogEntry> filterBy(final Identification identification, List<LogEntry> logEntries) {
        return  Lists.transform(logEntries, new Function<LogEntry, LogEntry>() {
            @Override
            public LogEntry apply(@Nullable LogEntry logEntry) {
                if ( whiteListedParts.get(identification).contains(logEntry.getPart(identification))) {
                    return logEntry;
                }
                return LogEntry.Null;
            }
        });
    }

    public void provideSelectedThreadsTo(Identification identification, ArgumentClosure<List<LogPart>> argumentClosure) {
        argumentClosure.execute(ImmutableList.copyOf(getSetOfWhiteListedPartsOrDefaultToEmpty(identification)));
    }

    private Set<LogPart> getSetOfWhiteListedPartsOrDefaultToEmpty(Identification identification) {
        Set<LogPart> defaultValue = Collections.emptySet();
        return Functions.forMap(whiteListedParts, defaultValue).apply(identification);
    }

    public void onAvailableThreadsChanges(Runnable runnable) {
        this.availableThreadChangeListener.addListener(runnable);
    }

    public void discardFilter(Identification identification, LogPart logPart) {
        getSetOfWhiteListedParts(identification).remove(logPart);
        announceChange();
        announceThreadSelectionChanged();
    }



    private Set<LogPart> getSetOfWhiteListedParts(Identification identification) {
        return this.whiteListedParts.get(identification);
    }
}
