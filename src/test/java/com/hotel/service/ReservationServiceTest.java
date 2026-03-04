package com.hotel.service;

import com.hotel.dao.ReservationDAO;
import com.hotel.model.Reservation;
import com.hotel.model.enums.RoomType;
import com.hotel.service.impl.ReservationServiceImpl;
import com.hotel.util.BillCalculator;
import com.hotel.util.ValidationHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReservationServiceTest {
    private IReservationService reservationService;
    private InMemoryReservationDAO reservationDAO;

    @Before
    public void setUp() {
        reservationDAO = new InMemoryReservationDAO();
        reservationService = new ReservationServiceImpl(reservationDAO, new BillCalculator(), new ValidationHelper());
    }

    @Test
    public void testAddValidReservation() {
        Reservation saved = reservationService.addReservation(createValidReservation());
        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
        assertNotNull(saved.getReservationNo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidReservation() {
        Reservation invalid = new Reservation();
        invalid.setGuestName("X");
        reservationService.addReservation(invalid);
    }

    @Test
    public void testSearchByGuestName() {
        Reservation reservation = createValidReservation();
        reservation.setGuestName("Jane Test");
        reservationService.addReservation(reservation);
        List<Reservation> result = reservationService.searchByGuestName("Jane");
        assertEquals(1, result.size());
        assertEquals("Jane Test", result.get(0).getGuestName());
    }

    @Test
    public void testCalculateBill() {
        Reservation reservation = createValidReservation();
        reservation.setRoomType(RoomType.SUITE);
        reservation.setCheckInDate(new Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000));
        reservation.setCheckOutDate(new Date(System.currentTimeMillis() + 4L * 24 * 60 * 60 * 1000));
        Reservation saved = reservationService.addReservation(reservation);

        double bill = reservationService.calculateBill(saved.getId());
        assertTrue(bill > 0);
    }

    @Test
    public void testDashboardStats() {
        reservationService.addReservation(createValidReservation());
        Map<String, Object> stats = reservationService.getDashboardStats();
        assertNotNull(stats);
        assertTrue(stats.containsKey("totalReservations"));
        assertTrue(stats.containsKey("totalRevenue"));
        assertTrue(stats.containsKey("occupancyRate"));
    }

    private Reservation createValidReservation() {
        Reservation reservation = new Reservation();
        reservation.setGuestName("Test Guest");
        reservation.setAddress("123 Test Street");
        reservation.setContactNo("1234567890");
        reservation.setEmail("test@example.com");
        reservation.setRoomType(RoomType.DOUBLE);
        reservation.setCheckInDate(new Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000));
        reservation.setCheckOutDate(new Date(System.currentTimeMillis() + 3L * 24 * 60 * 60 * 1000));
        reservation.setNumGuests(2);
        return reservation;
    }

    private static class InMemoryReservationDAO implements ReservationDAO {
        private final List<Reservation> data = new ArrayList<>();
        private int sequence = 1;

        @Override
        public Reservation save(Reservation reservation) {
            reservation.setId(sequence++);
            data.add(reservation);
            return reservation;
        }

        @Override
        public Reservation findById(int id) {
            return data.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
        }

        @Override
        public Reservation findByReservationNo(String reservationNo) {
            return data.stream().filter(r -> reservationNo.equals(r.getReservationNo())).findFirst().orElse(null);
        }

        @Override
        public List<Reservation> findByGuestName(String guestName) {
            List<Reservation> out = new ArrayList<>();
            for (Reservation reservation : data) {
                if (reservation.getGuestName() != null && reservation.getGuestName().contains(guestName)) {
                    out.add(reservation);
                }
            }
            return out;
        }

        @Override
        public List<Reservation> findByDateRange(Date startDate, Date endDate) {
            List<Reservation> out = new ArrayList<>();
            for (Reservation reservation : data) {
                if (reservation.getCheckInDate() != null && reservation.getCheckOutDate() != null
                        && !reservation.getCheckInDate().before(startDate)
                        && !reservation.getCheckOutDate().after(endDate)) {
                    out.add(reservation);
                }
            }
            return out;
        }

        @Override
        public List<Reservation> findAll() {
            return new ArrayList<>(data);
        }

        @Override
        public Reservation update(Reservation reservation) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId() == reservation.getId()) {
                    data.set(i, reservation);
                    return reservation;
                }
            }
            return null;
        }

        @Override
        public boolean delete(int id) {
            return data.removeIf(r -> r.getId() == id);
        }

        @Override
        public int getTotalCount() {
            return data.size();
        }

        @Override
        public double getTotalRevenue() {
            double total = 0.0;
            for (Reservation reservation : data) {
                total += reservation.getTotalAmount();
            }
            return total;
        }
    }
}
