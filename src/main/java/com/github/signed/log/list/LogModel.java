package com.github.signed.log.list;

import com.github.signed.log.core.LogEntry;
import lang.ArgumentClosure;

import java.util.List;

public interface LogModel {
    void onChange(Runnable runnable);

    void provideElementsTo(ArgumentClosure<List<LogEntry>> argumentClosure);
}
