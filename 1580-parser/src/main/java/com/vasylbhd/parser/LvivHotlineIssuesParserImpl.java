package com.vasylbhd.parser;

import com.vasylbhd.converter.ElementConverter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import com.vasylbhd.model.Action;
import org.jsoup.Jsoup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.vasylbhd.model.Constants.*;

public record LvivHotlineIssuesParserImpl(ElementConverter elementConverter) implements LvivHotlineIssuesParser {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");

    @Override
    @SneakyThrows
    public List<Action> parse(LocalDate from, LocalDate to) {
        return Jsoup.connect(LVIV_HOTLINE_URL)
                .timeout(90_000)
                .data("data", getRequestData(from, to))
                .data("rks", "1")
                .data("rn", "0")
                .data("all", "1")
                .data("isFrame", "")
                .data("hr", "1")
                .data("frameJeoId", "0")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
                .post()
                .select(ROW_CLASS)
                .stream()
                .map(elementConverter::toAction)
                .collect(Collectors.toList());
    }

    private String getRequestData(LocalDate from, LocalDate to) {
        return String.format(
                com.vasylbhd.model.Constants.REQUEST_FILTER_STRING_FORMAT,
                from.format(dateTimeFormatter),
                to.format(dateTimeFormatter),
                GROOVE_STREET_SWEET_HOME);
    }
}
