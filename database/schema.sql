-- ============================================================
-- Smart Gate Pass Approval System - NIET Hostel
-- Database Schema (MySQL)
-- ============================================================

CREATE DATABASE IF NOT EXISTS gatepass_db;
USE gatepass_db;

-- ---------------------------------------------------
-- HOSTELS
-- ---------------------------------------------------
CREATE TABLE hostels (
    hostel_id      INT AUTO_INCREMENT PRIMARY KEY,
    hostel_name    VARCHAR(100) NOT NULL,
    total_rooms    INT DEFAULT 0,
    hostel_fee     DECIMAL(10,2) DEFAULT 0,
    mess_fee       DECIMAL(10,2) DEFAULT 0,
    facilities     VARCHAR(255)
);

-- ---------------------------------------------------
-- WARDENS
-- ---------------------------------------------------
CREATE TABLE wardens (
    warden_id      INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    email          VARCHAR(100) UNIQUE NOT NULL,
    password_hash  VARCHAR(255) NOT NULL,
    hostel_id      INT,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id)
);

-- ---------------------------------------------------
-- CHIEF WARDENS
-- ---------------------------------------------------
CREATE TABLE chief_wardens (
    cw_id          INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    email          VARCHAR(100) UNIQUE NOT NULL,
    password_hash  VARCHAR(255) NOT NULL
);

-- ---------------------------------------------------
-- STUDENTS
-- ---------------------------------------------------
CREATE TABLE students (
    student_id        INT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    roll_no           VARCHAR(50) UNIQUE NOT NULL,
    email             VARCHAR(100) UNIQUE NOT NULL,
    password_hash     VARCHAR(255) NOT NULL,
    hostel_id         INT,
    room_no           VARCHAR(20),
    contact_no        VARCHAR(20),
    emergency_contact VARCHAR(20),
    status            ENUM('PENDING','ACTIVE','REJECTED') DEFAULT 'PENDING',
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id)
);

-- ---------------------------------------------------
-- GATE PASS
-- ---------------------------------------------------
CREATE TABLE gate_pass (
    pass_id            INT AUTO_INCREMENT PRIMARY KEY,
    pass_code          VARCHAR(64) UNIQUE NOT NULL,
    student_id         INT NOT NULL,
    destination        VARCHAR(255) NOT NULL,
    reason             VARCHAR(500) NOT NULL,
    out_date           DATE NOT NULL,
    out_time           TIME NOT NULL,
    expected_return    DATETIME NOT NULL,
    emergency_contact  VARCHAR(20),
    status             ENUM(
                          'PENDING',
                          'WARDEN_APPROVED',
                          'WARDEN_REJECTED',
                          'CHIEF_WARDEN_APPROVED',
                          'CHIEF_WARDEN_REJECTED',
                          'ACTIVE',
                          'EXIT_RECORDED',
                          'RETURN_RECORDED',
                          'COMPLETED'
                        ) DEFAULT 'PENDING',
    warden_remarks     VARCHAR(500),
    chief_warden_remarks VARCHAR(500),
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);

-- ---------------------------------------------------
-- GATE PASS LOGS  (exit / return events at the gate)
-- ---------------------------------------------------
CREATE TABLE gate_pass_logs (
    log_id       INT AUTO_INCREMENT PRIMARY KEY,
    pass_id      INT NOT NULL,
    action       ENUM('EXIT','RETURN') NOT NULL,
    action_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    verified_by  VARCHAR(100),
    remarks      VARCHAR(255),
    FOREIGN KEY (pass_id) REFERENCES gate_pass(pass_id)
);

-- ---------------------------------------------------
-- LOGIN ATTEMPTS (optional audit log)
-- ---------------------------------------------------
CREATE TABLE login_attempts (
    attempt_id   INT AUTO_INCREMENT PRIMARY KEY,
    user_email   VARCHAR(100),
    user_role    VARCHAR(20),
    success      BOOLEAN,
    attempt_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ---------------------------------------------------
-- SEED DATA
-- ---------------------------------------------------
INSERT INTO hostels (hostel_name, total_rooms, hostel_fee, mess_fee, facilities) VALUES
('Hostel 1', 100, 25000.00, 18000.00, 'Wi-Fi, Mess, Security, CCTV'),
('Hostel 2', 120, 27000.00, 18000.00, 'Wi-Fi, Mess, Security, CCTV, Sports'),
('Hostel 3', 80,  30000.00, 20000.00, 'Wi-Fi, Mess, Security, CCTV, Sports, Medical Facility');

-- default password for all seed accounts is: admin123
-- (hash below is SHA-256 of "admin123" - matches PasswordUtil.hash() in the app)
INSERT INTO chief_wardens (name, email, password_hash) VALUES
('Dr. R. K. Sharma', 'chiefwarden@niet.co.in', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9');

INSERT INTO wardens (name, email, password_hash, hostel_id) VALUES
('Mr. A. Verma', 'warden1@niet.co.in', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 1),
('Mr. S. Gupta', 'warden2@niet.co.in', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 2),
('Ms. P. Singh', 'warden3@niet.co.in', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 3);
