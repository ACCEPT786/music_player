package com.example.music_player;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.music_player.Content_Utils.Content_Adapter;
import com.example.music_player.Content_Utils.Content_Model;
import com.example.music_player.Music.Music;
import com.example.music_player.Music.MusicAdapter;
import com.example.music_player.Music.MusicUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<Music> lists;
    private MusicAdapter adapter;
    private List<Content_Model> mList = new ArrayList<>();
    private ListView mListView, listView_more;
    private Button more, search_more;
    private DrawerLayout  drawerLayout;
    private SeekBar seek_more;
    private MediaPlayer mediaPlayer;
    private Timer timer= new Timer();
    private int index = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mulit_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mediaPlayer = new MediaPlayer();
        drawerLayout = findViewById(R.id.DrawerLayout);

        more = findViewById(R.id.more);
        more.setOnClickListener(view ->
                drawerLayout.openDrawer(Gravity.RIGHT));

        search_more = findViewById(R.id.search_more);
        search_more.setOnClickListener(view ->
                startActivity(new Intent(com.example.music_player.MainActivity.this, search_view.class)));

        listView_more = findViewById(R.id.listView_more);
        listView_more.setTextFilterEnabled(true);
        listView_more.setBackgroundColor(this.getResources().getColor(R.color.transparent));

        seek_more = findViewById(R.id.seek_more);
        seek_more.setProgress(0);
        seek_more.setBackgroundColor(this.getResources().getColor(R.color.transparent));
        seek_more.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        MusicUtil musicUtil = new MusicUtil();
        lists = musicUtil.getMusic(this);
        Log.i(TAG, "" + lists);
        adapter = new MusicAdapter(lists, this);
        listView_more.setAdapter(adapter);
        listView_more.setOnItemClickListener((adapterView, view, i, l) -> {
            index = i;
            seek_more.setProgress(0);
            mediaPlayer.reset();
            try {
                seek_more.setMax(Integer.parseInt(lists.get(i).getDuration()));
                mediaPlayer.setDataSource(lists.get(i).getData());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                seek_more.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,1000);

        initData();
        mListView = findViewById(R.id.right_listview);
        Content_Adapter adapter = new Content_Adapter(this,mList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    //模拟两条数据

                    case 0://返回登陆
                        //跳转至登陆界面
                        activity_change();
                        break;

                    default:
                        break;
                }
                //点击任一项item项，都关闭左侧菜单
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });
    }

    public void activity_change(){
        startActivity(new Intent(com.example.music_player.MainActivity.this, activity_login.class));
    }

//
//    public void replaceFragment(Fragment fragment){
//        //1、拿到FragmentManager管理器
//        FragmentManager manager = getSupportFragmentManager();
//
//        //2、获取事物
//        FragmentTransaction transaction = manager.beginTransaction();
//
//        //3、fragment的替换
//        transaction.replace(R.id.content,fragment);
//
//        //4、提交事物
//        transaction.commit();
//    }

    private void initData() {
        mList.add(new Content_Model(R.drawable.user_icon, "返回登陆", 1));
        mList.add(new Content_Model(R.drawable.user_icon, "订阅", 2));
        mList.add(new Content_Model(R.drawable.user_icon, "图片", 3));
        mList.add(new Content_Model(R.drawable.user_icon, "视频", 4));
        mList.add(new Content_Model(R.drawable.user_icon, "跟帖", 5));
        mList.add(new Content_Model(R.drawable.user_icon, "投票", 6));
    }

    public void click(View view) {

        switch (view.getId()){
            case R.id.button_start:
                mediaPlayer.start();
                break;

            case R.id.button_pause:
                mediaPlayer.pause();
                break;

            case R.id.button_last:
                index--;
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(lists.get(index).getData());

                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.button_next:
                index++;
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(lists.get(index).getData());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}
