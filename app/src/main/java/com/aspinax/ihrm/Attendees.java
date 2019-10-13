package com.aspinax.ihrm;

public class Attendees {
    private String UniqueId;
    private String Attendee;
    private String Payment_status;
    private String Sponsor_name;
    private Boolean CheckIn15;
    private Boolean CheckIn16;
    private Boolean CheckIn17;
    private Boolean CheckIn18;

    public Attendees() {}

    public Attendees(String attendee, String unique_id, String payment_status, String sponsor_name, Boolean CheckIn15, Boolean CheckIn16, Boolean CheckIn17, Boolean CheckIn18) {
        this.UniqueId = unique_id;
        this.Attendee = attendee;
        this.Payment_status = payment_status;
        this.Sponsor_name = sponsor_name;
        this.CheckIn15 = CheckIn15;
        this.CheckIn16 = CheckIn16;
        this.CheckIn17 = CheckIn17;
        this.CheckIn18 = CheckIn18;
    }

    public String getUnique_id() {
        return this.UniqueId;
    }

    public String getAttendee() {
        return this.Attendee;
    }

    public String getPayment_status() {
        return this.Payment_status;
    }


    public String getSponsor_name() {
        return this.Sponsor_name;
    }

    public Boolean getCheckIn15() { return this.CheckIn15; }
    public Boolean getCheckIn16() { return this.CheckIn16; }
    public Boolean getCheckIn17() { return this.CheckIn17; }
    public Boolean getCheckIn18() { return this.CheckIn18; }

    public void setUnique_id(String unique_id) {
        this.UniqueId = unique_id;
    }

    public void setAttendee(String name) { this.Attendee = name; }

    public void setPayment_status(String payment_status) { this.Payment_status = payment_status; }

    public void setSponsor_name(String sponsor_name) { this.Sponsor_name = sponsor_name; }

    public void setCheckIn15(Boolean CheckIn15) { this.CheckIn15 = CheckIn15; }
    public void setCheckIn16(Boolean CheckIn16) { this.CheckIn16 = CheckIn16; }
    public void setCheckIn17(Boolean CheckIn17) { this.CheckIn17 = CheckIn17; }
    public void setCheckIn18(Boolean CheckIn18) { this.CheckIn18 = CheckIn18; }
}