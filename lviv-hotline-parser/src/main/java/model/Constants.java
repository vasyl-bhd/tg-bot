package model;

public final class Constants {
    private Constants() {}


    public static final String LVIV_HOTLINE_URL = "https://1580.lviv.ua/ajax/inform/informList.php";
    public static final String REQUEST_FILTER_STRING_FORMAT = "from=%S&to=%S&streetid=2251&skyscraperid=&street=%S&house=&radio1580datetype=1&radio1580works=1";
    public static final String AND = " > ";
    public static final String BR = "<br>";
    public static final String DIV_PREFIX = "<div";

    public static final String ROW_CLASS = "div[class=row animcolor]";
    public static final String SIDEBAR_CLASS = "div[class=col-xs-1]";
    public static final String BODY_CLASS = "div[class=col-xs-11]";
    public static final String PANEL_DEFAULT = "div[class=panel panel-default]";
    public static final String PANEL_BODY = "div[class=panel-body animColor]";

    public static final String START_DATE_CLASS = SIDEBAR_CLASS + AND + "div[title=Дата початку події]";
    public static final String END_DATE_CLASS = SIDEBAR_CLASS + AND + "div[title=Дата завершення події]";
    public static final String ESTIMATED_END_DATE_CLASS = SIDEBAR_CLASS + AND + "div[title=Планова дата завершення події]";
    public static final String REASON_CLASS = BODY_CLASS + AND + PANEL_DEFAULT + AND + "div[class=panel-heading animColor]";
    public static final String STREETS_CLASS = BODY_CLASS + AND + PANEL_DEFAULT + AND + PANEL_BODY;
    public static final String MODIFICATION_DATE_CLASS = BODY_CLASS + AND + PANEL_DEFAULT + AND + PANEL_BODY + AND + "div[class=pull-right]";

    public static final String GROOVE_STREET_SWEET_HOME = "Шевченка Т  вул., м.Львів";

    public static final String MODIFICATION_DATE = "Дата модифікації: ";
    public static final String DOUBLE_NBSP_NBSP = ",&nbsp;&nbsp;";
    public static final String EMPTY_STRING = "";
    public static final String DASH = " - ";
    public static final String HOUSE = "буд.";
    public static final String HOUSE_SUFFIX = " " + HOUSE;
    public static final String HOUSE_PREFIX = HOUSE + " ";
}
