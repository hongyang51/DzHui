package com.xyb513951.mydazahui.greendaobean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GUIDE_PAGE".
*/
public class GuidePageDao extends AbstractDao<GuidePage, Void> {

    public static final String TABLENAME = "GUIDE_PAGE";

    /**
     * Properties of entity GuidePage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Text = new Property(0, String.class, "text", false, "TEXT");
        public final static Property Img = new Property(1, String.class, "img", false, "IMG");
    };


    public GuidePageDao(DaoConfig config) {
        super(config);
    }
    
    public GuidePageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GUIDE_PAGE\" (" + //
                "\"TEXT\" TEXT," + // 0: text
                "\"IMG\" TEXT);"); // 1: img
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GUIDE_PAGE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, GuidePage entity) {
        stmt.clearBindings();
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(1, text);
        }
 
        String img = entity.getImg();
        if (img != null) {
            stmt.bindString(2, img);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public GuidePage readEntity(Cursor cursor, int offset) {
        GuidePage entity = new GuidePage( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // text
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // img
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, GuidePage entity, int offset) {
        entity.setText(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setImg(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(GuidePage entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(GuidePage entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
