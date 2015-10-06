package com.lanou3g.mydazahui.greendaobean;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.lanou3g.mydazahui.greendaobean.GuidePage;
import com.lanou3g.mydazahui.greendaobean.StoriesEntity;
import com.lanou3g.mydazahui.greendaobean.TopStoriesEntity;
import com.lanou3g.mydazahui.greendaobean.images;
import com.lanou3g.mydazahui.greendaobean.User;

import com.lanou3g.mydazahui.greendaobean.GuidePageDao;
import com.lanou3g.mydazahui.greendaobean.StoriesEntityDao;
import com.lanou3g.mydazahui.greendaobean.TopStoriesEntityDao;
import com.lanou3g.mydazahui.greendaobean.imagesDao;
import com.lanou3g.mydazahui.greendaobean.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig guidePageDaoConfig;
    private final DaoConfig storiesEntityDaoConfig;
    private final DaoConfig topStoriesEntityDaoConfig;
    private final DaoConfig imagesDaoConfig;
    private final DaoConfig userDaoConfig;

    private final GuidePageDao guidePageDao;
    private final StoriesEntityDao storiesEntityDao;
    private final TopStoriesEntityDao topStoriesEntityDao;
    private final imagesDao imagesDao;
    private final UserDao userDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        guidePageDaoConfig = daoConfigMap.get(GuidePageDao.class).clone();
        guidePageDaoConfig.initIdentityScope(type);

        storiesEntityDaoConfig = daoConfigMap.get(StoriesEntityDao.class).clone();
        storiesEntityDaoConfig.initIdentityScope(type);

        topStoriesEntityDaoConfig = daoConfigMap.get(TopStoriesEntityDao.class).clone();
        topStoriesEntityDaoConfig.initIdentityScope(type);

        imagesDaoConfig = daoConfigMap.get(imagesDao.class).clone();
        imagesDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        guidePageDao = new GuidePageDao(guidePageDaoConfig, this);
        storiesEntityDao = new StoriesEntityDao(storiesEntityDaoConfig, this);
        topStoriesEntityDao = new TopStoriesEntityDao(topStoriesEntityDaoConfig, this);
        imagesDao = new imagesDao(imagesDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(GuidePage.class, guidePageDao);
        registerDao(StoriesEntity.class, storiesEntityDao);
        registerDao(TopStoriesEntity.class, topStoriesEntityDao);
        registerDao(images.class, imagesDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        guidePageDaoConfig.getIdentityScope().clear();
        storiesEntityDaoConfig.getIdentityScope().clear();
        topStoriesEntityDaoConfig.getIdentityScope().clear();
        imagesDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
    }

    public GuidePageDao getGuidePageDao() {
        return guidePageDao;
    }

    public StoriesEntityDao getStoriesEntityDao() {
        return storiesEntityDao;
    }

    public TopStoriesEntityDao getTopStoriesEntityDao() {
        return topStoriesEntityDao;
    }

    public imagesDao getImagesDao() {
        return imagesDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
