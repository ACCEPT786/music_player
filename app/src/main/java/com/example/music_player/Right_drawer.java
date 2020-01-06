package com.example.music_player;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.music_player.Content_Utils.Content_Adapter;
import com.example.music_player.Content_Utils.Content_Model;
import java.util.ArrayList;
import java.util.List;

public class Right_drawer extends AppCompatActivity {
    private List<Content_Model> mList = new ArrayList<>();
    private ListView mListView;
    private Button more;
    private DrawerLayout  drawerLayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mulit_menu);

        drawerLayout = findViewById(R.id.DrawerLayout);
        more = findViewById(R.id.more);
        more.setOnClickListener(view ->
                drawerLayout.openDrawer(Gravity.RIGHT));

        initData();
        mListView = findViewById(R.id.right_listview);
        Content_Adapter adapter = new Content_Adapter(this,mList);
        mListView.setAdapter(adapter);
    }

    private void initData() {
        mList.add(new Content_Model(R.drawable.user_icon, "新闻", 1));
        mList.add(new Content_Model(R.drawable.user_icon, "订阅", 2));
        mList.add(new Content_Model(R.drawable.user_icon, "图片", 3));
        mList.add(new Content_Model(R.drawable.user_icon, "视频", 4));
        mList.add(new Content_Model(R.drawable.user_icon, "跟帖", 5));
        mList.add(new Content_Model(R.drawable.user_icon, "投票", 6));
    }

}
