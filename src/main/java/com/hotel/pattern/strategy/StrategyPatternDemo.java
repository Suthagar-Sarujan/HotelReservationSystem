package com.hotel.pattern.strategy;

import com.hotel.model.Reservation;

public class StrategyPatternDemo {


    public static void demonstrateStrategyPattern() {
        Reservation reservation = createSampleReservation();

        PricingCalculator calculator = new PricingCalculator();

        calculator.setPricingStrategy(new RegularPricingStrategy());
        double regularPrice = calculator.calculatePrice(reservation);
        System.out.println("Regular Price: $" + regularPrice);

        calculator.setPricingStrategy(new WeekendPricingStrategy());
        double weekendPrice = calculator.calculatePrice(reservation);
        System.out.println("Weekend Price: $" + weekendPrice);

        calculator.setPricingStrategy(new SeasonalDiscountStrategy());
        double discountedPrice = calculator.calculatePrice(reservation);
        System.out.println("Seasonal Discount Price: $" + discountedPrice);

        calculator.setPricingStrategy(new CorporatePricingStrategy());
        double corporatePrice = calculator.calculatePrice(reservation);
        System.out.println("Corporate Rate: $" + corporatePrice);
    }



    private static Reservation createSampleReservation() {
        Reservation reservation = new Reservation();
        reservation.setNumNights(3);
        // Assume base rate is set on room type
        return reservation;
    }
}


interface PricingStrategy {
    double calculatePrice(Reservation reservation);
}


class PricingCalculator {
    private PricingStrategy pricingStrategy;

    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
    }

    public double calculatePrice(Reservation reservation) {
        if (pricingStrategy == null) {
            throw new IllegalStateException("Pricing strategy not set");
        }
        return pricingStrategy.calculatePrice(reservation);
    }
}


class RegularPricingStrategy implements PricingStrategy {
    private static final double BASE_RATE = 100.0;
    private static final double TAX_RATE = 0.10;
    private static final double SERVICE_CHARGE = 0.05;

    @Override
    public double calculatePrice(Reservation reservation) {
        double subtotal = BASE_RATE * reservation.getNumNights();
        double tax = subtotal * TAX_RATE;
        double service = subtotal * SERVICE_CHARGE;

        System.out.println("  Base: $" + subtotal);
        System.out.println("  Tax (10%): $" + tax);
        System.out.println("  Service (5%): $" + service);

        return subtotal + tax + service;
    }
}


class WeekendPricingStrategy implements PricingStrategy {
    private static final double BASE_RATE = 100.0;
    private static final double WEEKEND_SURCHARGE = 0.25; // 25% increase
    private static final double TAX_RATE = 0.10;
    private static final double SERVICE_CHARGE = 0.05;

    @Override
    public double calculatePrice(Reservation reservation) {
        double basePrice = BASE_RATE * reservation.getNumNights();
        double surcharge = basePrice * WEEKEND_SURCHARGE;
        double subtotal = basePrice + surcharge;
        double tax = subtotal * TAX_RATE;
        double service = subtotal * SERVICE_CHARGE;

        System.out.println("  Base: $" + basePrice);
        System.out.println("  Weekend Surcharge (25%): $" + surcharge);
        System.out.println("  Tax (10%): $" + tax);
        System.out.println("  Service (5%): $" + service);

        return subtotal + tax + service;
    }
}


class SeasonalDiscountStrategy implements PricingStrategy {
    private static final double BASE_RATE = 100.0;
    private static final double DISCOUNT_RATE = 0.20; // 20% off
    private static final double TAX_RATE = 0.10;
    private static final double SERVICE_CHARGE = 0.05;

    @Override
    public double calculatePrice(Reservation reservation) {
        double basePrice = BASE_RATE * reservation.getNumNights();
        double discount = basePrice * DISCOUNT_RATE;
        double subtotal = basePrice - discount;
        double tax = subtotal * TAX_RATE;
        double service = subtotal * SERVICE_CHARGE;

        System.out.println("  Base: $" + basePrice);
        System.out.println("  Seasonal Discount (20%): -$" + discount);
        System.out.println("  Tax (10%): $" + tax);
        System.out.println("  Service (5%): $" + service);

        return subtotal + tax + service;
    }
}


class CorporatePricingStrategy implements PricingStrategy {
    private static final double CORPORATE_RATE = 85.0; // Discounted base rate
    private static final double TAX_RATE = 0.10;
    // Corporate bookings waive service charge

    @Override
    public double calculatePrice(Reservation reservation) {
        double subtotal = CORPORATE_RATE * reservation.getNumNights();
        double tax = subtotal * TAX_RATE;

        System.out.println("  Corporate Rate: $" + subtotal);
        System.out.println("  Tax (10%): $" + tax);
        System.out.println("  Service Charge: WAIVED");

        return subtotal + tax;
    }
}


interface PaymentStrategy {
    boolean processPayment(double amount, String accountDetails);
    String getPaymentMethod();
}

class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, String cardNumber) {
        // Validate card number
        // Process through payment gateway
        // Apply credit card processing fee
        System.out.println("Processing $" + amount + " via Credit Card");
        System.out.println("Card: ****" + cardNumber.substring(cardNumber.length() - 4));
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
}

class BankTransferPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, String accountNumber) {
        // Validate account number
        // Generate transfer reference
        // No processing fee
        System.out.println("Processing $" + amount + " via Bank Transfer");
        System.out.println("Account: " + accountNumber);
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Bank Transfer";
    }
}

class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, String receiptNumber) {
        // Record cash payment
        // Generate receipt
        System.out.println("Processing $" + amount + " via Cash");
        System.out.println("Receipt: " + receiptNumber);
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Cash";
    }
}

