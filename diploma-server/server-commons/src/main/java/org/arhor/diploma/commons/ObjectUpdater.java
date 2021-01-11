package org.arhor.diploma.commons;

public interface ObjectUpdater {

    <T, R> void update(T target, R source);
}
