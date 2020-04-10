package com.vasylbhd.command;
import java.util.List;

public interface CommandProcessor {
    List<String> process();
    Command getCommand();
}
