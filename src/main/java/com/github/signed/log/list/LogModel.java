package com.github.signed.log.list;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.thread.LoggedThread;
import lang.ArgumentClosure;

import java.util.List;

public interface LogModel {
    void onChange(Runnable runnable);

    void provideElementsTo(ArgumentClosure<List<LogEntry>> argumentClosure);

    void provideThreadChoicesTo(ArgumentClosure<List<LoggedThread>> argumentClosure);

    void describeTo(Authority authority);
}
