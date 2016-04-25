package com.van.halley.core.session;


public class SessionContextHolder {
    public static final String SYSTEM_PROPERTY = "com.van.halley.core.session.defaultstrategy";
    private static String strategyName = System.getProperty(SYSTEM_PROPERTY);
    private static SessionContextHolderStrategy strategy;
    private static int initializeCount = 0;

    static {
        initialize();
    }

    public static void clearContext() {
        strategy.clearContext();
    }

    public static SessionContext getContext() {
        return strategy.getContext();
    }

    public static int getInitializeCount() {
        return initializeCount;
    }

    private static void initialize() {
        strategy = new SessionContextHolderStrategy();
        initializeCount++;
    }

    public static void setContext(SessionContext context) {
        strategy.setContext(context);
    }

    public static void setStrategyName(String strategyName) {
    	SessionContextHolder.strategyName = strategyName;
        initialize();
    }

    public static SessionContextHolderStrategy getContextHolderStrategy() {
        return strategy;
    }

    public static SessionContext createEmptyContext() {
        return strategy.createEmptyContext();
    }

    public String toString() {
        return "SettingContextHolder[strategy='" + strategyName + "'; initializeCount=" + initializeCount + "]";
    }
}
