package com.github.signed.log.list;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.thread.LoggedThread;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;
import lang.ArgumentClosure;

import java.util.List;

public class LogPresenter {

    private final LogModel logModel;
    private final LogView logView;
    private Optional<LoggedThread> threadToFilterBy = Optional.absent();

    public LogPresenter(LogModel logModel, LogView logView) {
        this.logModel = logModel;
        this.logView = logView;
    }

    public void initialize() {
        logModel.onChange(new Runnable() {
            @Override
            public void run() {
                updateLogEntriesInView();
                logModel.provideThreadChoicesTo(new ArgumentClosure<List<LoggedThread>>() {
                    @Override
                    public void excecute(List<LoggedThread> loggedThreads) {
                        logView.filter().displayAvailableThreads(loggedThreads);
                    }
                });
            }
        });

        logView.filter().onSelectedThreadChanges(new ArgumentClosure<LoggedThread>() {
            @Override
            public void excecute(LoggedThread loggedThread) {
                threadToFilterBy = Optional.fromNullable(loggedThread);
                updateLogEntriesInView();
            }
        });
    }

    private void updateLogEntriesInView() {
        logModel.provideElementsTo(new ArgumentClosure<List<LogEntry>>() {
            @Override
            public void excecute(List<LogEntry> logEntries) {
                if (threadToFilterBy.isPresent()) {
                    List<LogEntry> transformed = Lists.transform(logEntries, new Function<LogEntry, LogEntry>() {
                        @Override
                        public LogEntry apply(@Nullable LogEntry input) {
                            if(input.thread().equals(threadToFilterBy.get())) {
                                return input;
                            }
                            return LogEntry.Null;
                        }
                    });
                    logView.display(transformed);
                } else {
                    logView.display(logEntries);
                }
            }
        });
    }

    public void scrollTo(Integer index) {
        logView.scrollTo(index);
    }
}
