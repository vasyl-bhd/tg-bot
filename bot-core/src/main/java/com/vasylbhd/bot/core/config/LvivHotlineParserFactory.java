package com.vasylbhd.bot.core.config;

import io.micronaut.context.annotation.Factory;
import com.vasylbhde.parser.LvivHotlineIssuesParser;
import com.vasylbhde.parser.LvivHotlineIssuesParserImpl;

import javax.inject.Singleton;

@Factory
public class LvivHotlineParserFactory {

    @Singleton
    public LvivHotlineIssuesParser parser() {
        return new LvivHotlineIssuesParserImpl();
    }
}
