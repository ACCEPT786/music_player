package com.example.music_player.Music;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class MusicUtil {
    public static List<Music> getMusic(Context context){

        List<Music> lists = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null){
            while (cursor.moveToNext()){
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                int seconds = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)) / 1000;
                int remain_seconds = (int)Math.floor(seconds % 60);
                int minutes = (seconds - remain_seconds) / 60;
                String duration_display = minutes + ":" + remain_seconds;
                String duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String size = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                int counter = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                Music music = new Music(title, artist, duration, data, size, duration_display, counter);
                lists.add(music);
            }
            cursor.close();
            return lists;
        }
        return null;

    }

}
