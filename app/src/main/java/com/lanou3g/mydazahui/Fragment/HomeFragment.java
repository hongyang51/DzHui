package com.lanou3g.mydazahui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.adapter.HomeFragment_List_Adapter;
import com.lanou3g.mydazahui.adapter.HomeFragment_ViewPager_Adapter;
import com.lanou3g.mydazahui.base.BaseFragment;
import com.lanou3g.mydazahui.base.DaoSingleton;
import com.lanou3g.mydazahui.base.Final_Base;
import com.lanou3g.mydazahui.bean.Theme;
import com.lanou3g.mydazahui.greendaobean.LatestNews;
import com.lanou3g.mydazahui.greendaobean.StoriesEntity;
import com.lanou3g.mydazahui.greendaobean.StoriesEntityDao;
import com.lanou3g.mydazahui.greendaobean.TopStoriesEntity;
import com.lanou3g.mydazahui.greendaobean.TopStoriesEntityDao;
import com.lanou3g.mydazahui.listview.SwipeRefreshLoadingLayout;
import com.lanou3g.mydazahui.utils.VolleySingleton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dllo on 15/9/22.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLoadingLayout.OnLoadListener, SwipeRefreshLoadingLayout.OnRefreshListener {
    private ViewPager viewPager;
    private VolleySingleton singleton;
    private LinearLayout home_viewpager_LinearL,Pinterest;
    private ImageView imageView;
    private ImageView getImageView;
    private View view, views;
    private TextView textView;
    private LatestNews latestNews;
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ArrayList<TopStoriesEntity> topStories;
    private ArrayList<Theme.OthersEntity> othersEntities;
    private ArrayList<StoriesEntity> storiesEntities;
    private int[] text_id = {R.id.day_text, R.id.user_text,
            R.id.movie_text, R.id.unBored_text, R.id.Design_text,
            R.id.Big_text, R.id.money_text, R.id.intenet_text,
            R.id.game_text, R.id.music_text, R.id.Animation_text,
            R.id.play_text};
    private int[] img_id = {R.id.day_img, R.id.user_img,
            R.id.movie_img, R.id.unBored_img, R.id.Design_img,
            R.id.Big_img, R.id.money_img, R.id.intenet_img,
            R.id.game_img, R.id.music_img, R.id.Animation_img,
            R.id.play_img};
    private ImageLoader imageLoader;
    private ListView listView;
    private HomeFragment_ViewPager_Adapter adapter;
    private HomeFragment_List_Adapter list_adapter;
    private NewsOnclick onclickLisner;
    private Handler handler = new Handler();
    private SwipeRefreshLoadingLayout swipeRefreshLoadingLayout;
    private int a = 1;
    private StoriesEntityDao storiesEntityDao;
    private TopStoriesEntityDao topStoriesEntityDao;
    private ProgressDialog dialog;




    public interface NewsOnclick {
        void OnNewsOnclick(String s, ArrayList<Theme.OthersEntity> othersEntities);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onclickLisner = (NewsOnclick) context;
    }


    @Override
    public View initViews() {
        storiesEntityDao = DaoSingleton.getInstance().getStoriesEntityDao();
        topStoriesEntityDao = DaoSingleton.getInstance().getTopStoriesEntityDao();
        views = View.inflate(mActivity, R.layout.fragment_tabhost_home_listview, null);
        swipeRefreshLoadingLayout = (SwipeRefreshLoadingLayout) views.findViewById(R.id.swipeRefreshLoadingLayout);
        swipeRefreshLoadingLayout.setOnLoadListener(this);
        swipeRefreshLoadingLayout.setOnRefreshListener(this);
        listView = (ListView) views.findViewById(R.id.Home_list_item);
        view = View.inflate(mActivity, R.layout.fragment_tabhost_home_socll, null);
        Pinterest = (LinearLayout) view.findViewById(R.id.Pinterest);
        viewPager = (ViewPager) view.findViewById(R.id.home_fragment_viewPager);
        singleton = VolleySingleton.getVolleySingleton(mActivity);
        imageLoader = singleton.getImageLoader();
        return views;
    }

    @Override
    public void initData() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("正在加载中.....");
        dialog.show();
        initFromeTopUrl();// 从网上拉取
        initFromeThemes();
    }

    // 从网上拉取
    private void initFromeTopUrl() {
        topStories = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Final_Base.LATESTNEWS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                latestNews = gson.fromJson(response, LatestNews.class);
                storiesEntities = (ArrayList<StoriesEntity>) latestNews.getStories();
                topStories = (ArrayList<TopStoriesEntity>) latestNews.getTop_stories();
                storiesEntityDao.deleteAll();
                storiesEntityDao.insertOrReplaceInTx(storiesEntities);

                topStoriesEntityDao.deleteAll();
                topStoriesEntityDao.insertOrReplaceInTx(topStories);
                if( dialog.isShowing()){
                    dialog.dismiss();
                }
                adapter = new HomeFragment_ViewPager_Adapter(mActivity, topStories);
                viewPager.setAdapter(adapter);

                viewPager.setCurrentItem(2000);
                startImageTimerTask();
                viewPager.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            startImageTimerTask();
                        } else {
                            stopImageTimerTask();
                        }

                        return false;
                    }
                });

                initGuide();// 设置引导小点
                initListView();
                listView.addHeaderView(view);
                listView.setAdapter(list_adapter);
            }
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                        Intent intent = new Intent(mActivity, WebViewActivity.class);
////                        int newsId = storiesEntities.get(position - 1).getId();
////                        intent.putExtra(Final_Base.NEWSID, newsId);
////                        startActivity(intent);
////                        Log.e("ID", storiesEntities.get(position - 1).getId() + "");
//                    }
//                });
//            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (storiesEntityDao.loadAll().size() > 0) {
                    storiesEntities = (ArrayList<StoriesEntity>) storiesEntityDao.loadAll();
                }

                if (topStoriesEntityDao.loadAll().size() > 0) {
                    topStories = (ArrayList<TopStoriesEntity>) topStoriesEntityDao.loadAll();


                    adapter = new HomeFragment_ViewPager_Adapter(mActivity, topStories);
                    viewPager.setAdapter(adapter);
                    startImageTimerTask();
                    viewPager.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                startImageTimerTask();
                            } else {
                                stopImageTimerTask();

                            }

                            return false;
                        }
                    });

                    initGuide();// 设置引导小点
                    initListView();
                    listView.addHeaderView(view);
                    listView.setAdapter(list_adapter);
                }

                if( dialog.isShowing()){
                    dialog.dismiss();
                }
                Log.e("解析失败", "网络拉取失败" + "HomeFragment");

            }
        });
        stringRequest.setShouldCache(false);
        singleton.addQueue(stringRequest, Final_Base.LATESTNEWS_REQUEUE_TAG);
    }

    // 设置引导小点
    private void initGuide() {

        home_viewpager_LinearL = (LinearLayout) view.findViewById(R.id.home_viewpager_LinearL);

        for (int i = 0; i < topStories.size(); i++) {
            imageView = new ImageView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            if (i != 0) {
                params.leftMargin = 10;
            }
            imageView.setLayoutParams(params);
            imageViews.add(imageView);
            if (i == 0) {
                imageViews.get(i).setImageResource(R.drawable.patchwork_first_red);
            } else {
                imageViews.get(i).setImageResource(R.drawable.patchwork_first_guid);
            }
            home_viewpager_LinearL.addView(imageViews.get(i));
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < topStories.size(); i++) {
                    if (i == position % topStories.size()) {
                        imageViews.get(i).setImageResource(R.drawable.patchwork_first_red);
                    } else {
                        imageViews.get(i).setImageResource(R.drawable.patchwork_first_guid);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    startImageTimerTask();
                }
            }
        });
    }

    // 从网上拉取 分类
    private void initFromeThemes() {
        othersEntities = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Final_Base.THEME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Theme theme = gson.fromJson(response, Theme.class);
                othersEntities = (ArrayList<Theme.OthersEntity>) theme.getOthers();
                initTheme();
                Pinterest.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        stringRequest.setShouldCache(false);
        singleton.addQueue(stringRequest, Final_Base.THEME_URL_REQUEUE_TAG);
    }

    private void initListView() {
        list_adapter = new HomeFragment_List_Adapter(mActivity, storiesEntities);
//        Log.e("sss",storiesEntities.size()+"");
    }

    /**
     * 加载伪瀑布流
     */
    private void initTheme() {
        for (int i = 0; i < img_id.length; i++) {
            initThemeView(i);
        }
    }

    private void initThemeView(final int i) {
        textView = (TextView) view.findViewById(text_id[i]);
        getImageView = (ImageView) view.findViewById(img_id[i]);
        getImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setText(othersEntities.get(i).getName());

        final String newsName = othersEntities.get(i).getName();
        final ImageLoader.ImageListener listener = ImageLoader.getImageListener(getImageView, R.mipmap.lanniao, R.mipmap.lanniao);
        imageLoader.get(othersEntities.get(i).getThumbnail(), listener);
        getImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickLisner.OnNewsOnclick(newsName, othersEntities);
            }
        });
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int index = viewPager.getCurrentItem();
            viewPager.setCurrentItem(++index);
            handler.postDelayed(runnable, 4000);

        }
    };


    private void stopImageTimerTask() {
        handler.removeCallbacks(runnable);
    }

    private void startImageTimerTask() {
        stopImageTimerTask();
        handler.postDelayed(runnable, 4000);
    }

    @Override
    public void onPause() {
        super.onPause();
        singleton.removeRequest(Final_Base.THEME_URL_REQUEUE_TAG);
        stopImageTimerTask();
    }

    @Override
    public void onStart() {
        super.onStart();
        startImageTimerTask();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopImageTimerTask();
    }



    // 上拉加载
    @Override
    public void onLoad() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        //日期减一
        a = a - 1;
        calendar.add(calendar.DATE, a);
        String newsDate = sdf.format(calendar.getTime());
        if (latestNews != null) {

            Log.e("要获得新闻的日期", latestNews.getDate());

            if (latestNews.getDate().equals(newsDate)) {
                String newUrl = Final_Base.OLD_NEWS_URL + newsDate;
                Log.i("TEST", "当前网址" + newUrl);
                StringRequest stringRequest = new StringRequest(newUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        latestNews = gson.fromJson(response, LatestNews.class);
                        storiesEntities = (ArrayList<StoriesEntity>) latestNews.getStories();
                        list_adapter.OnLoading(storiesEntities);
                        swipeRefreshLoadingLayout.setLoading(false);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                singleton.addQueue(stringRequest, "onLoding");
            } else {
                Toast.makeText(mActivity, "当前日期为" + latestNews.getDate() + "请您校正", Toast.LENGTH_LONG).show();
                swipeRefreshLoadingLayout.setLoading(false);
                return;
            }
        }else{
            Toast.makeText(mActivity, "请检查您的网络", Toast.LENGTH_LONG).show();
            swipeRefreshLoadingLayout.setLoading(false);
        }
    }
    // 下拉刷新
    @Override
    public void onRefresh() {
        StringRequest stringRequest = new StringRequest(Final_Base.LATESTNEWS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                latestNews = gson.fromJson(response, LatestNews.class);
                storiesEntities = (ArrayList<StoriesEntity>) latestNews.getStories();
                list_adapter.OnRefreshing(storiesEntities);
                swipeRefreshLoadingLayout.setRefreshing(false);
                a = 1;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        singleton.addQueue(stringRequest, "onRefresh");
    }

}
