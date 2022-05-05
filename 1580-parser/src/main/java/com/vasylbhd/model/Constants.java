package com.vasylbhd.model;

public final class Constants {
    private Constants() {}


    public static final String LVIV_HOTLINE_URL = "https://1580.lviv.ua/ajax/inform/InformList-reserv.php";
    public static final String REQUEST_FILTER_STRING_FORMAT = "from=%S&to=%S&streetid=2251&skyscraperid=&street=%S&house=%d&radio1580datetype=1&radio1580works=1";
    public static final String AND = " > ";

    public static final String ROW_CLASS = "div[class*=row gx-3 mt-4]";
    public static final String SIDEBAR_CLASS = "div[class*=dateinform]";
    public static final String BODY_CLASS = "div[class*=messageinform]";
    public static final String PANEL_DEFAULT = "div[class*=panelinform]";
    public static final String PANEL_FOOTER = "div[class*=card-footer]";

    public static final String START_DATE_CLASS = "div[class*=StartDate]";
    public static final String END_DATE_CLASS = "div[class*=EndDate]";
    public static final String ESTIMATED_END_DATE_CLASS = "div[class*=PlanDate]";
    public static final String REASON_CLASS = BODY_CLASS + AND + PANEL_DEFAULT + AND + "div[class*=card-header]";
    public static final String STREETS_CLASS = "div[class=card-body animColor]";
    public static final String MODIFICATION_DATE_CLASS = BODY_CLASS + AND + PANEL_DEFAULT  + AND + PANEL_FOOTER;

    public static final String GROOVE_STREET_SWEET_HOME = "Шевченка Т  вул., м.Львів";
    public static final int HOUSE_NUMBER = 108;

    public static final String MODIFICATION_DATE = "Дата модифікації: ";
    public static final String DOUBLE_NBSP_NBSP = ",&nbsp;&nbsp;";
    public static final String EMPTY_STRING = "";
    public static final String DASH = " - ";
    public static final String HOUSE = "буд.";
    public static final String HOUSE_SUFFIX = " " + HOUSE;
    public static final String HOUSE_PREFIX = HOUSE + " ";
}
