package com.vasylbhd.resolver.element;

import com.vasylbhd.model.Street;
import com.vasylbhd.resolver.element.constants.ElementType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

import static com.vasylbhd.model.Constants.*;
import static java.util.Collections.emptyList;
import static java.util.function.Predicate.not;

public class StreetsElementResolver implements ElementResolver<List<Street>> {

    @Override
    public List<Street> resolve(Element element) {
        return getStreets(element.select(STREETS_CLASS));
    }

    @Override
    public ElementType getType() {
        return ElementType.AffectedStreets;
    }


    private Street getStreetFromLines(String[] lines) {
        List<String> streetNumbers = lines.length < 2
                ? emptyList()
                : List.of(lines[1].replace(HOUSE_SUFFIX, EMPTY_STRING).split(DOUBLE_NBSP_NBSP));

        return new Street(lines[0], streetNumbers);
    }

    private String getSplitter(String s) {
        return s.contains(HOUSE_PREFIX) ? HOUSE_PREFIX : DASH;
    }

    private List<Street> getStreets(Elements element) {
        return element.textNodes().stream()
                .map(TextNode::text)
                .filter(not(String::isBlank))
                .map(s -> s.split(getSplitter(s)))
                .map(this::getStreetFromLines)
                .collect(Collectors.toList());
    }
}
