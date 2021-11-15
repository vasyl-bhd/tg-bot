package com.vasylbhd.resolver.element;

import com.vasylbhd.resolver.element.constants.ElementType;
import org.jsoup.nodes.Element;

public interface ElementResolver<T> {

    T resolve(Element element);

    ElementType getType();
}
