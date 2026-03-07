package com.hotel.pattern;

import com.hotel.util.DatabaseConnection;

public class AllPatternsShowcase {

    public static void demonstrateFactoryPattern() {
        System.out.println("=== FACTORY PATTERN DEMONSTRATION ===");
        System.out.println("See: com.hotel.pattern.factory.ServiceFactory");
        System.out.println("Creates service objects through centralized factory");
        System.out.println();
    }

    public static void demonstrateSingletonPattern() {
        System.out.println("=== SINGLETON PATTERN DEMONSTRATION ===");

        // Get singleton instance
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();

        // Verify same instance
        System.out.println("Same instance: " + (db1 == db2)); // true
        System.out.println();
    }


    public static void demonstrateObserverPattern() {
        System.out.println("=== OBSERVER PATTERN DEMONSTRATION ===");
        System.out.println("See: com.hotel.pattern.observer.ReservationSubject");
        System.out.println("Notifies multiple observers when reservations change");
        System.out.println();
    }


    public static void demonstrateDAOPattern() {
        System.out.println("=== DAO PATTERN DEMONSTRATION ===");
        System.out.println("See: com.hotel.dao.ReservationDAO, UserDAO");
        System.out.println("Provides CRUD operations with database abstraction");
        System.out.println();
    }


    public static void demonstrateStrategyPattern() {
        System.out.println("=== STRATEGY PATTERN DEMONSTRATION ===");
        System.out.println("See: com.hotel.pattern.strategy.StrategyPatternDemo");
        System.out.println("Different pricing strategies for reservations");
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("\n" + "═".repeat(80));
        System.out.println("      HOTEL RESERVATION SYSTEM - DESIGN PATTERNS SHOWCASE");
        System.out.println("═".repeat(80) + "\n");

        demonstrateFactoryPattern();
        demonstrateSingletonPattern();
        demonstrateObserverPattern();
        demonstrateDAOPattern();
        demonstrateStrategyPattern();

        System.out.println("═".repeat(80));
        System.out.println("All design patterns demonstrated successfully!");
        System.out.println("═".repeat(80) + "\n");
    }
}


