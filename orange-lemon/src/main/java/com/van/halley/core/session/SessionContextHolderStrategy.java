package com.van.halley.core.session;


public class SessionContextHolderStrategy {
    private static final ThreadLocal<SessionContext> contextHolder = new ThreadLocal<SessionContext>();

    public void clearContext() {
        contextHolder.remove();
    }

    public SessionContext getContext() {
    	SessionContext ctx = contextHolder.get();

        if (ctx == null) {
            ctx = new SessionContextImpl();
            contextHolder.set(ctx);
        }

        return ctx;
    }

    public void setContext(SessionContext context) {
        contextHolder.set(context);
    }

    public SessionContext createEmptyContext() {
        return new SessionContextImpl();
    }
}
