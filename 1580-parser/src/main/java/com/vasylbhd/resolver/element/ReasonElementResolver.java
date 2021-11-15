package com.vasylbhd.resolver.element;

import com.vasylbhd.resolver.element.constants.ElementType;
import org.jsoup.nodes.Element;

import static com.vasylbhd.model.Constants.REASON_CLASS;

public class ReasonElementResolver implements ElementResolver<String> {

    @Override
    public String resolve(Element element) {
        return element.select(REASON_CLASS).text();
    }

    @Override
    public ElementType getType() {
        return ElementType.Reason;
    }
}
