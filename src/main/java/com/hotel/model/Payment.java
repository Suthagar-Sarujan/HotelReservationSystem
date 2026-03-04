package com.hotel.model;

import com.hotel.model.enums.PaymentMethod;
import com.hotel.model.enums.PaymentStatus;

import java.util.Date;

public class Payment extends BaseEntity {
    private String paymentNo;
    private int reservationId;
    private double amount;
    private Date paymentDate;
    private PaymentMethod method;
    private PaymentStatus status;

    public Payment() {
        super();
        this.paymentNo = "PAY" + System.currentTimeMillis();
        this.paymentDate = new Date();
        this.status = PaymentStatus.PENDING;
    }

    public String getPaymentNo() { return paymentNo; }
    public void setPaymentNo(String paymentNo) { this.paymentNo = paymentNo; }
    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public void processPayment() {
        if (amount <= 0 || method == null) {
            this.status = PaymentStatus.FAILED;
            return;
        }
        this.status = PaymentStatus.COMPLETED;
    }

    public String generateReceipt() {
        return String.format("Receipt %s | Reservation #%d | Amount %.2f | Method %s | Status %s",
                paymentNo, reservationId, amount, method, status);
    }
}
