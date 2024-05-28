package com.example.gos;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "car_database12";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "cars";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_MODEL = "model";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_STATE = "state";

    public CarDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BRAND + " TEXT, " +
                COLUMN_MODEL + " TEXT, " +
                COLUMN_YEAR + " INTEGER, " +
                COLUMN_NUMBER + " TEXT, " +
                COLUMN_STATE + " TEXT, " +
                "person_name TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addCar(String brand, String model, int year, String number, String state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_MODEL, model);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_NUMBER, number);
        values.put(COLUMN_STATE, state);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateCar(int carId, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(carId)});
        db.close();
    }
    // Метод для получения всех машин из базы данных
    public Cursor getAllCars() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }


    // Метод для получения количества машин в определенном состоянии
    public int getCountByState(String state) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COLUMN_STATE + " = ?", new String[]{state});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    // Метод для получения количества машин в парке (state="p")
    public int getParkedCarCount() {
        return getCountByState("p");
    }

    // Метод для получения количества выданных машин (state="v")
    public int getIssuedCarCount() {
        return getCountByState("v");
    }

    // Метод для получения количества списанных машин (state="c")
    public int getWrittenOffCarCount() {
        return getCountByState("c");
    }


}
