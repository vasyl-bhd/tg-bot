package utils;

import model.Street;

import java.util.List;

import static model.Constants.*;
import static model.Constants.MODIFICATION_DATE;

public class Ютиліти {

    public static Street getStreetFromLines(String[] lines) {
        return new Street(lines[0], List.of(lines[1].replace(HOUSE_SUFFIX, EMPTY_STRING).split(DOUBLE_NBSP_NBSP)));
    }

    public static String getSplitter(String s) {
        return s.contains(HOUSE_PREFIX) ? HOUSE_PREFIX : DASH;
    }

    public static String getModificationDate(String div) {
        return div.substring(div.indexOf(MODIFICATION_DATE) + MODIFICATION_DATE.length());
    }
}
