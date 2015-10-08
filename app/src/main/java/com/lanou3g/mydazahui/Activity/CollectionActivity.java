package com.lanou3g.mydazahui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.adapter.Collection_List_Adapter;
import com.lanou3g.mydazahui.base.DaoSingleton;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.base.MainActivity;
import com.lanou3g.mydazahui.greendaobean.Collection;
import com.lanou3g.mydazahui.greendaobean.CollectionDao;
import com.lanou3g.mydazahui.greendaobean.User;
import com.lanou3g.mydazahui.greendaobean.UserDao;
import com.lanou3g.mydazahui.utils.DataCleanManager;

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
    private AlertDialog dialog = null;
    private AlertDialog.Builder builder = null;


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
            final String userName = user.getName();
            collectionDao = DaoSingleton.getInstance().getCollectionDao();
            final QueryBuilder qb = collectionDao.queryBuilder();
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
            list_collection.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    final Collection collection = (Collection) parent.getItemAtPosition(position);

                    builder = new AlertDialog.Builder(CollectionActivity.this);
                    dialog = builder.setIcon(R.mipmap.dzhreceiver)
                            .setTitle("系统提示")
                            .setMessage("您确定要取消收藏?")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DataCleanManager.deleteFolderFile(getCacheDir().toString(), true);
                                    try {
                                        String i = DataCleanManager.getCacheSize(getCacheDir());
                                        Log.e("sss", i);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(CollectionActivity.this, "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CollectionDao collectionDao = DaoSingleton.getInstance().getCollectionDao();
                                    QueryBuilder qb1 = collectionDao.queryBuilder();
                                    qb1.where(CollectionDao.Properties.User_name.eq(userName));
                                    qb1.where(CollectionDao.Properties.Title.eq(collection.getTitle()));
                                    Log.e("NNN", qb1.list() + "");
                                    qb1.buildDelete().executeDeleteWithoutDetachingEntities();
//                                    collectionDao.delete(collection);
                                    QueryBuilder qb = collectionDao.queryBuilder();
                                    qb.where(CollectionDao.Properties.User_name.eq(userName));
                                    final ArrayList<Collection> collections = (ArrayList<Collection>) qb.list();
                                    adapter = new Collection_List_Adapter(CollectionActivity.this, collections);
                                    list_collection.setAdapter(adapter);

                                    Toast.makeText(CollectionActivity.this, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();
                    dialog.show();
                    return true;
                }
            });
        }
    }


}