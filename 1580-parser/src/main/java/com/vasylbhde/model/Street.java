package com.vasylbhde.model;

import java.util.List;

public record Street(
    String name,
    List<String> houseNumber
){}
