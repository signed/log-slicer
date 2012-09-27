package com.github.signed.log.filter;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.list.LogModel;
import com.github.signed.log.thread.LoggedThread;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;
import lang.Announcer;
import lang.ArgumentClosure;

import java.util.List;

public class LogPartFilterModel implements LogModel {

    private final Announcer<Runnable> availableThreadChangeListener = new Announcer<>(Runnable.class);
    private final Announcer<Runnable> changeListener = new Announcer<>(Runnable.class);
    private final LogModel logModel;
    private Optional<LoggedThread> threadToFilterBy = Optional.absent();

    public LogPartFilterModel(LogModel logModel) {
        this.logModel = logModel;
        logModel.onChange(new Runnable() {
            @Override
            public void run() {
                availableThreadChangeListener.announce().run();
                announceChange();
            }
        });
    }

    @Override
    public void onChange(Runnable runnable) {
        changeListener.addListener(runnable);
    }

    public void matches(LoggedThread loggedThread) {
        threadToFilterBy = Optional.fromNullable(loggedThread);
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

    private void filterAndForwardTo(List<LogEntry> logEntries, ArgumentClosure<List<LogEntry>> argumentClosure) {
        if (threadToFilterBy.isPresent()) {
            List<LogEntry> transformed = Lists.transform(logEntries, new Function<LogEntry, LogEntry>() {
                @Override
                public LogEntry apply(@Nullable LogEntry input) {
                    if (input.thread().equals(threadToFilterBy.get())) {
                        return input;
                    }
                    return LogEntry.Null;
                }
            });
            argumentClosure.excecute(transformed);
        } else {
            argumentClosure.excecute(logEntries);
        }
    }

    public void provideSelectedThreadTo(ArgumentClosure<LoggedThread> argumentClosure) {
        if(threadToFilterBy.isPresent()){
            argumentClosure.excecute(threadToFilterBy.get());
        }
    }

    public void onAvailableThreadsChanges(Runnable runnable) {
        this.availableThreadChangeListener.addListener(runnable);
    }
}
