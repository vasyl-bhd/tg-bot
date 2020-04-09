package converter;

import model.Action;
import model.Street;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Ютиліти;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;
import static model.Constants.*;
import static model.Constants.REASON_CLASS;
import static utils.Ютиліти.getModificationDate;
import static utils.Ютиліти.getSplitter;

public final class ElementConverter {
    private ElementConverter(){}
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu kk:mm");

    public static Action toAction(Element element) {
        return new Action(
                toLocalDateTime(element.select(START_DATE_CLASS).text()),
                toLocalDateTime(element.select(ESTIMATED_END_DATE_CLASS).text()),
                toLocalDateTime(element.select(END_DATE_CLASS).text()),
                getStreets(element.select(STREETS_CLASS)),
                toLocalDateTime(getModificationDate(element.select(MODIFICATION_DATE_CLASS).text())),
                element.select(REASON_CLASS).text());
    }

    public static List<Street> getStreets(Elements element) {
        return Arrays.stream(element.html().replace(BR, "").split(System.lineSeparator()))
                .takeWhile(s -> !s.startsWith(DIV_PREFIX))
                .filter(not(String::isBlank))
                .map(s -> s.split(getSplitter(s)))
                .map(Ютиліти::getStreetFromLines)
                .collect(Collectors.toList());
    }

    private static LocalDateTime toLocalDateTime(String time) {
       return time.isBlank() ? null : LocalDateTime.parse(time, dateTimeFormatter);
    }


}
