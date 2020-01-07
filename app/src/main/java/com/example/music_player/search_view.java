package com.example.music_player;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.media.MediaPlayer;

import com.example.music_player.Music.Music;
import com.example.music_player.Music.MusicAdapter;
import com.example.music_player.Music.MusicUtil;

public class search_view extends AppCompatActivity {
    private List<Music> lists;
    private TextView tv_main_title;
    private TextView tv_back;
    private ListView listView;
    private SearchView searchView;
    private MediaPlayer mediaPlayer;
    private Timer timer = new Timer();
    private SeekBar seek;
    private MusicAdapter musicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText(this.getResources().getText(R.string.search_local));

        listView = findViewById(R.id.lv);
        listView.setTextFilterEnabled(true);


        tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(view ->
                search_view.this.finish());

        searchView = findViewById(R.id.sv);
        mediaPlayer = new MediaPlayer();
        seek = findViewById(R.id.search_seek);
        seek.setProgress(0);
        seek.setBackgroundColor(this.getResources().getColor(R.color.transparent));
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

        MusicUtil musicUtil = new MusicUtil();
        lists = musicUtil.getMusic(this);
        musicAdapter = new MusicAdapter(lists, this);
        listView.setAdapter(musicAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) ->{
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


        int size = lists.size();
        String[] title = new String[size];
        for (int i = 0; i < size; i++) {
            title[i] = lists.get(i).getTitle() + i;
        }

        final ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,title);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    adapter.getFilter().filter(newText);
                    listView.setOnItemClickListener((adapterView,view,i,l) -> {
                        TextView selected = view.findViewById(android.R.id.text1);
                        String info = selected.getText().toString();
                        String[] array1 = info.split("[\\D]+");
                        String final_sequence = array1[array1.length - 1];
                        int sequence = Integer.parseInt(final_sequence);
                        seek.setProgress(0);
                        mediaPlayer.reset();
                        try {
                            seek.setMax(Integer.parseInt(lists.get(sequence).getDuration()));
                            mediaPlayer.setDataSource(lists.get(sequence).getData());
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
                }else{
                    listView.clearTextFilter();
                }
                return true;
            }
        });
    }
}
