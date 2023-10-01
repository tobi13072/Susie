package io.github.lizewskik.susieserver.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtils {

    public static ZonedDateTime reduceTimeFromZonedDateTime(ZonedDateTime dateTime) {
        return dateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault());
    }
}
