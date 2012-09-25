package com.github.signed.log;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public class RawLog implements Iterable<LogEntry> {
    private final String raw;

    public RawLog(String complete) {
        raw = complete;
    }

    @Override
    public Iterator<LogEntry> iterator() {
        Iterable<String> split = Splitter.on("\n").omitEmptyStrings().split(raw);

        List<LogEntry> logEntries = Lists.newArrayList();
        List<List<String>> allRawEntries = Lists.newArrayList();
        List<String> linesInCurrentLogEntry = Lists.newArrayList();

        for (String currentLine : split) {
            if(!currentLine.startsWith("2")){
                linesInCurrentLogEntry.add(currentLine);
            }else {
                linesInCurrentLogEntry = Lists.newArrayList();
                allRawEntries.add(linesInCurrentLogEntry);
                linesInCurrentLogEntry.add(currentLine);
            }
        }

        for (List<String> allRawEntry : allRawEntries) {
            LogEntry e = LogEntry.createLogEntry(Joiner.on("\n").join(allRawEntry));
            logEntries.add(e);
        }

        return logEntries.iterator();
    }
}