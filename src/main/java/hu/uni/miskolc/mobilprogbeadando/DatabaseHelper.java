package hu.uni.miskolc.mobilprogbeadando;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import hu.uni.miskolc.mobilprogbeadando.model.Employee;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "employee_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";
    private static final String COL3 = "salary";
    private static final String COL4 = "age";

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " +
                COL3 + " NUMBER, " +
                COL4 + " NUMBER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(new StringBuilder().append("DROP IF TABLE EXISTS ").append(TABLE_NAME).toString());
        onCreate(db);
    }

    public boolean addData(Employee employee){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, employee.getName());
        contentValues.put(COL3, employee.getSalary());
        contentValues.put(COL4, employee.getAge());

        Log.d(TAG, "addingData: Adding " + employee.getName() + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM "+ TABLE_NAME + " WHERE " +
                COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = '" + newName + "' WHERE " +
                COL1 + " = '" + id + "'" + " AND " +COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " +query);
        Log.d(TAG, "updateName: Setting name to: " + newName);
        db.execSQL(query);
    }

    public void deleteItem(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + id +
                "'" + " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database");
        db.execSQL(query);
    }
}
