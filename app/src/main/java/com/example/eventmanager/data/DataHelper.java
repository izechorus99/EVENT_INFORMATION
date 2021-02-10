package com.example.eventmanager.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DataHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "information.db";
    public static final String TABLE_EVENT = "TblEvent";
    public static final String EVENT_ID = "eventID";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String VENUE = "venue";
    public static final String VENUE_TYPE = "venueType";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String TOTAL_GUEST = "totalGuest";
    public static final String FOOD_ITEM = "foodItem";
    public static final String FOOD_COST = "foodCost";
    public static final String VENUE_COST = "venueCost";
    public static final String TOTAL_COST = "totalCost";
    private static final String TAG = "FUCK";

    Context context;

    public DataHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 9);
        SQLiteDatabase db = this.getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(("CREATE TABLE " + TABLE_EVENT +
                "(" +EVENT_ID+" INTEGER PRIMARY KEY," +
                CUSTOMER_NAME + " TEXT," +
                VENUE + " TEXT, " +
                VENUE_TYPE + " TEXT," +
                START_DATE + " TEXT, " +
                END_DATE + " TEXT, " +
                START_TIME + " TEXT," +
                END_TIME + " TEXT," +
                TOTAL_GUEST + " INTEGER, " +
                FOOD_ITEM + " TEXT, " +
                FOOD_COST + " TEXT, " +
                VENUE_COST + " TEXT, " +
                TOTAL_COST + " TEXT" +
                ")"));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_EVENT);
        onCreate(db);

    }

    //=================Methods for insert
    public boolean insertData(String customerName,
                              String venue,
                              String venueType,
                              String startDate,
                              String endDate,
                              String startTime,
                              String endTime,
                              int totalGuest,
                              String foodItem,
                              String foodCost,
                              String venueCost,
                              String totalCost
                              ) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CUSTOMER_NAME, customerName);
            cv.put(VENUE, venue);
            cv.put(VENUE_TYPE, venueType);
            cv.put(START_DATE, startDate);
            cv.put(END_DATE, endDate);
            cv.put(START_TIME, startTime);
            cv.put(END_TIME, endTime);
            cv.put(TOTAL_GUEST, totalGuest);
            cv.put(FOOD_ITEM, foodItem);
            cv.put(FOOD_COST, foodCost);
            cv.put(VENUE_COST, venueCost);
            cv.put(TOTAL_COST, totalCost);
            long insert = db.insert(TABLE_EVENT, null, cv);
            if (insert == -1) {
                return false;
            } else {
                return true;
            }
        }
        catch(Exception ex){
                Log.d("hays", ex.toString());
            return false;
        }
    }


    //==================Methods for validity / if date,time and venue exists in database when inserting
    private boolean isVenueValid(String venue){
        boolean result = false;
        String sql = "SELECT * FROM TblEvent WHERE venue = '" + venue + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext()){
            if(cursor.getCount() != 0 ){
                return false; //means you have data
            }
            else{
                return true; //means no event using this venue
            }
        }
        else{
            return true; //means no event using this venue
        }
    }



    private Cursor getEventDateTimes(String venue, String startTime, String startDate){
        String sql = "SELECT * FROM TblEvent WHERE venue = '" + venue + "' " +
                "AND startTime = '"
                + startTime +
                "' AND startDate = '"
                + startDate + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }


    private boolean isDateTimeValid(String venue, String startDate, String startTime, String endDate, String endTime)  {
        Cursor dateTimesCursor = getEventDateTimes(venue, startTime, startDate); //means startTime is there and startDate
        if(dateTimesCursor.moveToNext()){
            if(dateTimesCursor.getCount() != 0){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return true;
        }
        /*
        if(dateTimesCursor.moveToFirst()){
            Toast.makeText(context, "HERE in isDateTimeValid cursor move to first", Toast.LENGTH_SHORT).show();
            do {
                String startDateString = dateTimesCursor.getString(0);
                String startTimeString = dateTimesCursor.getString(1);
                String endDateString = dateTimesCursor.getString(2);
                String endTimeString = dateTimesCursor.getString(3);

                String startDB = startDateString + " " + startTimeString;
                String endDB = endDateString + " " + endTimeString;
                Date dbStartDateConverted = convertStringToDate(startDB);
                Date dbEndDateConverted = convertStringToDate(endDB);
                String startInput = startDate + " " + startTime;
                String endInput = endDate + " " + endTime;
                Date inputStartDateConverted = convertStringToDate(startInput);
                Date inputEndDateConverted = convertStringToDate(endInput);

                boolean isStartInCenter = inputStartDateConverted.after(dbStartDateConverted)
                        && inputStartDateConverted.before(dbEndDateConverted); //means start datetime is centered
                boolean isEndInCenter = inputEndDateConverted.after(dbStartDateConverted)
                        &&
                        inputEndDateConverted.before(dbEndDateConverted);   //means end datetime is centered

                if(startTimeString == startTime){
                    if(data == ""){
                        data = "equal";
                    }
                }
                if(isStartInCenter || isEndInCenter){
                    if(data == ""){
                        data = "center";
                    }
                }
                else{
                    if(data == ""){
                        data = "not center";
                    }
                }
            }
            while (dateTimesCursor.moveToNext());
        }
        else{
            //cursor is null
            data = "cursor is null";
        }

        if(data == "equal"){
            return false;
        }
        else if(data == "center"){
            return false;
        }
        else if(data == "not center"){
            return true;
        }
        else{
            return true;
        }

         */
    }

    public String insertValidity(String venue, String startDate,
                                      String endDate, String startTime, String endTime) {
        String result = "";
        boolean isVenueValid = isVenueValid(venue);
        if(isVenueValid){
            //means you can happily insert
            result = "Venue is empty";
        }
        else{
            boolean isDateTimeValid = isDateTimeValid(venue, startDate, startTime, endDate, endTime);
            if(isDateTimeValid == true){
                result = "Date and Time is Valid";
                //happy insert
            }
            else{
                result = "Date and Time is Taken";
            }
        }
        return result;
    }


    private Date convertStringToDate(String dateString){
        Date date = null;
        try {
            String newDate = modifyDateLayout(dateString);
            date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH).parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    private String modifyDateLayout(String inputDate){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(inputDate);
            return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    //=====================Methods for update
    public boolean updateEvent(String id, String customerName,
                               String venue, String venueType,
                               String startDate, String endDate,
                               String startTime, String endTime,
                               int totalGuest, String foodItem,
                               String foodCost, String venueCost,
                               String totalCost
    ) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CUSTOMER_NAME, customerName);
            cv.put(VENUE, venue);
            cv.put(VENUE_TYPE, venueType);
            cv.put(START_DATE, startDate);
            cv.put(END_DATE, endDate);
            cv.put(START_TIME, startTime);
            cv.put(END_TIME, endTime);
            cv.put(TOTAL_GUEST, totalGuest);
            cv.put(FOOD_ITEM, foodItem);
            cv.put(FOOD_COST, foodCost);
            cv.put(VENUE_COST, venueCost);
            cv.put(TOTAL_COST, totalCost);
            String whereClause = EVENT_ID + " = ?";
            int update = db.update(TABLE_EVENT, cv, whereClause, new String[]{id});
            if (update == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            Log.d("hays", ex.toString());
            return false;
        }
    }

    private Cursor getEventDateTimesForUpdate(String venue, String startDateOld, String endDateOld,
                                              String startTimeOld, String endTimeOld){
        String sql = "SELECT startDate, startTime, endDate, endTime FROM TblEvent WHERE venue = '" + venue + "' " +
                "AND startDate != '"+ startDateOld+"' AND startTime != '"+startTimeOld+ "' AND " +
                "endDate != '" +endDateOld + "' AND endTime != '"+endTimeOld+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }
    private boolean isDateTimeForUpdateValid(String venue, String startDate, String startTime, String endDate, String endTime,
                                             String startDateOld, String startTimeOld, String endDateOld, String endTimeOld){
        Cursor dateTimesCursor = getEventDateTimesForUpdate(venue, startDateOld, endDateOld, startTimeOld, endTimeOld);
        if(dateTimesCursor.moveToFirst()){
            do {
                String startDateString = dateTimesCursor.getString(0);
                String startTimeString = dateTimesCursor.getString(1);
                String endDateString = dateTimesCursor.getString(2);
                String endTimeString = dateTimesCursor.getString(3);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                String startDB = startDateString + " " + startTimeString;
                String endDB = endDateString + " " + endTimeString;
                Date dbStartDateConverted = convertStringToDate(startDB);
                Date dbEndDateConverted = convertStringToDate(endDB);
                String startInput = startDate + " " + startTime;
                String endInput = endDate + " " + endTime;
                Date inputStartDateConverted = convertStringToDate(startInput);
                Date inputEndDateConverted = convertStringToDate(endInput);
                boolean isStartInCenter = inputStartDateConverted.after(dbStartDateConverted)
                        && inputStartDateConverted.before(dbEndDateConverted); //means start datetime is centered
                boolean isEndInCenter = inputEndDateConverted.after(dbEndDateConverted)
                        && inputEndDateConverted.before(dbEndDateConverted);   //means end datetime is centered

                if(isStartInCenter || isEndInCenter){
                    return false;
                }
                else{
                    return true;
                }
            }
            while (dateTimesCursor.moveToNext());
        }
        else{
            //cursor is null
            return true;
        }
    }
    public String updateValidity(String venue, String startDate, String startTime, String endDate, String endTime,
                                   String startDateOld, String startTimeOld, String endDateOld, String endTimeOld){
        String result = "";

        boolean isVenueValid = isVenueValid(venue);
        if(isVenueValid){
            //means you can happily insert
            result = "Venue is empty";
        }
        else{
            boolean isDateTimeValid = isDateTimeForUpdateValid(venue, startDate, startTime, endDate, endTime,
                    startDateOld, startTimeOld, endDateOld, endTimeOld);
            if(isDateTimeValid){
                result = "Date and Time is Valid";
                //happy insert
            }
            else{
                result = "Date and Time is Taken";
            }
        }
        return result;
    }





    //=======================================Methods for delete
    public boolean deleteEvent(int eventID){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "DELETE FROM TblEvent WHERE eventID = '" + eventID + "'";

        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext()){
            return true;
        }
        else{
            return false;
        }
    }



    public Cursor viewAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_EVENT;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }
}
