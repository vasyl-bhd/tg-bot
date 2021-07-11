package com.vasylbhd.bot.core.model;

import com.vasylbhde.model.Action;
import com.vasylbhde.model.Street;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record LvivHotlineResponse(
        String id,
        LocalDateTime startDate,
        LocalDateTime estimatedEndDate,
        LocalDateTime endDate,
        String affectedStreets,
        LocalDateTime modificationDate,
        String reason) {


    public String toTelegramResponse() {
        return String.format("ID: %s\n\nDate registered: %s\n\nStart date: %s\n\nAproximate end date: %s\n\nReason: %s\n\nAffected streets: %s",
                id, modificationDate, startDate, estimatedEndDate, reason, affectedStreets);
    }

    private static String streetsToString(List<Street> streets) {
        return streets.stream()
                .map(street -> String.format("%s - %s",
                        street.name(), String.join(", ", street.houseNumber())))
                .collect(Collectors.joining(";\n"));
    }

    public static LvivHotlineResponse fromAction(Action action) {
        return new LvivHotlineResponse(action.id(),
                action.startDate(),
                action.estimatedEndDate(),
                action.endDate(),
                streetsToString(action.affectedStreets()),
                action.modificationDate(),
                action.reason());
    }
}
