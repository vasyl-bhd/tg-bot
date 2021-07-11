package com.vasylbhd.bot.core.config;

import io.micronaut.context.annotation.Factory;
import com.vasylbhd.parser.LvivHotlineIssuesParser;
import com.vasylbhd.parser.LvivHotlineIssuesParserImpl;

import javax.inject.Singleton;

@Factory
public class LvivHotlineParserFactory {

    @Singleton
    public LvivHotlineIssuesParser parser() {
        return new LvivHotlineIssuesParserImpl();
    }
}
