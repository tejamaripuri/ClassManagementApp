package com.cs442.group17.classmanagment;


public class Faculty {

    private int _fid;
    private int _userid;
    private int _facultyid;
    private int _collegeid;
    private String _subjectids;

    public void set_collegeid(int _collegeid) {
        this._collegeid = _collegeid;
    }

    public void set_facultyid(int _facultyid) {
        this._facultyid = _facultyid;
    }

    public void set_subjectids(String _subjectids) {
        this._subjectids = _subjectids;
    }

    public void set_userid(int _userid) {
        this._userid = _userid;
    }

    public int get_collegeid() {
        return _collegeid;
    }

    public int get_facultyid() {
        return _facultyid;
    }

    public int get_fid() {
        return _fid;
    }

    public String get_subjectids() {
        return _subjectids;
    }

    public int get_userid() {
        return _userid;
    }
}
