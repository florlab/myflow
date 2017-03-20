package tech.myflow.myflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 3/20/2017.
 */
public class TableControllerItem extends DatabaseHandler{
    public TableControllerItem(Context context) {
        super(context);
    }

    public boolean create(ObjectItem ObjectItem) {

        ContentValues values = new ContentValues();

        values.put("item_name", ObjectItem.item_name);
        values.put("item_amount", ObjectItem.item_amount);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("items", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM items";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public List<ObjectItem> read() {

        List<ObjectItem> recordsList = new ArrayList<ObjectItem>();

        String sql = "SELECT * FROM items ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String itemName = cursor.getString(cursor.getColumnIndex("item_name"));
                String itemAmount = cursor.getString(cursor.getColumnIndex("item_amount"));

                ObjectItem objectItem = new ObjectItem();
                objectItem.id = id;
                objectItem.item_name = itemName;
                objectItem.item_amount = itemAmount;

                recordsList.add(objectItem);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public ObjectItem readSingleRecord(int itemId) {

        ObjectItem objectItem = null;

        String sql = "SELECT * FROM items WHERE id = " + itemId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String itemName = cursor.getString(cursor.getColumnIndex("item_name"));
            String itemAmount = cursor.getString(cursor.getColumnIndex("item_amount"));

            objectItem = new ObjectItem();
            objectItem.id = id;
            objectItem.item_name = itemName;
            objectItem.item_amount = itemAmount;

        }

        cursor.close();
        db.close();

        return objectItem;

    }

    public boolean update(ObjectItem objectItem) {

        ContentValues values = new ContentValues();

        values.put("item_name", objectItem.item_name);
        values.put("item_amount", objectItem.item_amount);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectItem.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("items", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("items", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }
}
