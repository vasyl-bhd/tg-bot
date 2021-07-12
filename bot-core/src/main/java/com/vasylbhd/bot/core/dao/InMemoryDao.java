package com.vasylbhd.bot.core.dao;

import com.vasylbhd.model.Action;
import io.micronaut.context.annotation.Requires;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Singleton
@Requires(property = "telegram.bot.persistence", value = "in-memory")
public class InMemoryDao implements TgMetadataDao {
    private static final Map<String, LocalDateTime> idToEndDateMap = new HashMap<>();

    public void save(Action action) {
        idToEndDateMap.put(action.id(), action.estimatedEndDate());
    }

    public boolean contains(String id) {
        return idToEndDateMap.containsKey(id);
    }

    public LocalDateTime get(String id) {
        return idToEndDateMap.get(id);
    }

    public Map<String, LocalDateTime> getAll() {
        return idToEndDateMap;
    }

    @Override
    public long remove(List<String> keys) {
        return keys.stream()
                .map(idToEndDateMap::remove)
                .filter(Objects::nonNull)
                .count();
    }
}
