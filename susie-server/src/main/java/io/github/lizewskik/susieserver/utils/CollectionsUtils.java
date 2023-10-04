package io.github.lizewskik.susieserver.utils;

import java.util.Collection;

import static java.util.Objects.isNull;

public class CollectionsUtils {

    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return isNull(collection) || collection.isEmpty();
    }
}
