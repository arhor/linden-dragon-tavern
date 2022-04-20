package com.github.arhor.linden.dragon.tavern.infrastructure.startup;

import org.springframework.core.Ordered;

@FunctionalInterface
public interface StartupTask extends Ordered, Comparable<StartupTask> {

    int MEDIUM_PRECEDENCE = 0;

    void execute();

    @Override
    default int getOrder() {
        return MEDIUM_PRECEDENCE;
    }

    @Override
    default int compareTo(final StartupTask other) {
        return this.getOrder() - other.getOrder();
    }
}
