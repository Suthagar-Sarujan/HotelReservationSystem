package com.hotel.dao.impl;

import com.hotel.dao.ReservationDAO;
import com.hotel.model.Reservation;
import com.hotel.model.enums.RoomType;
import com.hotel.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    private final DatabaseConnection dbConnection;

    public ReservationDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservations (reservation_no, guest_name, address, contact_no, email, room_type, check_in_date, check_out_date, num_nights, total_amount, special_requests, num_guests, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            pstmt.setString(1, reservation.getReservationNo());
            pstmt.setString(2, reservation.getGuestName());
            pstmt.setString(3, reservation.getAddress());
            pstmt.setString(4, reservation.getContactNo());
            pstmt.setString(5, reservation.getEmail());
            pstmt.setString(6, reservation.getRoomType().name());
            pstmt.setTimestamp(7, new Timestamp(reservation.getCheckInDate().getTime()));
            pstmt.setTimestamp(8, new Timestamp(reservation.getCheckOutDate().getTime()));
            pstmt.setInt(9, reservation.getNumNights());
            pstmt.setDouble(10, reservation.getTotalAmount());
            pstmt.setString(11, reservation.getSpecialRequests());
            pstmt.setInt(12, reservation.getNumGuests());
            pstmt.setTimestamp(13, new Timestamp(reservation.getCreatedAt().getTime()));
            pstmt.setTimestamp(14, new Timestamp(reservation.getUpdatedAt().getTime()));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        reservation.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return reservation;
        } catch (SQLException e) {
            throw new RuntimeException("Save reservation failed", e);
        }
    }

    @Override
    public Reservation findById(int id) {
        String sql = "SELECT * FROM reservations WHERE id = ?";
        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToReservation(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Find reservation by id failed", e);
        }
    }

    @Override
    public Reservation findByReservationNo(String reservationNo) {
        String sql = "SELECT * FROM reservations WHERE reservation_no = ?";
        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            pstmt.setString(1, reservationNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToReservation(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Find reservation by number failed", e);
        }
    }

    @Override
    public List<Reservation> findByGuestName(String guestName) {
        String sql = "SELECT * FROM reservations WHERE guest_name LIKE ? ORDER BY check_in_date DESC";
        List<Reservation> reservations = new ArrayList<>();
        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + guestName + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapResultSetToReservation(rs));
                }
            }
            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException("Find reservation by guest failed", e);
        }
    }

    @Override
    public List<Reservation> findByDateRange(Date startDate, Date endDate) {
        String sql = "SELECT * FROM reservations WHERE check_in_date >= ? AND check_out_date <= ? ORDER BY check_in_date";
        List<Reservation> reservations = new ArrayList<>();
        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            pstmt.setTimestamp(1, new Timestamp(startDate.getTime()));
            pstmt.setTimestamp(2, new Timestamp(endDate.getTime()));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapResultSetToReservation(rs));
                }
            }
            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException("Find reservations by date range failed", e);
        }
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservations ORDER BY created_at DESC";
        List<Reservation> reservations = new ArrayList<>();
        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException("Find all reservations failed", e);
        }
    }

    @Override
    public Reservation update(Reservation reservation) {
        String sql = "UPDATE reservations SET guest_name = ?, address = ?, contact_no = ?, email = ?, room_type = ?, check_in_date = ?, check_out_date = ?, num_nights = ?, total_amount = ?, special_requests = ?, num_guests = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            pstmt.setString(1, reservation.getGuestName());
            pstmt.setString(2, reservation.getAddress());
            pstmt.setString(3, reservation.getContactNo());
            pstmt.setString(4, reservation.getEmail());
            pstmt.setString(5, reservation.getRoomType().name());
            pstmt.setTimestamp(6, new Timestamp(reservation.getCheckInDate().getTime()));
            pstmt.setTimestamp(7, new Timestamp(reservation.getCheckOutDate().getTime()));
            pstmt.setInt(8, reservation.getNumNights());
            pstmt.setDouble(9, reservation.getTotalAmount());
            pstmt.setString(10, reservation.getSpecialRequests());
            pstmt.setInt(11, reservation.getNumGuests());
            pstmt.setTimestamp(12, new Timestamp(new Date().getTime()));
            pstmt.setInt(13, reservation.getId());
            pstmt.executeUpdate();
            return reservation;
        } catch (SQLException e) {
            throw new RuntimeException("Update reservation failed", e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM reservations WHERE id = ?";
        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Delete reservation failed", e);
        }
    }

    @Override
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM reservations";
        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Count reservations failed", e);
        }
    }

    @Override
    public double getTotalRevenue() {
        String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM reservations";
        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getDouble(1) : 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Get total revenue failed", e);
        }
    }

    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id"));
        reservation.setReservationNo(rs.getString("reservation_no"));
        reservation.setGuestName(rs.getString("guest_name"));
        reservation.setAddress(rs.getString("address"));
        reservation.setContactNo(rs.getString("contact_no"));
        reservation.setEmail(rs.getString("email"));
        reservation.setRoomType(RoomType.valueOf(rs.getString("room_type")));
        reservation.setCheckInDate(rs.getTimestamp("check_in_date"));
        reservation.setCheckOutDate(rs.getTimestamp("check_out_date"));
        reservation.setNumNights(rs.getInt("num_nights"));
        reservation.setTotalAmount(rs.getDouble("total_amount"));
        reservation.setSpecialRequests(rs.getString("special_requests"));
        reservation.setNumGuests(rs.getInt("num_guests"));
        reservation.setCreatedAt(rs.getTimestamp("created_at"));
        reservation.setUpdatedAt(rs.getTimestamp("updated_at"));
        return reservation;
    }
}