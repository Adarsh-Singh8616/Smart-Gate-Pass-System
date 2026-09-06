package com.niet.gatepass.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class GatePass {
    private int passId;
    private String passCode;
    private int studentId;
    private String studentName;
    private String rollNo;
    private String hostelName;
    private String roomNo;
    private String destination;
    private String reason;
    private Date outDate;
    private Time outTime;
    private Timestamp expectedReturn;
    private String emergencyContact;
    private String status;
    private String wardenRemarks;
    private String chiefWardenRemarks;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public GatePass() {}

    public int getPassId() { return passId; }
    public void setPassId(int passId) { this.passId = passId; }

    public String getPassCode() { return passCode; }
    public void setPassCode(String passCode) { this.passCode = passCode; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getHostelName() { return hostelName; }
    public void setHostelName(String hostelName) { this.hostelName = hostelName; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Date getOutDate() { return outDate; }
    public void setOutDate(Date outDate) { this.outDate = outDate; }

    public Time getOutTime() { return outTime; }
    public void setOutTime(Time outTime) { this.outTime = outTime; }

    public Timestamp getExpectedReturn() { return expectedReturn; }
    public void setExpectedReturn(Timestamp expectedReturn) { this.expectedReturn = expectedReturn; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getWardenRemarks() { return wardenRemarks; }
    public void setWardenRemarks(String wardenRemarks) { this.wardenRemarks = wardenRemarks; }

    public String getChiefWardenRemarks() { return chiefWardenRemarks; }
    public void setChiefWardenRemarks(String chiefWardenRemarks) { this.chiefWardenRemarks = chiefWardenRemarks; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
