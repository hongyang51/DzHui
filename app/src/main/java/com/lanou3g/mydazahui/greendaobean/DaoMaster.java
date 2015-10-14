package com.lanou3g.mydazahui.greendaobean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import com.lanou3g.mydazahui.greendaobean.GuidePageDao;
import com.lanou3g.mydazahui.greendaobean.StoriesEntityDao;
import com.lanou3g.mydazahui.greendaobean.TopStoriesEntityDao;
import com.lanou3g.mydazahui.greendaobean.imagesDao;
import com.lanou3g.mydazahui.greendaobean.UserDao;
import com.lanou3g.mydazahui.greendaobean.CollectionDao;
import com.lanou3g.mydazahui.greendaobean.OthersEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        GuidePageDao.createTable(db, ifNotExists);
        StoriesEntityDao.createTable(db, ifNotExists);
        TopStoriesEntityDao.createTable(db, ifNotExists);
        imagesDao.createTable(db, ifNotExists);
        UserDao.createTable(db, ifNotExists);
        CollectionDao.createTable(db, ifNotExists);
        OthersEntityDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        GuidePageDao.dropTable(db, ifExists);
        StoriesEntityDao.dropTable(db, ifExists);
        TopStoriesEntityDao.dropTable(db, ifExists);
        imagesDao.dropTable(db, ifExists);
        UserDao.dropTable(db, ifExists);
        CollectionDao.dropTable(db, ifExists);
        OthersEntityDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(GuidePageDao.class);
        registerDaoClass(StoriesEntityDao.class);
        registerDaoClass(TopStoriesEntityDao.class);
        registerDaoClass(imagesDao.class);
        registerDaoClass(UserDao.class);
        registerDaoClass(CollectionDao.class);
        registerDaoClass(OthersEntityDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
