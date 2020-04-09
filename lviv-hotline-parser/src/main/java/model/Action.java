package model;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class Action {
    String id;
    LocalDateTime startDate;
    LocalDateTime estimatedEndDate;
    LocalDateTime endDate;
    List<Street> affectedStreets;
    LocalDateTime modificationDate;
    String reason;
}
