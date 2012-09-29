package com.github.signed.log.list;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import lang.ArgumentClosure;

import java.util.List;

public interface LogModel {
    void onLogEntryChange(Runnable runnable);

    void onDescriptorChange(Runnable runnable);

    void provideElementsTo(ArgumentClosure<List<LogEntry>> argumentClosure);

    void provideThreadChoicesTo(ArgumentClosure<List<LogPart>> argumentClosure, Identification identification);

    void describeTo(Authority authority);
}
