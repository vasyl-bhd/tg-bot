package model;

import lombok.Value;

import java.util.List;

@Value
public class Street {
    String name;
    List<String> houseNumber;
}
