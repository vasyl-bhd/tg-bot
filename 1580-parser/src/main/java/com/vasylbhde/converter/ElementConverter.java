package com.vasylbhde.converter;

import com.vasylbhde.model.Action;
import com.vasylbhde.model.Street;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;
import static com.vasylbhde.model.Constants.*;

public final class ElementConverter {
    private ElementConverter(){}
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uu kk:mm");
    private static final DateTimeFormatter modDateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu kk:mm");

    public static Action toAction(Element element) {
        return new Action(
                element.attr(ID_ATTRIBUTE),
                toLocalDateTime(getStringTime(element.select(START_DATE_CLASS).text())),
                toLocalDateTime(getStringTime(element.select(ESTIMATED_END_DATE_CLASS).text())),
                toLocalDateTime(getStringTime(element.select(END_DATE_CLASS).text())),
                getStreets(element.select(STREETS_CLASS)),
                toLocalDateTime(getModificationDateTime(element.select(MODIFICATION_DATE_CLASS).text()), modDateTimeFormatter),
                element.select(REASON_CLASS).text());
    }

    public static List<Street> getStreets(Elements element) {
        return Arrays.stream(element.html().replace(BR, "").split(System.lineSeparator()))
                .takeWhile(s -> !s.startsWith(DIV_PREFIX))
                .filter(not(String::isBlank))
                .map(s -> s.split(getSplitter(s)))
                .map(ElementConverter::getStreetFromLines)
                .collect(Collectors.toList());
    }

    private static LocalDateTime toLocalDateTime(String time, DateTimeFormatter formatter) {
       return time.isBlank() ? null : LocalDateTime.parse(time, formatter);
    }

    private static LocalDateTime toLocalDateTime(String time) {
        return toLocalDateTime(time, dateTimeFormatter);
    }

    private static String getStringTime(String s) {
        return s.substring(s.indexOf(' ') + 1);
    }

    public static Street getStreetFromLines(String[] lines) {
        return new Street(lines[0], List.of(lines[1].replace(HOUSE_SUFFIX, EMPTY_STRING).split(DOUBLE_NBSP_NBSP)));
    }

    public static String getSplitter(String s) {
        return s.contains(HOUSE_PREFIX) ? HOUSE_PREFIX : DASH;
    }

    public static String getModificationDateTime(String div) {
        return div.substring(div.indexOf(MODIFICATION_DATE) + MODIFICATION_DATE.length());
    }


}
