package com.vasylbhd.resolver.element;

import org.jsoup.nodes.Element;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import static com.vasylbhd.model.Constants.EMPTY_STRING;

public abstract class AbstractDateResolver implements ElementResolver<LocalDateTime> {

    protected static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uu kk:mm");
    private static final Pattern DATE_PATTERN = Pattern.compile("[А-Яа-я]+:?\\s(\\d{2}.\\d{2}.\\d{2}\\s\\d{2}:\\d{2}).*");

    @Override
    public LocalDateTime resolve(Element element) {
        return toLocalDateTime(getStringTime(element.select(getSelector()).text()));
    }

    protected abstract String getSelector();

    protected LocalDateTime toLocalDateTime(String time) {
        return toLocalDateTime(time, dateTimeFormatter);
    }

    protected LocalDateTime toLocalDateTime(String time, DateTimeFormatter formatter) {
        return time.isBlank() ? null : LocalDateTime.parse(time, formatter);
    }

    private String getStringTime(String s) {
        var matcher = DATE_PATTERN.matcher(s);
        var found = matcher.find(0);

        return found ? matcher.group(1) : EMPTY_STRING;
    }


}
