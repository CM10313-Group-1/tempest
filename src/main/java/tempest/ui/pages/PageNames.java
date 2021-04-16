package tempest.ui.pages;

public class PageNames {
    public static final String HOME = "Home";
    public static final String MANAGE_MODULES = "ManageModules";
    public static final String MANAGE_SESSIONS = "ManageSessions";
    public static final String ADD_MODULE = "AddModule";
    public static final String ADD_SESSION = "AddSession";
    public static final String DELETE_MODULE = "DeleteModule";
    public static final String DELETE_SESSION = "DeleteSession";
    public static final String CHART_VIEW = "ChartView";
    public static final String STACKED_BAR = "StackedBarChart";
    public static final String LINE = "LineChart";
    public static final String PIE = "PieChart";
    public static final String TIME_BAR = "TimeBarChart";
    public static final String DATA_PROTECTION = "DataProtection";

    public static String[] getCharts() {
        return new String[] {STACKED_BAR, LINE, PIE, TIME_BAR};
    }
}
