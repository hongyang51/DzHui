package com.xyb513951.mydazahui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou3g.mydazahui.R;
import com.xyb513951.mydazahui.adapter.Collection_List_Adapter;
import com.xyb513951.mydazahui.utils.daosingleton.DaoSingleton;
import com.xyb513951.mydazahui.base.Final_Base;
import com.xyb513951.mydazahui.base.MainActivity;
import com.xyb513951.mydazahui.greendaobean.Collection;
import com.xyb513951.mydazahui.greendaobean.CollectionDao;
import com.xyb513951.mydazahui.greendaobean.User;
import com.xyb513951.mydazahui.greendaobean.UserDao;

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
    private ImageView back;
    private ArrayList<User> users;
    private String userName;
    private Collection collection;
    private ArrayList<Collection> collections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao = DaoSingleton.getInstance().getUserDao();
        initView();//初始化视图
        initData();//初始化数据

    }

    /**
     * 初始化视图
     */
    private void initView() {
        setContentView(R.layout.activity_collection);
        textView = (TextView) findViewById(R.id.load);
        list_collection = (ListView) findViewById(R.id.list_collection);
        back = (ImageView) findViewById(R.id.back);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        textView.setText("我的收藏");
        users = (ArrayList<User>) userDao.loadAll();
        if (users.size() > 0) {
            User user = users.get(0);
            userName = user.getName();
            collectionDao = DaoSingleton.getInstance().getCollectionDao();
            final QueryBuilder qb = collectionDao.queryBuilder();
            qb.where(CollectionDao.Properties.User_name.eq(userName));
            collections = (ArrayList<Collection>) qb.list();
            adapter = new Collection_List_Adapter(this, collections);
            list_collection.setAdapter(adapter);
            initViewListener();

        }
    }

    /**
     * 控件监听方法
     */
    private void initViewListener() {
        // listView行布局监听
        list_collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CollectionActivity.this, WebViewActivity.class);
                intent.putExtra(Final_Base.NEWSID, collections.get(position).getNewsId());
                startActivity(intent);
                overridePendingTransition
                        (R.anim.translate_enter_in, R.anim.translate_enter_out);
            }
        });
        // listView行布局长按监听
        list_collection.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                collection = (Collection) parent.getItemAtPosition(position);
                dialogShow();

                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition
                        (R.anim.translate_exit_in, R.anim.translate_exit_out);
            }
        });
    }

    /**
     * 弹出Dialog方法
     */
    private void dialogShow() {
        builder = new AlertDialog.Builder(CollectionActivity.this);
        dialog = builder.setIcon(R.mipmap.ic_launcher)
                .setTitle("系统提示")
                .setMessage("您确定要取消收藏?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        QueryBuilder qb1 = collectionDao.queryBuilder();
                        qb1.where(CollectionDao.Properties.User_name.eq(userName));
                        qb1.where(CollectionDao.Properties.Title.eq(collection.getTitle()));
                        Log.e("NNN", qb1.list() + "");
                        qb1.buildDelete().executeDeleteWithoutDetachingEntities();
                        QueryBuilder qb = collectionDao.queryBuilder();
                        qb.where(CollectionDao.Properties.User_name.eq(userName));
                        final ArrayList<Collection> collections = (ArrayList<Collection>) qb.list();
                        adapter = new Collection_List_Adapter(CollectionActivity.this, collections);
                        list_collection.setAdapter(adapter);

                        Toast.makeText(CollectionActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        dialog.show();

    }

    /**
     * 复写onKeyDown方法添加退出动画
     **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition
                    (R.anim.translate_exit_in, R.anim.translate_exit_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}