package com.cs442.group17.classmanagment;

public class Students {

    private int _sid;
    private int _userid;
    private int _collegeid;
    private String _studentid;
    private String _subjectids;
    private  int _leaves;
    private  int _tdays;

    public Students(){

    }

    public void set_collegeid(int _collegeid) {
        this._collegeid = _collegeid;
    }

    public void set_leaves(int _leaves) {
        this._leaves = _leaves;
    }

    public void set_studentid(String _studentid) {
        this._studentid = _studentid;
    }

    public void set_subjectids(String _subjectids) {
        this._subjectids = _subjectids;
    }

    public void set_tdays(int _tdays) {
        this._tdays = _tdays;
    }

    public void set_userid(int _userid) {
        this._userid = _userid;
    }

    public int get_collegeid() {
        return _collegeid;
    }

    public int get_leaves() {
        return _leaves;
    }

    public int get_sid() {
        return _sid;
    }

    public String get_studentid() {
        return _studentid;
    }

    public String get_subjectids() {
        return _subjectids;
    }

    public int get_tdays() {
        return _tdays;
    }

    public int get_userid() {
        return _userid;
    }
}
