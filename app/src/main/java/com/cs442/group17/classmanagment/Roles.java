package com.cs442.group17.classmanagment;

public class Roles {

    private int _rid;
    private int _roleid;
    private String _rolename;

    public void set_roleid(int _roleid) {
        this._roleid = _roleid;
    }

    public void set_rolename(String _rolename) {
        this._rolename = _rolename;
    }

    public int get_roleid() {
        return _roleid;
    }

    public String get_rolename() {
        return _rolename;
    }

    public int get_rid() {
        return _rid;
    }
}
