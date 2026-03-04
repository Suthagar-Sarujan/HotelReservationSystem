package com.hotel.web.listener;

import com.hotel.util.DatabaseConnection;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationLifecycleListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // No-op. Connections are initialized lazily when first used.
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DatabaseConnection.shutdown();
        } catch (RuntimeException ignored) {
            // Best-effort cleanup on shutdown.
        }

        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Throwable ignored) {
            // Best-effort cleanup on shutdown.
        }
    }
}
