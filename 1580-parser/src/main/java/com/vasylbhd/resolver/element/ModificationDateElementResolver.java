package com.vasylbhd.resolver.element;

import com.vasylbhd.resolver.element.constants.ElementType;
import org.jsoup.nodes.Element;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.vasylbhd.model.Constants.*;

public class ModificationDateElementResolver extends AbstractDateResolver {

    private static final DateTimeFormatter modDateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu kk:mm");

    @Override
    protected String getSelector() {
        return null;
    }

    @Override
    public LocalDateTime resolve(Element element) {
        return toLocalDateTime(getModificationDateTime(element.select(MODIFICATION_DATE_CLASS).text()), modDateTimeFormatter);
    }

    @Override
    public ElementType getType() {
        return ElementType.ModificationDate;
    }


    private String getModificationDateTime(String div) {
        return div.isEmpty() ? div : div.substring(div.indexOf(MODIFICATION_DATE) + MODIFICATION_DATE.length());
    }
}
