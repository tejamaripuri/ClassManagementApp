package com.cs442.group17.classmanagment;


public class Users {

    private int _uid;
    private int _roleid;
    private String _username;
    private String _password;
    private  String _name;

    public Users()
    {

    }


    public Users(int _roleid, String _username, String _password, String _name) {
        this._roleid  = _roleid;
        this._username = _username;
        this._password = _password;
        this._name = _name;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public void set_uid(int _uid) {
        this._uid = _uid;
    }

    public int get_uid() {
        return _uid;
    }

    public String get_username() {
        return _username;
    }

    public String get_password() {
        return _password;
    }

    public String get_name() {
        return _name;
    }

    public int get_roleid() {
        return _roleid;
    }


}
