package com.example.music_player;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.music_player.Music.Music;
import com.example.music_player.Music.MusicAdapter;
import com.example.music_player.Music.MusicUtil;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    private List<Music> lists;
    private MusicAdapter adapter;
    private ListView listView;
    private MediaPlayer mediaPlayer;
    private SeekBar seek;
    private Timer timer = new Timer();
    private int index = 0;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicplayer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        search = findViewById(R.id.search);
        search.setOnClickListener(view ->
                startActivity(new Intent(com.example.music_player.MainActivity.this, search_view.class)));

        MusicUtil musicUtil = new MusicUtil();
        seek = findViewById(R.id.seek);
        listView = findViewById(R.id.listView);
        lists = musicUtil.getMusic(this);

        seek.setProgress(0);
        seek.setBackgroundColor(this.getResources().getColor(R.color.transparent));
        mediaPlayer = new MediaPlayer();
        listView.setTextFilterEnabled(true);
        listView.setBackgroundColor(this.getResources().getColor(R.color.transparent));
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        Log.i(TAG, "" + lists);
        adapter = new MusicAdapter(lists, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            index = i;
            seek.setProgress(0);
            mediaPlayer.reset();
            try {
                seek.setMax(Integer.parseInt(lists.get(i).getDuration()));
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
                seek.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,1000);

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
