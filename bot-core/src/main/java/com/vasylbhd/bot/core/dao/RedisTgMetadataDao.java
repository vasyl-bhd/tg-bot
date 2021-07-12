package com.vasylbhd.bot.core.dao;

import com.vasylbhd.model.Action;
import io.lettuce.core.api.StatefulRedisConnection;
import io.micronaut.context.annotation.Requires;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Singleton
@RequiredArgsConstructor
@Requires(property = "telegram.bot.persistence", value = "redis", defaultValue = "redis")
public class RedisTgMetadataDao implements TgMetadataDao {
    private static final String PREFIX = "tg-";

    private final StatefulRedisConnection<String, String> redisConnection;

    @Override
    public void save(Action action) {
        var commands = redisConnection.sync();
        var estimatedEndDate = getApproximateEndDate(action.estimatedEndDate());

        commands.set(getKey(action.id()), estimatedEndDate);
    }

    @Override
    public boolean contains(String id) {
        var commands = redisConnection.sync();

        return commands.get(getKey(id)) != null;
    }

    @Override
    public LocalDateTime get(String id) {
        var commands = redisConnection.sync();
        return LocalDateTime.parse(commands.get(getKey(id)));
    }

    @Override
    public Map<String, LocalDateTime> getAll() {
        var commands = redisConnection.sync();
        return commands.keys(getKey("*"))
                .stream()
                .map(k -> Map.entry(k, commands.get(k)))
                .collect(toMap(Map.Entry::getKey, e -> parseApproximateEndDate(e.getValue())));
    }

    @Override
    public long remove(List<String> keys) {
        var commands = redisConnection.sync();

        return commands.del(keys.toArray(new String[0]));
    }

    private String getKey(String id) {
        return PREFIX + id;
    }

    private String getApproximateEndDate(LocalDateTime estimatedEndDate) {
        return Optional.ofNullable(estimatedEndDate)
                .map(LocalDateTime::toString)
                .orElse(null);
    }

    private LocalDateTime parseApproximateEndDate(String estimatedEndDate) {
        return Optional.ofNullable(estimatedEndDate)
                .filter(not(String::isBlank))
                .map(LocalDateTime::parse)
                .orElse(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
    }
}
