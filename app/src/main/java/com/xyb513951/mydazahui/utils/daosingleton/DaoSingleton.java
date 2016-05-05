package com.xyb513951.mydazahui.utils.daosingleton;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xyb513951.mydazahui.base.BaseApplication;
import com.xyb513951.mydazahui.greendaobean.CollectionDao;
import com.xyb513951.mydazahui.greendaobean.DaoMaster;
import com.xyb513951.mydazahui.greendaobean.DaoSession;
import com.xyb513951.mydazahui.greendaobean.GuidePageDao;
import com.xyb513951.mydazahui.greendaobean.OthersEntityDao;
import com.xyb513951.mydazahui.greendaobean.StoriesEntityDao;
import com.xyb513951.mydazahui.greendaobean.TopStoriesEntityDao;
import com.xyb513951.mydazahui.greendaobean.UserDao;
import com.xyb513951.mydazahui.greendaobean.imagesDao;

/**
 * Created by dllo on 15/9/30.
 */
public class DaoSingleton {
    private static final String DATABASE_NAME = "daodemo.db";
    private volatile static DaoSingleton instance;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Context context;
    private DaoMaster.DevOpenHelper helper;
    private GuidePageDao guidePageDao;
    private StoriesEntityDao storiesEntityDao;
    private TopStoriesEntityDao topStoriesEntityDao;
    private imagesDao imagesDao;
    private UserDao userDao;
    private CollectionDao collectionDao;
    private OthersEntityDao othersEntityDao;


    private DaoSingleton() {
        context = BaseApplication.getContext();
    }

    public static DaoSingleton getInstance() {
        if (instance == null) {
            synchronized (DaoSingleton.class) {
                if (instance == null) {
                    instance = new DaoSingleton();
                }
            }
        }
        return instance;
    }

    public DaoMaster.DevOpenHelper getHelper() {
        if (helper == null) {
            helper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
        }
        return helper;
    }

    private SQLiteDatabase getDb() {
        if (db == null) {
            db = getHelper().getWritableDatabase();
        }
        return db;
    }

    private DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(getDb());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }

    public GuidePageDao getGuidePageDao() {
        if (guidePageDao == null) {
            guidePageDao = getDaoSession().getGuidePageDao();
        }

        return guidePageDao;
    }

    public StoriesEntityDao getStoriesEntityDao() {
        if (storiesEntityDao == null) {
            storiesEntityDao = getDaoSession().getStoriesEntityDao();
        }

        return storiesEntityDao;
    }

    public TopStoriesEntityDao getTopStoriesEntityDao() {
        if (topStoriesEntityDao == null) {
            topStoriesEntityDao = getDaoSession().getTopStoriesEntityDao();
        }
        return topStoriesEntityDao;
    }

    public com.xyb513951.mydazahui.greendaobean.imagesDao getImagesDao() {
        if (imagesDao == null) {
            imagesDao = getDaoSession().getImagesDao();
        }
        return imagesDao;
    }

    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = getDaoSession().getUserDao();
        }
        return userDao;
    }

    public CollectionDao getCollectionDao() {
        if (collectionDao == null) {
            collectionDao = getDaoSession().getCollectionDao();
        }

        return collectionDao;
    }

    public OthersEntityDao getOthersEntityDao() {
        if (othersEntityDao == null) {
            othersEntityDao = getDaoSession().getOthersEntityDao();
        }

        return othersEntityDao;
    }
}

