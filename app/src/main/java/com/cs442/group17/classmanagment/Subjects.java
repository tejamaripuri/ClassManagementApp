package com.cs442.group17.classmanagment;


public class Subjects {
    private int _sid;
    private String _subjectid;
    private String _subjectname;

    public void set_subjectid(String _subjectid) {
        this._subjectid = _subjectid;
    }

    public void set_subjectname(String _subjectname) {
        this._subjectname = _subjectname;
    }

    public int get_sid() {
        return _sid;
    }

    public String get_subjectid() {
        return _subjectid;
    }

    public String get_subjectname() {
        return _subjectname;
    }
}
