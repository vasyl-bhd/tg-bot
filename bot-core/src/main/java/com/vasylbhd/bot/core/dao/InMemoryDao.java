package com.vasylbhd.bot.core.dao;

import com.vasylbhde.model.Action;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class InMemoryDao {
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


}
