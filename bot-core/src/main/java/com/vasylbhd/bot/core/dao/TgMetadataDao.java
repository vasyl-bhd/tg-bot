package com.vasylbhd.bot.core.dao;

import com.vasylbhd.model.Action;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TgMetadataDao {

    void save(Action action);

    boolean contains(String id);

    LocalDateTime get(String id);

    Map<String, LocalDateTime> getAll();

    long remove(List<String> keys);

}
