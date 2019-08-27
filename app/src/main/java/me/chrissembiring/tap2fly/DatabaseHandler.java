package me.chrissembiring.tap2fly;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = Environment.getExternalStorageDirectory().toString() + "/flightmanifest.db";

    private static final String TABLE_MANIFEST = "FlightManifest";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SEATNO = "seatno";
    private static final String KEY_FLIGHTNO = "flightno";
    private static final String KEY_BOARDINGTIME = "boardingtime";
    private static final String KEY_ETD = "etd";
    private static final String KEY_ETA = "eta";

    public DatabaseHandler(Context c)
    {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_MANIFEST_TABLE = "CREATE TABLE " + TABLE_MANIFEST + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," + KEY_SEATNO + " TEXT," + KEY_FLIGHTNO + " TEXT," + KEY_BOARDINGTIME + " TEXT," +
                KEY_ETD + " TEXT," + KEY_ETA + "TEXT)";
        db.execSQL(CREATE_MANIFEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANIFEST);
        onCreate(db);
    }

    public FlightManifest getFlightManifest(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MANIFEST, new String[] {KEY_ID ,KEY_NAME, KEY_SEATNO, KEY_FLIGHTNO, KEY_BOARDINGTIME, KEY_ETD, KEY_ETA},
                KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        FlightManifest flightmanifest;
        flightmanifest = new FlightManifest(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)));
        return flightmanifest;
    }

    public List<FlightManifest> getAllFlightManifest()
    {
        List<FlightManifest> flightmanifestList = new ArrayList<FlightManifest>();
        String selectQuery = "SELECT * FROM " + TABLE_MANIFEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
                FlightManifest flightmanifest = new FlightManifest();
                flightmanifest.setId(Integer.parseInt(cursor.getString(0)));
                flightmanifest.setName(cursor.getString(1));
                flightmanifest.setSeatno(cursor.getString(2));
                flightmanifest.setFlightno(cursor.getString(3));
                flightmanifest.setBoardingtime(Integer.parseInt(cursor.getString(4)));
                flightmanifest.setEta(Integer.parseInt(cursor.getString(5)));
                flightmanifest.setEtd(Integer.parseInt(cursor.getString(6)));
                flightmanifestList.add(flightmanifest);
            }
            while (cursor.moveToNext());
        }
        return flightmanifestList;
    }

    public int getFlightManifestCount()
    {
        String countQuery = "SELECT * FROM " + TABLE_MANIFEST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public void addFlightManifest(FlightManifest flightmanifest)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, flightmanifest.getName());
        values.put(KEY_SEATNO, flightmanifest.getSeatno());
        values.put(KEY_FLIGHTNO, flightmanifest.getFlightno());
        values.put(KEY_BOARDINGTIME, flightmanifest.getBoardingtime());
        values.put(KEY_ETD, flightmanifest.getEtd());
        values.put(KEY_ETA, flightmanifest.getEta());
        db.insert(TABLE_MANIFEST, null, values);
        db.close();
    }

    public int updateFlightManifest(FlightManifest flightmanifest)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, flightmanifest.getName());
        values.put(KEY_SEATNO, flightmanifest.getSeatno());
        values.put(KEY_FLIGHTNO, flightmanifest.getFlightno());
        values.put(KEY_BOARDINGTIME, flightmanifest.getBoardingtime());
        values.put(KEY_ETD, flightmanifest.getEtd());
        values.put(KEY_ETA, flightmanifest.getEta());;

        return db.update(TABLE_MANIFEST, values, KEY_NAME + " = ?", new String[] {flightmanifest.getName()});
    }

    public void deleteFlightManifest(FlightManifest flightmanifest)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MANIFEST, KEY_NAME + " = ?", new String[] {flightmanifest.getName()});
        db.close();
    }

}