package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UserMaster.Users.TABLE_NAME + " (" +
                        UserMaster.Users._ID + " INTEGER PRIMARY KEY," +
                        UserMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                        UserMaster.Users.COLUMN_NAME_PASSWORD + " TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addInfo(String Username, String Password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserMaster.Users.COLUMN_NAME_USERNAME, Username);
        values.put(UserMaster.Users.COLUMN_NAME_PASSWORD, Password);

        long newRowId = db.insert(UserMaster.Users.TABLE_NAME, null, values);
        if(newRowId == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor readAllInfo(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+UserMaster.Users.TABLE_NAME,null);
        return res;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {UserMaster.Users._ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + "=?" + " and " + UserMaster.Users.COLUMN_NAME_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(UserMaster.Users.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }

    public void deleteInfo(String Username){
        SQLiteDatabase db = getReadableDatabase();
        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " LIKE ? ";
        String[] selectionArgs = {Username};
        db.delete(UserMaster.Users.TABLE_NAME,selection,selectionArgs);
    }

    public void updateInfo(String Username,String Password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserMaster.Users.COLUMN_NAME_PASSWORD,Password);

        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " LIKE ? ";
        String[] selectionArgs = {Username};
        int count = db.update(UserMaster.Users.TABLE_NAME,values,selection,selectionArgs);
    }
}
