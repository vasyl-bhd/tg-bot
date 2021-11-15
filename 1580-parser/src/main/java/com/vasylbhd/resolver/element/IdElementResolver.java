package com.vasylbhd.resolver.element;

import com.vasylbhd.resolver.element.constants.ElementType;
import org.jsoup.nodes.Element;

public class IdElementResolver implements ElementResolver<String> {

    private static final String ID_ATTRIBUTE = "id";

    @Override
    public String resolve(Element element) {
        return element.attr(ID_ATTRIBUTE);
    }

    @Override
    public ElementType getType() {
        return ElementType.Id;
    }
}
