package tech.gregori.criteriosderansom.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import tech.gregori.criteriosderansom.db.Database;

public abstract class DAL {
    private static final String TAG = "DAL";
    protected SQLiteDatabase db;
    protected Database schema;
    protected Cursor cursor;
    private Class<?> tableClass;

    private Object getFieldValue(String fieldName) {
        Object fieldValue = null;

        try {
            Field field = tableClass.getField(fieldName);
            field.setAccessible(true);

            fieldValue = field.get(null);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "getFieldValue: Campo inexistente: " + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(TAG, "getFieldValue: Acesso ilegal: " + e.getMessage());
        }

        return fieldValue;
    }

    private String getIdName() {
        return (String) getFieldValue("PRIMARY_KEY");
    }

    private String getTableName() {
        return (String) getFieldValue("TABLE_NAME");
    }

    private String[] getColumns() {
        return (String[]) getFieldValue("COLUMNS");
    }

    protected abstract <T> T cursorToEntity(Cursor cursor);

    public <T> T findById(long resourceId) {
        final String selectionArgs[] = {String.valueOf(resourceId)};
        final String selection = String.format("%s = ?", getIdName());

        T resource;
        cursor = query(getTableName(), getColumns(), selection,
                selectionArgs, getIdName());

        if (cursor != null && cursor.moveToFirst()) {
            resource = cursorToEntity(cursor);
            cursor.close();
        } else {
            return null;
        }
        db.close();
        return resource;
    }

    public <T> List<T> findAll() {
        List<T> resources = new ArrayList<>();

        cursor = query(getTableName(), getColumns(), null,
                null, getIdName());
        if (cursor != null) {
            cursor.moveToFirst();
            Log.d(TAG, "findAll: count: " + cursor.getCount());
            while (!cursor.isAfterLast()) {
                T resource = cursorToEntity(cursor);
                resources.add(resource);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();;
        return resources;
    }


    public DAL(Context context, Class<?> tableClass) {
        schema = new Database(context);
        this.tableClass = tableClass;
    }

    public int delete(String tableName, String selection, String[] selectionArgs) {
        db = schema.getWritableDatabase();
        int result = db.delete(tableName, selection, selectionArgs);
        return result;
    }

    public int update(String tableName, ContentValues values,
                      String selection, String[] selectionArgs) {
        db = schema.getWritableDatabase();
        int result = db.update(tableName, values, selection, selectionArgs);

        return result;
    }

    public long insert(String tableName, ContentValues values) {
        db = schema.getWritableDatabase();
        long result = db.insert(tableName, null, values);
        return result;
    }

    public Cursor query(String tableName, String[] columns, String selection,
                        String[] selectionArgs, String sortOrder) {
        db = schema.getReadableDatabase();
        final Cursor cursor = db.query(tableName, columns, selection, selectionArgs,
                null, null, sortOrder);
        return cursor;
    }

    public Cursor query(String tableName, String[] columns, String selection, String[]
            selectionArgs, String sortOrder, String limit) {
        db = schema.getReadableDatabase();
        final Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null,
                null, sortOrder, limit);
        db.close();
        return cursor;
    }

    public Cursor query(String tableName, String[] columns, String selection, String[]
            selectionArgs, String groupBy, String having, String orderBy, String limit) {
        db = schema.getReadableDatabase();
        final Cursor cursor = db.query(tableName, columns, selection,
                selectionArgs, groupBy, having, orderBy, limit);
        return cursor;
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        db = schema.getReadableDatabase();
        final Cursor cursor = db.rawQuery(sql, selectionArgs);
        return cursor;
    }

}
