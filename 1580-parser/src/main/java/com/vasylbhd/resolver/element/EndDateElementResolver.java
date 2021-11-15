package com.vasylbhd.resolver.element;

import com.vasylbhd.resolver.element.constants.ElementType;

import static com.vasylbhd.model.Constants.END_DATE_CLASS;
import static com.vasylbhd.model.Constants.START_DATE_CLASS;

public class EndDateElementResolver extends AbstractDateResolver {
    @Override
    protected String getSelector() {
        return END_DATE_CLASS;
    }

    @Override
    public ElementType getType() {
        return ElementType.EndDate;
    }
}
