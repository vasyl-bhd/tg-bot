package com.vasylbhd;

import com.vasylbhd.model.Action;
import com.vasylbhd.parser.LvivHotlineIssuesParserImpl;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.vasylbhd.model.Constants.*;
import static java.util.stream.Collectors.joining;

public class TestMain {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");

    @SneakyThrows
    public static void main(String[] args) {
//        var select = Jsoup.connect(LVIV_HOTLINE_URL)
//                .timeout(90_000)
//                .data("data", getRequestData(LocalDate.now().minus(1, ChronoUnit.DAYS), LocalDate.now()))
//                .data("rks", "1")
//                .data("rn", "0")
//                .data("all", "1")
//                .data("isFrame", "")
//                .data("hr", "1")
//                .data("frameJeoId", "0")
//                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
//                .post()
//                .select(ROW_CLASS);
//        System.out.println(select);

        var parse = new LvivHotlineIssuesParserImpl().parse(LocalDate.now().minus(40, ChronoUnit.DAYS), LocalDate.now());
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
