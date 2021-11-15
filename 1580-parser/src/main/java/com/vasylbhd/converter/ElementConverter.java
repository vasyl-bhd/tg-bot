package com.vasylbhd.converter;

import com.vasylbhd.model.Action;
import com.vasylbhd.model.Street;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.vasylbhd.model.Constants.*;
import static java.util.Collections.emptyList;
import static java.util.function.Predicate.not;

public final class ElementConverter {
    private ElementConverter(){}
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uu kk:mm");
    private static final DateTimeFormatter modDateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu kk:mm");
    private static final Pattern DATE_PATTERN = Pattern.compile("[А-Яа-я]+:?\\s(\\d{2}.\\d{2}.\\d{2}\\s\\d{2}:\\d{2}).*");

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
        return element.textNodes().stream()
                .map(TextNode::text)
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
        var matcher = DATE_PATTERN.matcher(s);
        var found = matcher.find(0);
        return found ? matcher.group(1) : EMPTY_STRING;
    }

    public static Street getStreetFromLines(String[] lines) {
        List<String> streetNumbers = lines.length < 2 ? emptyList() : List.of(lines[1].replace(HOUSE_SUFFIX, EMPTY_STRING).split(DOUBLE_NBSP_NBSP));

        return new Street(lines[0], streetNumbers);
    }

    public static String getSplitter(String s) {
        return s.contains(HOUSE_PREFIX) ? HOUSE_PREFIX : DASH;
    }

    public static String getModificationDateTime(String div) {
        return div.isEmpty() ? div : div.substring(div.indexOf(MODIFICATION_DATE) + MODIFICATION_DATE.length());
    }


}
