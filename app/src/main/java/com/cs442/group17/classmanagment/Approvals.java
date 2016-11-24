package com.cs442.group17.classmanagment;

import java.util.Date;

public class Approvals {

    private int _aid;
    private int _approvalid;
    private int _initiatedby;
    private int _approverid;
    private int _isapproved;
    private Date _createdon;
    private Date _autherisedon;

    public void set_approvalid(int _approvalid) {
        this._approvalid = _approvalid;
    }

    public void set_approverid(int _approverid) {
        this._approverid = _approverid;
    }

    public void set_autherisedon(Date _autherisedon) {
        this._autherisedon = _autherisedon;
    }

    public void set_createdon(Date _createdon) {
        this._createdon = _createdon;
    }

    public void set_initiatedby(int _initiatedby) {
        this._initiatedby = _initiatedby;
    }

    public void set_isapproved(int _isapproved) {
        this._isapproved = _isapproved;
    }

    public int get_aid() {
        return _aid;
    }

    public int get_approvalid() {
        return _approvalid;
    }

    public int get_approverid() {
        return _approverid;
    }

    public Date get_autherisedon() {
        return _autherisedon;
    }

    public Date get_createdon() {
        return _createdon;
    }

    public int get_initiatedby() {
        return _initiatedby;
    }

    public int is_isapproved() {
        return _isapproved;
    }
}
