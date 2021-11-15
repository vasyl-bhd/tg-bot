package com.vasylbhd.resolver.element;

import com.vasylbhd.resolver.element.constants.ElementType;

import static com.vasylbhd.model.Constants.START_DATE_CLASS;

public class StartDateElementResolver extends AbstractDateResolver {
    @Override
    protected String getSelector() {
        return START_DATE_CLASS;
    }

    @Override
    public ElementType getType() {
        return ElementType.StartDate;
    }
}
