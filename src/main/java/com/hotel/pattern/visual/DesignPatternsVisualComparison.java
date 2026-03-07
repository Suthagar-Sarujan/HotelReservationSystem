package com.hotel.pattern.visual;


public class DesignPatternsVisualComparison {

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("   HOTEL RESERVATION SYSTEM - DESIGN PATTERNS VISUAL COMPARISON");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        System.out.println("1. SINGLETON PATTERN");
        System.out.println("   [DatabaseConnection]");
        System.out.println("   Ensures only one instance of DatabaseConnection exists\n");

        System.out.println("2. FACTORY PATTERN");
        System.out.println("   [ServiceFactory]");
        System.out.println("   Centralized creation of service objects\n");

        System.out.println("3. DAO PATTERN");
        System.out.println("   [ReservationDAO, UserDAO]");
        System.out.println("   Abstracts data access with CRUD operations\n");

        System.out.println("4. OBSERVER PATTERN");
        System.out.println("   [ReservationSubject, EmailNotifier]");
        System.out.println("   Notifies observers of reservation changes\n");

        System.out.println("5. STRATEGY PATTERN");
        System.out.println("   [PricingCalculator]");
        System.out.println("   Different pricing strategies for reservations\n");

        System.out.println("6. MVC PATTERN");
        System.out.println("   Overall application architecture");
        System.out.println("   Separates Model, View, and Controller layers\n");

        System.out.println("═══════════════════════════════════════════════════════════");
    }

}

