package com.vasylbhd.lvivhotlinebot.model;

import lombok.Value;
import model.Action;
import model.Street;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class LvivHotlineResponse {
    String id;
    LocalDateTime startDate;
    LocalDateTime estimatedEndDate;
    LocalDateTime endDate;
    String affectedStreets;
    LocalDateTime modificationDate;
    String reason;

    public String toTelegramResponse() {
        return String.format("ID: %s\n\nDate registered: %s\n\nStart date: %s\n\nAproximate end date: %s\n\nReason: %s\n\nAffected streets: %s",
                id, modificationDate, startDate, estimatedEndDate, reason, affectedStreets);
    }

    private static String streetsToString(List<Street> streets) {
        return streets.stream()
                .map(street -> String.format("%s - %s",
                        street.getName(), String.join(", ", street.getHouseNumber())))
                .collect(Collectors.joining(";\n"));
    }

    public static LvivHotlineResponse fromAction(Action action) {
        return new LvivHotlineResponse(action.getId(),
                action.getStartDate(),
                action.getEstimatedEndDate(),
                action.getEndDate(),
                streetsToString(action.getAffectedStreets()),
                action.getModificationDate(),
                action.getReason());
    }
}
