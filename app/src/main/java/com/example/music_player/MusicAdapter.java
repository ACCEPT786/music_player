package com.example.music_player;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class MusicAdapter extends BaseAdapter {
    private List<Music> lists;
    private Context context;

    public MusicAdapter(List<Music> list, Context context) {
        this.lists = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.listview,null);
            holder.text_artist = view.findViewById(R.id.text_artist);
            holder.text_duration = view.findViewById(R.id.text_duration);
            holder.text_title = view.findViewById(R.id.text_title);
            holder.text_counter = view.findViewById(R.id.text_counter);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.text_title.setText(lists.get(i).getTitle());
        holder.text_duration.setText(lists.get(i).getDuration_display());
        holder.text_artist.setText(lists.get(i).getArtist());
        holder.text_counter.setText(String.valueOf(lists.get(i).getCounter()));
        return view;
    }

    private class ViewHolder{
        private TextView text_title,text_artist,text_duration,text_counter;
    }

}
