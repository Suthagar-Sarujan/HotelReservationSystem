-- noinspection SqlNoDataSourceInspectionForFile
-- noinspection SqlDialectInspectionForFile

CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    role ENUM('RECEPTION', 'MANAGER', 'ADMIN') DEFAULT 'RECEPTION',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS reservations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    reservation_no VARCHAR(20) UNIQUE NOT NULL,
    guest_name VARCHAR(100) NOT NULL,
    address TEXT,
    contact_no VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    room_type ENUM('SINGLE', 'DOUBLE', 'TWIN', 'SUITE', 'DELUXE') NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    num_nights INT,
    total_amount DECIMAL(10,2),
    special_requests TEXT,
    num_guests INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_reservation_no (reservation_no),
    INDEX idx_guest_name (guest_name),
    INDEX idx_dates (check_in_date, check_out_date)
);

CREATE TABLE IF NOT EXISTS payments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    reservation_id INT NOT NULL,
    payment_no VARCHAR(20) UNIQUE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATE NOT NULL,
    method ENUM('CASH', 'CARD', 'BANK_TRANSFER') NOT NULL,
    status ENUM('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE,
    INDEX idx_payment_no (payment_no)
);

CREATE TABLE IF NOT EXISTS login_logs (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users (username, password, email, role)
VALUES
('admin', '$2a$10$YourHashedPasswordHere', 'admin@hotel.com', 'ADMIN'),
('manager', '$2a$10$YourHashedPasswordHere', 'manager@hotel.com', 'MANAGER'),
('reception', '$2a$10$YourHashedPasswordHere', 'reception@hotel.com', 'RECEPTION')
ON DUPLICATE KEY UPDATE
    password = VALUES(password),
    email = VALUES(email),
    role = VALUES(role);

INSERT INTO reservations (
    reservation_no, guest_name, address, contact_no, email,
    room_type, check_in_date, check_out_date, num_nights, total_amount, num_guests
)
VALUES
(
    'RES_DEMO_001',
    'John Doe', '123 Main St, City', '1234567890', 'john@email.com',
    'DELUXE', DATE_ADD(CURDATE(), INTERVAL 5 DAY), DATE_ADD(CURDATE(), INTERVAL 10 DAY), 5, 1750.00, 2
),
(
    'RES_DEMO_002',
    'Jane Smith', '456 Oak Ave, Town', '0987654321', 'jane@email.com',
    'SUITE', DATE_ADD(CURDATE(), INTERVAL 2 DAY), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 5, 1250.00, 3
)
ON DUPLICATE KEY UPDATE
    guest_name = VALUES(guest_name),
    address = VALUES(address),
    contact_no = VALUES(contact_no),
    email = VALUES(email),
    room_type = VALUES(room_type),
    check_in_date = VALUES(check_in_date),
    check_out_date = VALUES(check_out_date),
    num_nights = VALUES(num_nights),
    total_amount = VALUES(total_amount),
    num_guests = VALUES(num_guests);
