package com.vasylbhde.parser;

import com.vasylbhde.model.Action;

import java.time.LocalDate;
import java.util.List;

public interface LvivHotlineIssuesParser {
    List<Action> parse(LocalDate from, LocalDate to);
}
