package com.vasylbhde.model;

import java.time.LocalDateTime;
import java.util.List;

public record Action(
        String id,
        LocalDateTime startDate,
        LocalDateTime estimatedEndDate,
        LocalDateTime endDate,
        List<Street> affectedStreets,
        LocalDateTime modificationDate,
        String reason
){}
