package com.vasylbhd.lvivhotlinebot.config;

import io.micronaut.context.annotation.Factory;
import parser.LvivHotlineIssuesParser;
import parser.LvivHotlineIssuesParserImpl;

import javax.inject.Singleton;

@Factory
public class LvivHotlineParserFactory {

    @Singleton
    public LvivHotlineIssuesParser parser() {
        return new LvivHotlineIssuesParserImpl();
    }
}
