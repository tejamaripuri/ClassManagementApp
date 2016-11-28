package com.cs442.group17.classmanagment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "classmanagment.db";

    //Users Table
    private static final String TABLE_USERS = "users";
    private static final String USERS_COLUMN_ID = "_uid";
    private static final String USERS_COLUMN_RoleID = "_roleid";
    private static final String USERS_COLUMN_USERNAME = "_username";
    private static final String USERS_COLUMN_PASSWORD = "_password";
    private static final String USERS_COLUMN_NAME = "_name";
    private static final String USERS_COLUMN_IsActive = "_isactive";

    //Students Table
    private static final String TABLE_STUDENTS = "students";
    private static final String STUDENT_COLUMN_ID = "_sid";
    private static final String STUDENT_COLUMN_UserID = "_userid";
    private static final String STUDENT_COLUMN_CollegeID = "_collegeid";
    private static final String STUDENT_COLUMN_StudentID = "_studentid";
    private static final String STUDENT_COLUMN_SubjectIDs = "_subjectids";
    //private static final String STUDENT_COLUMN_GROUPID = "_groupid";
    private static final String STUDENT_COLUMN_Leaves = "_leaves";
    private static final String STUDENT_COLUMN_TotalDays = "_tdays";

    //Faculty Table
    private static final String TABLE_FACULTY = "faculty";
    private static final String FACULTY_COLUMN_ID = "_fid";
    private static final String FACULTY_COLUMN_UserID = "_userid";
    private static final String FACULTY_COLUMN_FacultyID = "_facultyid";
    private static final String FACULTY_COLUMN_CollegeID = "_collegeid";
    private static final String FACULTY_COLUMN_SubjectIDs = "_subjectids";

    //Roles Table
    private static final String TABLE_ROLES = "roles";
    private static final String ROLES_COLUMN_ID = "_rid";
    private static final String ROLES_COLUMN_RoleID = "_roleid";
    private static final String ROLES_COLUMN_RoleName = "_rolename";

    //Approvals Table
    private static final String TABLE_APPROVALS = "approvals";
    private static final String APPROVALS_COLUMN_ID = "_aid";
    private static final String APPROVALS_COLUMN_ApprovalID = "_approvalid";
    private static final String APPROVALS_COLUMN_InitiatedBy = "_initiatedby";
    private static final String APPROVALS_COLUMN_ApproverID = "_approverid";
    private static final String APPROVALS_COLUMN_IsApproved = "_isapproved";
    private static final String APPROVALS_COLUMN_CreatedOn = "_createdon";
    private static final String APPROVALS_COLUMN_AutherisedOn = "_autherisedon"; // Use this for leave date.

    //Subjects Table
    private static final String TABLE_SUBJECTS = "subjects";
    private static final String SUBJECTS_COLUMN_ID = "_sid";
    private static final String SUBJECTS_COLUMN_SubjectID = "_subjectid";
    private static final String SUBJECTS_COLUMN_SubjectName = "_subjectname";

    //Notifications
    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String NOTIFICATIONS_COLUMN_ID = "_nid";
    private static final String NOTIFICATIONS_COLUMN_NotificationDetails = "_notificationdetails";
    private static final String NOTIFICATIONS_COLUMN_NotificationGeneratedBy = "_generatedby";
    private static final String NOTIFICATIONS_COLUMN_NotificationIntendedTo = "_generatedfor";
    private static final String NOTIFICATIONS_COLUMN_NotificationIsRead = "_isread";
    private static final String NOTIFICATIONS_COLUMN_NotificationGeneratedOn = "_generatedon";

    //Colleges Table
    private static final String TABLE_COLLEGES = "colleges";
    private static final String COLLEGES_COLUMN_ID = "_cid";
    private static final String COLLEGES_COLUMN_CollegeID = "_collegeid";
    private static final String COLLEGES_COLUMN_CollegeName = "_collegename";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ArrayList<String> query = new ArrayList<>();

        //Users Table Creation
        query.add("CREATE TABLE " + TABLE_USERS + "(" +
                USERS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERS_COLUMN_RoleID + " INTEGER, " +
                USERS_COLUMN_USERNAME + " TEXT, " +
                USERS_COLUMN_PASSWORD + " TEXT, " +
                USERS_COLUMN_NAME + " TEXT, " +
                USERS_COLUMN_IsActive + " INTEGER " +
                ");");

        //Student Table Creation
        query.add("CREATE TABLE " + TABLE_STUDENTS + "(" +
                STUDENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                STUDENT_COLUMN_UserID + " INTEGER, " +
                STUDENT_COLUMN_CollegeID + " INTEGER, " +
                STUDENT_COLUMN_StudentID + " TEXT, " +
                STUDENT_COLUMN_SubjectIDs + " TEXT, " +
                STUDENT_COLUMN_Leaves + " INTEGER, " +
                STUDENT_COLUMN_TotalDays + " INTEGER " +
                ");");

        //Faculty Table Creation
        query.add("CREATE TABLE " + TABLE_FACULTY + "(" +
                FACULTY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FACULTY_COLUMN_UserID + " INTEGER, " +
                FACULTY_COLUMN_FacultyID + " TEXT, " +
                FACULTY_COLUMN_CollegeID + " INTEGER, " +
                FACULTY_COLUMN_SubjectIDs + " TEXT " +
                ");");

        //Roles Table Creation
        query.add("CREATE TABLE " + TABLE_ROLES + "(" +
                ROLES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ROLES_COLUMN_RoleID + " INTEGER, " +
                ROLES_COLUMN_RoleName + " TEXT " +
                ");");

        //Approvals Table Creation
        query.add("CREATE TABLE " + TABLE_APPROVALS + "(" +
                APPROVALS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                APPROVALS_COLUMN_ApprovalID + " INTEGER, " +
                APPROVALS_COLUMN_InitiatedBy + " INTEGER, " +
                APPROVALS_COLUMN_ApproverID + " INTEGER, " +
                APPROVALS_COLUMN_IsApproved + " INTEGER, " +
                APPROVALS_COLUMN_CreatedOn + " TEXT, " +
                APPROVALS_COLUMN_AutherisedOn + " TEXT " +
                ");");

        //Subjects Table Creation
        query.add("CREATE TABLE " + TABLE_SUBJECTS + "(" +
                SUBJECTS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SUBJECTS_COLUMN_SubjectID + " INTEGER, " +
                SUBJECTS_COLUMN_SubjectName + " TEXT " +
                ");");

        //Notifications Table Creation
        query.add("CREATE TABLE " + TABLE_NOTIFICATIONS + "(" +
                NOTIFICATIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTIFICATIONS_COLUMN_NotificationDetails + " TEXT, " +
                NOTIFICATIONS_COLUMN_NotificationGeneratedBy + " INTEGER, " +
                NOTIFICATIONS_COLUMN_NotificationIntendedTo + " INTEGER, " +
                NOTIFICATIONS_COLUMN_NotificationIsRead + " INTEGER, " +
                NOTIFICATIONS_COLUMN_NotificationGeneratedOn + " TEXT " +
                ");");

        //Colleges Table Creation
        query.add("CREATE TABLE " + TABLE_COLLEGES + "(" +
                COLLEGES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLLEGES_COLUMN_CollegeID + " INTEGER, " +
                COLLEGES_COLUMN_CollegeName + " TEXT " +
                ");");

        for (int i = 0; i < query.size(); i++) {
            String value = query.get(i).toString();
            db.execSQL(value);
        }

        insertDummyData(db);
        //copyDBToSDCard();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    //Add User at Register
    public int addUser(Users user, int cId){
        int userId = 0;
        SQLiteDatabase db = getWritableDatabase();
        try
        {
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                    USERS_COLUMN_USERNAME + " LIKE \"" + user.get_username().toString() + "\";";
            Cursor c = db.rawQuery(query, null);
            if(c.getCount() != 0)
            {
                int isActive = 0;
                String query1 = "SELECT " + USERS_COLUMN_IsActive + ", " + USERS_COLUMN_ID + " FROM " + TABLE_USERS + " WHERE " + USERS_COLUMN_USERNAME + " LIKE \"" + user.get_username().toString() + "\";";
                Cursor  cursor = db.rawQuery(query1,null);
                if (cursor.moveToFirst()) {
                    while (cursor.isAfterLast() != true) {
                        isActive = Integer.parseInt(cursor.getString(cursor.getColumnIndex(USERS_COLUMN_IsActive)));
                        userId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(USERS_COLUMN_ID)));
                        break;
                    }
                }
                if(isActive == 0)
                {
                    int collId = getCollegeIdByUserId(userId);
                    if(collId == cId)
                    {
                        if(user.get_roleid() == getRoleByUserId(userId))
                        {
                            db.execSQL("UPDATE " + TABLE_USERS + " SET " + USERS_COLUMN_IsActive + " = 1, " + USERS_COLUMN_NAME + " = \"" + user.get_name() + "\", " + USERS_COLUMN_PASSWORD + " = \"" + user.get_password() + "\" WHERE " + USERS_COLUMN_USERNAME +  " LIKE \"" + user.get_username().toString() + "\";");
                            db.close();
                            return  1;
                        }
                        else
                        {
                            db.close();
                            return  4;
                        }

                    }
                    else
                    {
                        db.close();
                        return  3;
                    }
                }
                else
                {
                    db.close();
                    return 2;
                }
            }
            else
            {
                db.close();
                return 9;
            }
        }
        catch (Exception e)
        {
            return -99;
        }
        finally {
            db.close();
        }

    }

    //Check User Credentials
    public boolean checkLogin(String username, String password, int collid)
    {
        Boolean auth = false;
        int userId = 0;
        int count = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+ USERS_COLUMN_ID +" FROM " + TABLE_USERS + " WHERE " +
                USERS_COLUMN_USERNAME + " LIKE \"" + username + "\" AND " +
                USERS_COLUMN_PASSWORD + " =\"" + password + "\" AND " + USERS_COLUMN_IsActive + "= 1;";
        try
        {
            Cursor c = db.rawQuery(query, null);
            if(c.getCount() != 0)
            {
                if (c.moveToFirst()) {
                    while (c.isAfterLast() != true) {
                        userId =  Integer.parseInt(c.getString(c.getColumnIndex(USERS_COLUMN_ID)));
                        break;
                    }
                }
                String queryStu = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + STUDENT_COLUMN_UserID + " = " + userId + " AND " + STUDENT_COLUMN_CollegeID + " = " + collid;
                String queryFac = "SELECT * FROM " + TABLE_FACULTY + " WHERE " + FACULTY_COLUMN_UserID + " = " + userId + " AND " + FACULTY_COLUMN_CollegeID + " = " + collid ;
                Cursor c1 = db.rawQuery(queryStu, null);
                count = c1.getCount();
                if(count == 0)
                {
                    c1 = db.rawQuery(queryFac, null);
                    count = count + c1.getCount();
                }
                if(count != 0)
                {
                    auth = true;
                }
            }
        }
        catch (Exception e)
        {

        }
        finally {
            db.close();
            return auth;
        }
    }

    public void insertDummyData(SQLiteDatabase db)
    {
        ArrayList<String> query = new ArrayList<>();

        //User Table Dummy Values
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student1@gmail.com\", \"1234\", \"student1\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student2@gmail.com\", \"1234\", \"student2\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student3@gmail.com\", \"1234\", \"student3\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student4@gmail.com\", \"1234\", \"student4\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student5@gmail.com\", \"1234\", \"student5\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student6@gmail.com\", \"1234\", \"student6\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student7@gmail.com\", \"1234\", \"student7\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student8@gmail.com\", \"1234\", \"student8\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student9@gmail.com\", \"1234\", \"student9\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student10@gmail.com\", \"1234\", \"student10\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student11@gmail.com\", \"1234\", \"student11\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student12@gmail.com\", \"1234\", \"student12\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student13@gmail.com\", \"1234\", \"student13\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student14@gmail.com\", \"1234\", \"student14\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student15@gmail.com\", \"1234\", \"student15\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student16@gmail.com\", \"1234\", \"student16\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student17@gmail.com\", \"1234\", \"student17\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student18@gmail.com\", \"1234\", \"student18\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student19@gmail.com\", \"1234\", \"student19\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(1, \"student20@gmail.com\", \"1234\", \"student20\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(2, \"faculty1@gmail.com\", \"1234\", \"faculty1\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(2, \"faculty2@gmail.com\", \"1234\", \"faculty2\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(2, \"faculty3@gmail.com\", \"1234\", \"faculty3\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(2, \"faculty4@gmail.com\", \"1234\", \"faculty4\",0)");
        query.add("INSERT INTO " + TABLE_USERS + " ("+ USERS_COLUMN_RoleID + ", " + USERS_COLUMN_USERNAME + ", " + USERS_COLUMN_PASSWORD + ", " + USERS_COLUMN_NAME + ", " + USERS_COLUMN_IsActive + ") VALUES(2, \"faculty5@gmail.com\", \"1234\", \"faculty5\",0)");

        //Student Table Dummy Values
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(1, 1, \"A0001\", \"1,2,3\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(2, 1, \"A0002\", \"1,2,4\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(3, 1, \"A0003\", \"1,2,5\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(4, 1, \"A0004\", \"1,3,2\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(5, 1, \"A0005\", \"1,3,4\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(6, 1, \"A0006\", \"1,3,5\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(7, 1, \"A0007\", \"1,4,2\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(8, 1, \"A0008\", \"1,4,3\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(9, 1, \"A0009\", \"1,4,5\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(10, 1, \"A0010\", \"1,5,2\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(11, 1, \"A0011\", \"1,5,3\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(12, 1, \"A0012\", \"1,5,4\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(13, 1, \"A0013\", \"2,1,5\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(14, 1, \"A0014\", \"2,1,3\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(15, 1, \"A0015\", \"2,1,4\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(16, 1, \"A0016\", \"2,3,5\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(17, 1, \"A0017\", \"2,3,1\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(18, 1, \"A0018\", \"2,3,4\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(19, 1, \"A0019\", \"2,4,5\", 0 , 100)");
        query.add("INSERT INTO " + TABLE_STUDENTS + " ("+ STUDENT_COLUMN_UserID + ", " + STUDENT_COLUMN_CollegeID + ", " + STUDENT_COLUMN_StudentID + ", " + STUDENT_COLUMN_SubjectIDs + ", " + STUDENT_COLUMN_Leaves + ", " + STUDENT_COLUMN_TotalDays + ") VALUES(20, 1, \"A0020\", \"2,4,1\", 0 , 100)");

        //Faculty Table Dummy Values
        query.add("INSERT INTO " + TABLE_FACULTY + " ("+ FACULTY_COLUMN_UserID + ", " + FACULTY_COLUMN_FacultyID + ", " + FACULTY_COLUMN_CollegeID + ", " + FACULTY_COLUMN_SubjectIDs + ") VALUES(21, \"F0001\", 1, \"1\")");
        query.add("INSERT INTO " + TABLE_FACULTY + " ("+ FACULTY_COLUMN_UserID + ", " + FACULTY_COLUMN_FacultyID + ", " + FACULTY_COLUMN_CollegeID + ", " + FACULTY_COLUMN_SubjectIDs + ") VALUES(22, \"F0002\", 1, \"2\")");
        query.add("INSERT INTO " + TABLE_FACULTY + " ("+ FACULTY_COLUMN_UserID + ", " + FACULTY_COLUMN_FacultyID + ", " + FACULTY_COLUMN_CollegeID + ", " + FACULTY_COLUMN_SubjectIDs + ") VALUES(23, \"F0003\", 1, \"3\")");
        query.add("INSERT INTO " + TABLE_FACULTY + " ("+ FACULTY_COLUMN_UserID + ", " + FACULTY_COLUMN_FacultyID + ", " + FACULTY_COLUMN_CollegeID + ", " + FACULTY_COLUMN_SubjectIDs + ") VALUES(24, \"F0004\", 1, \"4\")");
        query.add("INSERT INTO " + TABLE_FACULTY + " ("+ FACULTY_COLUMN_UserID + ", " + FACULTY_COLUMN_FacultyID + ", " + FACULTY_COLUMN_CollegeID + ", " + FACULTY_COLUMN_SubjectIDs + ") VALUES(25, \"F0005\", 1, \"5\")");

        //Roles Table Dummy Values
        query.add("INSERT INTO " + TABLE_ROLES + " ("+ ROLES_COLUMN_RoleID + ", " + ROLES_COLUMN_RoleName + ") VALUES(1, \"Student\")");
        query.add("INSERT INTO " + TABLE_ROLES + " ("+ ROLES_COLUMN_RoleID + ", " + ROLES_COLUMN_RoleName + ") VALUES(2, \"Faculty\")");

        //Approvals Table Dummy Values
        query.add("INSERT INTO " + TABLE_APPROVALS + " ("+ APPROVALS_COLUMN_ApprovalID + ", " + APPROVALS_COLUMN_InitiatedBy + ", " + APPROVALS_COLUMN_ApproverID + ", " + APPROVALS_COLUMN_IsApproved + ", " + APPROVALS_COLUMN_CreatedOn + ", " + APPROVALS_COLUMN_AutherisedOn +") VALUES(1, 1, 21, 0, \"11/4/2016\", \"11/29/2016\")");
        query.add("INSERT INTO " + TABLE_APPROVALS + " ("+ APPROVALS_COLUMN_ApprovalID + ", " + APPROVALS_COLUMN_InitiatedBy + ", " + APPROVALS_COLUMN_ApproverID + ", " + APPROVALS_COLUMN_IsApproved + ", " + APPROVALS_COLUMN_CreatedOn + ", " + APPROVALS_COLUMN_AutherisedOn +") VALUES(2, 2, 21, 0, \"11/4/2016\", \"12/29/2016\")");
        query.add("INSERT INTO " + TABLE_APPROVALS + " ("+ APPROVALS_COLUMN_ApprovalID + ", " + APPROVALS_COLUMN_InitiatedBy + ", " + APPROVALS_COLUMN_ApproverID + ", " + APPROVALS_COLUMN_IsApproved + ", " + APPROVALS_COLUMN_CreatedOn + ", " + APPROVALS_COLUMN_AutherisedOn +") VALUES(3, 2, 22, 0, \"11/4/2016\", \"11/20/2016\")");

        //Subjects Table Dummy Values
        query.add("INSERT INTO " + TABLE_SUBJECTS + " ("+ SUBJECTS_COLUMN_SubjectID + ", " + SUBJECTS_COLUMN_SubjectName + ") VALUES(1, \"Mobile Application Development\")");
        query.add("INSERT INTO " + TABLE_SUBJECTS + " ("+ SUBJECTS_COLUMN_SubjectID + ", " + SUBJECTS_COLUMN_SubjectName + ") VALUES(2, \"Software Testing and Analysis\")");
        query.add("INSERT INTO " + TABLE_SUBJECTS + " ("+ SUBJECTS_COLUMN_SubjectID + ", " + SUBJECTS_COLUMN_SubjectName + ") VALUES(3, \"Data mining\")");
        query.add("INSERT INTO " + TABLE_SUBJECTS + " ("+ SUBJECTS_COLUMN_SubjectID + ", " + SUBJECTS_COLUMN_SubjectName + ") VALUES(4, \"Advanced Data Mining\")");
        query.add("INSERT INTO " + TABLE_SUBJECTS + " ("+ SUBJECTS_COLUMN_SubjectID + ", " + SUBJECTS_COLUMN_SubjectName + ") VALUES(5, \"Machine Learning\")");

        //Notifications Table Dummy Values
        query.add("INSERT INTO " + TABLE_NOTIFICATIONS + " ("+ NOTIFICATIONS_COLUMN_NotificationDetails + ", " + NOTIFICATIONS_COLUMN_NotificationGeneratedBy + ", " + NOTIFICATIONS_COLUMN_NotificationIntendedTo + ", " + NOTIFICATIONS_COLUMN_NotificationIsRead + ", " + NOTIFICATIONS_COLUMN_NotificationGeneratedOn + ") VALUES(\"Leave was requested by student1 on 11/4/2016\", 1, 21, 0, \"11/4/2016\")");
        query.add("INSERT INTO " + TABLE_NOTIFICATIONS + " ("+ NOTIFICATIONS_COLUMN_NotificationDetails + ", " + NOTIFICATIONS_COLUMN_NotificationGeneratedBy + ", " + NOTIFICATIONS_COLUMN_NotificationIntendedTo + ", " + NOTIFICATIONS_COLUMN_NotificationIsRead + ", " + NOTIFICATIONS_COLUMN_NotificationGeneratedOn + ") VALUES(\"Leave was requested by student2 on 11/4/2016\", 2, 21, 0, \"11/4/2016\")");
        query.add("INSERT INTO " + TABLE_NOTIFICATIONS + " ("+ NOTIFICATIONS_COLUMN_NotificationDetails + ", " + NOTIFICATIONS_COLUMN_NotificationGeneratedBy + ", " + NOTIFICATIONS_COLUMN_NotificationIntendedTo + ", " + NOTIFICATIONS_COLUMN_NotificationIsRead + ", " + NOTIFICATIONS_COLUMN_NotificationGeneratedOn + ") VALUES(\"Leave was requested by student2 on 11/4/2016\", 2, 22, 0, \"11/4/2016\")");

        //Colleges Table Dummy Values
        query.add("INSERT INTO " + TABLE_COLLEGES + " ("+ COLLEGES_COLUMN_CollegeID + ", " + COLLEGES_COLUMN_CollegeName + ") VALUES(1, \"IIT - Illinois Institute of Technology\")");
        query.add("INSERT INTO " + TABLE_COLLEGES + " ("+ COLLEGES_COLUMN_CollegeID + ", " + COLLEGES_COLUMN_CollegeName + ") VALUES(2, \"MIT - Massachusetts Institute of Technology\")");
        query.add("INSERT INTO " + TABLE_COLLEGES + " ("+ COLLEGES_COLUMN_CollegeID + ", " + COLLEGES_COLUMN_CollegeName + ") VALUES(3, \"UIC - University of Illinois at Chicago\")");

        for (int i = 0; i < query.size(); i++) {
            String value = query.get(i).toString();
            db.execSQL(value);
        }
    }

    public String getNameById(int userId)
    {
        String userName = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + USERS_COLUMN_NAME + " FROM " + TABLE_USERS + " WHERE " + USERS_COLUMN_ID + " = " + userId ;
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                userName =  cursor.getString(cursor.getColumnIndex(USERS_COLUMN_NAME));
                break;
            }
        }
        return userName;
    }

    public int getUserIdByEmail(String email)
    {
        int userId = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + USERS_COLUMN_ID + " FROM " + TABLE_USERS + " WHERE " + USERS_COLUMN_USERNAME + " = \"" + email + "\"";
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                userId =  Integer.parseInt(cursor.getString(cursor.getColumnIndex(USERS_COLUMN_ID)));
                break;
            }
        }
        return userId;
    }

    public ArrayList<String> getRosterBySubjectID(int subjectId) {

        String name = "";
        String query = "";
        ArrayList<String> names = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        query = "SELECT " + USERS_COLUMN_NAME + " FROM " + TABLE_USERS + " WHERE " + USERS_COLUMN_ID + " IN (SELECT " + STUDENT_COLUMN_UserID + " FROM " + TABLE_STUDENTS +  " WHERE " + STUDENT_COLUMN_SubjectIDs + " LIKE \"%" + subjectId + "%\")";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                name = c.getString(c.getColumnIndex(USERS_COLUMN_NAME));
                names.add(name);
            } while (c.moveToNext());
        }

        return names;
    }

    public int getSubjectIdBySubjectName(String subjectName)
    {
        int subjectId = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + SUBJECTS_COLUMN_SubjectID + " FROM " + TABLE_SUBJECTS + " WHERE " + SUBJECTS_COLUMN_SubjectName + " = \"" + subjectName + "\"";
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                subjectId =  Integer.parseInt(cursor.getString(cursor.getColumnIndex(SUBJECTS_COLUMN_SubjectID)));
                break;
            }
        }
        return subjectId;
    }

    public String getSubjectNameBySubjectId(int subjectId)
    {
        String subjectName = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + SUBJECTS_COLUMN_SubjectName + " FROM " + TABLE_SUBJECTS + " WHERE " + SUBJECTS_COLUMN_SubjectID + " = " + subjectId ;
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                subjectName =  cursor.getString(cursor.getColumnIndex(SUBJECTS_COLUMN_SubjectName));
                break;
            }
        }
        return subjectName;
    }

    public int getRoleByUserId(int userId)
    {
        int roleId = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + USERS_COLUMN_RoleID + " FROM " + TABLE_USERS + " WHERE " + USERS_COLUMN_ID + " = " + userId ;
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                roleId =  Integer.parseInt(cursor.getString(cursor.getColumnIndex(USERS_COLUMN_RoleID)));
                break;
            }
        }
        return roleId;
    }

    public ArrayList<String> getSubjectsByUserIdRoleId(int userId, int roleId)
    {
        String subjects = "";
        String query = "";
        ArrayList<String> subs =new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        if(roleId == 1)
        {
            query = "SELECT " + STUDENT_COLUMN_SubjectIDs + " FROM " + TABLE_STUDENTS + " WHERE " + STUDENT_COLUMN_UserID + " = " + userId ;
            Cursor  cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() != true) {
                    subjects =  cursor.getString(cursor.getColumnIndex(STUDENT_COLUMN_SubjectIDs));
                    break;
                }
            }
        }
        else if(roleId == 2)
        {
            query = "SELECT " + FACULTY_COLUMN_SubjectIDs + " FROM " + TABLE_FACULTY + " WHERE " + FACULTY_COLUMN_UserID + " = " + userId ;
            Cursor  cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() != true) {
                    subjects =  cursor.getString(cursor.getColumnIndex(FACULTY_COLUMN_SubjectIDs));
                    break;
                }
            }
        }

        if(!subjects.equals(""))
        for (String s: subjects.split(",")) {
            String name = getSubjectNameBySubjectId(Integer.parseInt(s));
            subs.add(name);
        }

        return subs;
    }

    public void ApplyLeave(int userId, String date, int approverId)
    {
        int maxApprovalId = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currdate = new Date();
        String datestring = dateFormat.format(currdate);
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT MAX(" + APPROVALS_COLUMN_ApproverID + ") AS MAXI FROM " + TABLE_APPROVALS;
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                maxApprovalId =  Integer.parseInt(cursor.getString(cursor.getColumnIndex("MAXI")));
                break;
            }
        }
        query = "INSERT INTO " + TABLE_APPROVALS + " ("+ APPROVALS_COLUMN_ApprovalID + ", " + APPROVALS_COLUMN_InitiatedBy + ", " + APPROVALS_COLUMN_ApproverID + ", " + APPROVALS_COLUMN_IsApproved + ", " + APPROVALS_COLUMN_CreatedOn + ", " + APPROVALS_COLUMN_AutherisedOn +") VALUES("+ (maxApprovalId + 1)+", "+ userId +", "+ approverId + ", 0, \""+datestring+"\", \""+ date +"\")";
        db.execSQL(query);
        query = "INSERT INTO " + TABLE_NOTIFICATIONS + " ("+ NOTIFICATIONS_COLUMN_NotificationDetails + ", " + NOTIFICATIONS_COLUMN_NotificationGeneratedBy + ", " + NOTIFICATIONS_COLUMN_NotificationIntendedTo + ", " + NOTIFICATIONS_COLUMN_NotificationIsRead + ", " + NOTIFICATIONS_COLUMN_NotificationGeneratedOn + ") VALUES(\"Leave Application\", " + userId + ", " + approverId + ", 0, \""+datestring+"\")";
        db.execSQL(query);
        db.close();
    }

    public int getApproverBySubjectId(int subjectId)
    {
        int approverId = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + FACULTY_COLUMN_UserID + " FROM " + TABLE_FACULTY + " WHERE " + FACULTY_COLUMN_SubjectIDs + " = \"" + subjectId +"\"";
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                approverId =  Integer.parseInt(cursor.getString(cursor.getColumnIndex(FACULTY_COLUMN_UserID)));
                break;
            }
        }
        return approverId;
    }

    public ArrayList<String> getApprovals(int approverId) {

        String name = "";
        String query = "";
        ArrayList<String> names = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        query = "SELECT " + APPROVALS_COLUMN_InitiatedBy + ", "+ APPROVALS_COLUMN_AutherisedOn + " FROM " + TABLE_APPROVALS + " WHERE " + APPROVALS_COLUMN_ApproverID + " = " + approverId;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                int nameId = Integer.parseInt(c.getString(c.getColumnIndex(APPROVALS_COLUMN_InitiatedBy)));
                String date =  c.getString(c.getColumnIndex(APPROVALS_COLUMN_AutherisedOn));
                name = getNameById(nameId);
                name = name + " for " + date;
                names.add(name);
            } while (c.moveToNext());
        }

        return names;
    }

    public ArrayList<String> getColleges() {

        String name = "";
        String query = "";
        ArrayList<String> names = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        query = "SELECT " + COLLEGES_COLUMN_CollegeName + " FROM " + TABLE_COLLEGES ;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                name =  c.getString(c.getColumnIndex(COLLEGES_COLUMN_CollegeName));
                names.add(name);
            } while (c.moveToNext());
        }

        return names;
    }

    public int getCollegeIdByCollegeName(String collegeName)
    {
        int collegeId = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLLEGES_COLUMN_CollegeID + " FROM " + TABLE_COLLEGES + " WHERE " + COLLEGES_COLUMN_CollegeName + " = \"" + collegeName + "\"";
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                collegeId =  Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLLEGES_COLUMN_CollegeID)));
                break;
            }
        }
        return collegeId;
    }

    public int getCollegeIdByUserId(int userId)
    {
        int roleId = getRoleByUserId(userId);
        String query = "";
        int collegeId = 0;
        SQLiteDatabase db = getWritableDatabase();
        String arg = "";
        if(roleId == 1) {
            query = "SELECT " + STUDENT_COLUMN_CollegeID + " FROM " + TABLE_STUDENTS + " WHERE " + STUDENT_COLUMN_UserID + " = " + userId;
            arg = STUDENT_COLUMN_CollegeID;
        }
        else if (roleId == 2)
        {
            query = "SELECT " + FACULTY_COLUMN_CollegeID + " FROM " + TABLE_FACULTY + " WHERE " + FACULTY_COLUMN_UserID + " = " + userId;
            arg = FACULTY_COLUMN_CollegeID;
        }
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                collegeId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(arg)));
                break;
            }
        }
        return collegeId;
    }

    public ArrayList getNotificatiosFromDB(int userId)
    {
        HashMap<Integer, Notifications> hmap = new HashMap<Integer, Notifications>();
        ArrayList<Notifications> notificationCollection = new ArrayList<>();
        Notifications notiObj;

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + NOTIFICATIONS_COLUMN_NotificationDetails + ", " + NOTIFICATIONS_COLUMN_NotificationGeneratedBy + ", " + NOTIFICATIONS_COLUMN_NotificationGeneratedOn + " FROM " + TABLE_NOTIFICATIONS
                + " WHERE " + NOTIFICATIONS_COLUMN_NotificationIsRead + " = 0 AND " + NOTIFICATIONS_COLUMN_NotificationIntendedTo + " = " + userId;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                notiObj = new Notifications();
                notiObj.set_notificationdetails(c.getString(c.getColumnIndex(NOTIFICATIONS_COLUMN_NotificationDetails)));
                notiObj.set_generatedby(Integer.parseInt(c.getString(c.getColumnIndex(NOTIFICATIONS_COLUMN_NotificationGeneratedBy))));
                notiObj.set_generatedon(c.getString(c.getColumnIndex(NOTIFICATIONS_COLUMN_NotificationGeneratedOn)));

                notificationCollection.add(notiObj);
            } while (c.moveToNext());
        }

        return notificationCollection;

    }

    public void makeNotiRead(int userId)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NOTIFICATIONS + " SET " + NOTIFICATIONS_COLUMN_NotificationIsRead + " = 1 WHERE " + NOTIFICATIONS_COLUMN_NotificationIsRead + " = 0 AND " + NOTIFICATIONS_COLUMN_NotificationIntendedTo + " = " + userId;
        db.execSQL(query);
        db.close();
    }

    public void copyDBToSDCard() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.cs442.group17.classmanagment//databases//"+DATABASE_NAME+"";
                String backupDBPath = "backupcm.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }

            }

        }  catch (Exception e) {
            Log.i("FO","exception="+e);
        }


    }

}














