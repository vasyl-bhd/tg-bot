package com.vasylbhd.bot.core.config;

import com.vasylbhd.converter.ElementConverter;
import com.vasylbhd.model.Street;
import com.vasylbhd.parser.LvivHotlineIssuesParser;
import com.vasylbhd.parser.LvivHotlineIssuesParserImpl;
import com.vasylbhd.resolver.element.*;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Factory
public class LvivHotlineParserFactory {

    @Singleton
    public LvivHotlineIssuesParser parser(final ElementConverter elementConverter) {
        return new LvivHotlineIssuesParserImpl(elementConverter);
    }

    @Singleton
    public ElementConverter elementConverter() {
        return new ElementConverter(List.of(
                new StartDateElementResolver(),
                new EndDateElementResolver(),
                new ModificationDateElementResolver(),
                new EstimatedDateElementResolver(),
                new ReasonElementResolver(),
                new IdElementResolver(),
                new StreetsElementResolver()
        ));
    }

}
