package com.vasylbhd.bot.core.dao;

import com.vasylbhd.model.Action;
import io.lettuce.core.api.StatefulRedisConnection;
import io.micronaut.context.annotation.Requires;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Singleton
@RequiredArgsConstructor
@Requires(property = "telegram.bot.persistence", value = "redis", defaultValue = "redis")
public class RedisTgMetadataDao implements TgMetadataDao {
    private static final String PREFIX = "tg-";

    private final StatefulRedisConnection<String, LocalDateTime> redisConnection;


    @Override
    public void save(Action action) {
        var commands = redisConnection.sync();
        commands.set(getKey(action.id()), action.estimatedEndDate());

    }

    @Override
    public boolean contains(String id) {
        var commands = redisConnection.sync();

        return commands.get(getKey(id)) != null;
    }

    @Override
    public LocalDateTime get(String id) {
        var commands = redisConnection.sync();
        return commands.get(getKey(id));
    }

    @Override
    public Map<String, LocalDateTime> getAll() {
        var commands = redisConnection.sync();
        return commands.keys(getKey("*"))
                .stream()
                .map(k -> Map.entry(k, commands.get(k)))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String getKey(String id) {
        return PREFIX + id;
    }
}
