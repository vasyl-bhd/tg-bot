package parser;

import converter.ElementConverter;
import lombok.SneakyThrows;
import model.Action;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static model.Constants.*;

public class LvivHotlineIssuesParserImpl implements LvivHotlineIssuesParser {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");

    @Override
    @SneakyThrows
    public List<Action> parse(LocalDate from, LocalDate to) {

        Document post = Jsoup.connect(LVIV_HOTLINE_URL)
                .data("data", getRequestData(from, to))
                .data("rn", "0")
                .data("all", "1")
                .data("isFrame", "")
                .data("hr", "1")
                .data("frameJeoId", "0")
                .post();

        return post.select(ROW_CLASS)
                .stream()
                .map(ElementConverter::toAction)
                .collect(Collectors.toList());
    }

    private String getRequestData(LocalDate from, LocalDate to) {
        return String.format(
                model.Constants.REQUEST_FILTER_STRING_FORMAT,
                from.format(dateTimeFormatter),
                to.format(dateTimeFormatter),
                GROOVE_STREET_SWEET_HOME);
    }
}