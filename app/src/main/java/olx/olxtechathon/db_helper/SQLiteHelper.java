package olx.olxtechathon.db_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_OLX = "olx_table";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_OLX_ITEM = "olx_item";
    public static final String COLUMN_UPDATED_AT = "updated_at";
    private static final String DATABASE_NAME = "olx.db";
    private static final int DATABASE_VERSION = 1;


    private static final String OLX_TABLE_CREATE = "create table "
            + TABLE_OLX + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_OLX_ITEM + " text not null, "
            + COLUMN_UPDATED_AT + " text not null);";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OLX_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + OLX_TABLE_CREATE);
        onCreate(db);
    }
}
