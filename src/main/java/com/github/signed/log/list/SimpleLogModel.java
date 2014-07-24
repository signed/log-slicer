package com.github.signed.log.list;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lang.Announcer;
import lang.ArgumentClosure;

public class SimpleLogModel implements LogModel {
    private final Announcer<Runnable> changeListener = new Announcer<>(Runnable.class);
    private final Announcer<Runnable> descriptorChangeListener = new Announcer<>(Runnable.class);
    private final List<LogEntry> logEntries = Lists.newArrayList();

    public void addEntriesFrom(Iterable<LogEntry> logEntries) {
        Iterables.addAll(this.logEntries, logEntries);
        announceChange();
    }

    private void announceChange() {
        descriptorChangeListener.announce().run();
        changeListener.announce().run();
    }

    @Override
    public void onLogEntryChange(Runnable runnable) {
        changeListener.addListener(runnable);
    }

    @Override
    public void onDescriptorChange(Runnable runnable) {
        descriptorChangeListener.addListener(runnable);
    }

    @Override
    public void provideElementsTo(ArgumentClosure<List<LogEntry>> argumentClosure) {
       argumentClosure.execute(Lists.newArrayList(logEntries));
    }

    @Override
    public void provideRemainingChoicesTo(final Identification identification, ArgumentClosure<List<LogPart>> closure) {
        List<LogPart> transform = Lists.transform(logEntries, input -> input.getPart(identification));
        Set<LogPart> unique = Sets.newHashSet(transform);
        List<LogPart> list = Lists.newArrayList(unique);
        Collections.sort(list);
        closure.execute(list);
    }

    @Override
    public void describeTo(final Authority authority) {
        final Authority uniqueDescriptors = new DuplicateFilteringAuthority(authority);
        for (LogEntry logEntry : logEntries) {
            logEntry.describeTo(uniqueDescriptors);
        }
    }

    private static class DuplicateFilteringAuthority implements Authority {
        final Set<Descriptor> descriptors;
        private final Authority authority;

        public DuplicateFilteringAuthority(Authority authority) {
            this.authority = authority;
            descriptors = Sets.newHashSet();
        }

        @Override
        public void accept(Descriptor descriptor) {
            if (!descriptors.contains(descriptor)) {
                descriptors.add(descriptor);
                authority.accept(descriptor);
            }
        }
    }
}
