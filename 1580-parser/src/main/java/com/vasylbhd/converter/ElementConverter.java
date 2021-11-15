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
                this.getElementAndCast(ElementType.Id, element),
                this.getElementAndCast(ElementType.StartDate, element),
                this.getElementAndCast(ElementType.EstimatedEndDate, element),
                this.getElementAndCast(ElementType.EndDate, element),
                this.getElementAndCast(ElementType.AffectedStreets, element),
                this.getElementAndCast(ElementType.ModificationDate, element),
                this.getElementAndCast(ElementType.Reason, element));
    }

    private <T> T getElementAndCast(final ElementType elementType, final Element element) {
        return (T) elementTypeMap.get(elementType).resolve(element);
    }

}
