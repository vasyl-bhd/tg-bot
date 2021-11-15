package com.vasylbhd.converter;

import com.vasylbhd.model.Action;
import com.vasylbhd.model.Street;
import com.vasylbhd.resolver.element.ElementResolver;
import com.vasylbhd.resolver.element.constants.ElementType;
import org.jsoup.nodes.Element;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public final class ElementConverter {
    private final Map<ElementType, ElementResolver> elementTypeMap;

    public ElementConverter(List<ElementResolver> elementResolvers){
        this.elementTypeMap = elementResolvers.stream().collect(toMap(ElementResolver::getType, Function.identity()));
    }

    public Action toAction(Element element) {
        return new Action(
                (String) getElementAndCast(ElementType.Id).apply(element),
                (LocalDateTime) getElementAndCast(ElementType.StartDate).apply(element),
                (LocalDateTime) getElementAndCast(ElementType.EstimatedEndDate).apply(element),
                (LocalDateTime) getElementAndCast(ElementType.EndDate).apply(element),
                (List<Street>) getElementAndCast(ElementType.AffectedStreets).apply(element),
                (LocalDateTime) getElementAndCast(ElementType.ModificationDate).apply(element),
                (String) getElementAndCast(ElementType.Reason).apply(element));
    }

    private <T> Function<Element, T> getElementAndCast(final ElementType elementType) {
        return (Element element) -> (T) elementTypeMap.get(elementType).resolve(element);
    }

}
