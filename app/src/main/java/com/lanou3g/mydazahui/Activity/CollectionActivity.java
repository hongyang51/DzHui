package com.lanou3g.mydazahui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.adapter.Collection_List_Adapter;
import com.lanou3g.mydazahui.base.DaoSingleton;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.base.MainActivity;
import com.lanou3g.mydazahui.greendaobean.Collection;
import com.lanou3g.mydazahui.greendaobean.CollectionDao;
import com.lanou3g.mydazahui.greendaobean.User;
import com.lanou3g.mydazahui.greendaobean.UserDao;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dllo on 15/10/7.
 */
public class CollectionActivity extends MainActivity {
    private TextView textView;
    private Collection_List_Adapter adapter;
    private ListView list_collection;
    private CollectionDao collectionDao;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        textView = (TextView) findViewById(R.id.load);
        list_collection = (ListView) findViewById(R.id.list_collection);
        textView.setText("我的收藏");
        userDao = DaoSingleton.getInstance().getUserDao();
        ArrayList<User> users = (ArrayList<User>) userDao.loadAll();
        if (users.size() > 0) {
            User user = users.get(0);
            String userName = user.getName();
            collectionDao = DaoSingleton.getInstance().getCollectionDao();
            QueryBuilder qb = collectionDao.queryBuilder();
            qb.where(CollectionDao.Properties.User_name.eq(userName));
            final ArrayList<Collection> collections = (ArrayList<Collection>) qb.list();
            adapter = new Collection_List_Adapter(this, collections);
            list_collection.setAdapter(adapter);
            list_collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CollectionActivity.this, WebViewActivity.class);
                    intent.putExtra(Final_Base.NEWSID, collections.get(position).getNewsId());
                    startActivity(intent);
                }
            });
        }


    }
}
