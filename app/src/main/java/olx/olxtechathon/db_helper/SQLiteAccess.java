package olx.olxtechathon.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class SQLiteAccess {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private Context context;

    private String[] issueColumns = {SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_OLX_ITEM, SQLiteHelper.COLUMN_UPDATED_AT};

    public SQLiteAccess(Context context) {
        this.context = context;
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void deleteTableOnStartOfNewSession() {
        database.delete(SQLiteHelper.TABLE_OLX, null, null);
    }

    public void close() {
        dbHelper.close();
    }

}

