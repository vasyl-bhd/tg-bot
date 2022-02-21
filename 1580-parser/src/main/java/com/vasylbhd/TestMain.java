package com.vasylbhd;

import com.vasylbhd.converter.ElementConverter;
import com.vasylbhd.model.Action;
import com.vasylbhd.parser.LvivHotlineIssuesParserImpl;
import com.vasylbhd.resolver.element.*;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.vasylbhd.model.Constants.*;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.YEARS;
import static java.util.stream.Collectors.joining;

public class TestMain {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");

    @SneakyThrows
    public static void main(String[] args) {
        List<ElementResolver> elementResolvers = List.of(new EndDateElementResolver(),
                new EstimatedDateElementResolver(),
                new IdElementResolver(),
                new ModificationDateElementResolver(),
                new ReasonElementResolver(),
                new StartDateElementResolver(),
                new StreetsElementResolver()
        );
        var elementConverter = new ElementConverter(elementResolvers);
        var parse = new LvivHotlineIssuesParserImpl(elementConverter).parse(LocalDate.now().minus(1, YEARS), LocalDate.now().plus(2, DAYS));
        var collect = parse.stream().map(Record::toString).collect(joining("\n"));
        System.out.println(collect);
    }

    private static String getRequestData(LocalDate from, LocalDate to) {
        return String.format(
                com.vasylbhd.model.Constants.REQUEST_FILTER_STRING_FORMAT,
                from.format(dateTimeFormatter),
                to.format(dateTimeFormatter),
                GROOVE_STREET_SWEET_HOME);
    }
}
