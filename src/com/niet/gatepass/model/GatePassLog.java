package com.niet.gatepass.model;

import java.sql.Timestamp;

public class GatePassLog {
    private int logId;
    private int passId;
    private String action;
    private Timestamp actionTime;
    private String verifiedBy;
    private String remarks;

    public GatePassLog() {}

    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }

    public int getPassId() { return passId; }
    public void setPassId(int passId) { this.passId = passId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Timestamp getActionTime() { return actionTime; }
    public void setActionTime(Timestamp actionTime) { this.actionTime = actionTime; }

    public String getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
