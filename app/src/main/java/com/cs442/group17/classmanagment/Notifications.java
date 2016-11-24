package com.cs442.group17.classmanagment;


public class Notifications {

    private int _nid;
    private String _notificationdetails;
    private int _generatedby;
    private int _generatedfor;
    private int _isread;
    private String _generatedon;

    public void set_generatedby(int _generatedby) {
        this._generatedby = _generatedby;
    }

    public void set_generatedfor(int _generatedfor) {
        this._generatedfor = _generatedfor;
    }

    public void set_notificationdetails(String _notificationdetails) {
        this._notificationdetails = _notificationdetails;
    }

    public int get_generatedby() {
        return _generatedby;
    }

    public int get_generatedfor() {
        return _generatedfor;
    }

    public String get_notificationdetails() {
        return _notificationdetails;
    }

    public int get_nid() {
        return _nid;
    }

    public void set_generatedon(String _generatedon) {
        this._generatedon = _generatedon;
    }

    public void set_isread(int _isread) {
        this._isread = _isread;
    }

    public String get_generatedon() {
        return _generatedon;
    }

    public int get_isread() {
        return _isread;
    }
}
