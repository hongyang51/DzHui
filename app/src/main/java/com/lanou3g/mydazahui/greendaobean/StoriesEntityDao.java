package com.lanou3g.mydazahui.greendaobean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.lanou3g.mydazahui.greendaobean.StoriesEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "STORIES_ENTITY".
*/
public class StoriesEntityDao extends AbstractDao<StoriesEntity, Void> {

    public static final String TABLENAME = "STORIES_ENTITY";

    /**
     * Properties of entity StoriesEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Title = new Property(0, String.class, "title", false, "TITLE");
        public final static Property Ga_prefix = new Property(1, String.class, "ga_prefix", false, "GA_PREFIX");
        public final static Property Multipic = new Property(2, String.class, "multipic", false, "MULTIPIC");
        public final static Property Type = new Property(3, String.class, "type", false, "TYPE");
        public final static Property Id = new Property(4, String.class, "id", false, "ID");
    };


    public StoriesEntityDao(DaoConfig config) {
        super(config);
    }
    
    public StoriesEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"STORIES_ENTITY\" (" + //
                "\"TITLE\" TEXT," + // 0: title
                "\"GA_PREFIX\" TEXT," + // 1: ga_prefix
                "\"MULTIPIC\" TEXT," + // 2: multipic
                "\"TYPE\" TEXT," + // 3: type
                "\"ID\" TEXT);"); // 4: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"STORIES_ENTITY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, StoriesEntity entity) {
        stmt.clearBindings();
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(1, title);
        }
 
        String ga_prefix = entity.getGa_prefix();
        if (ga_prefix != null) {
            stmt.bindString(2, ga_prefix);
        }
 
        String multipic = entity.getMultipic();
        if (multipic != null) {
            stmt.bindString(3, multipic);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(4, type);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(5, id);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public StoriesEntity readEntity(Cursor cursor, int offset) {
        StoriesEntity entity = new StoriesEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // title
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // ga_prefix
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // multipic
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // type
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, StoriesEntity entity, int offset) {
        entity.setTitle(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setGa_prefix(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMultipic(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(StoriesEntity entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(StoriesEntity entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
