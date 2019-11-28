package id.ac.umn.tematik;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlaylistListAdapter extends RecyclerView.Adapter<PlaylistListAdapter.PromoListView>{
    List<PlayList.Music> musics = new ArrayList<>();

    public PlaylistListAdapter(){}

    public void SetData(List<PlayList.Music> musics){
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new PlaylistDiffUtil(this.musics, musics), false);
        this.musics = musics;
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public PlaylistListAdapter.PromoListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_list, parent, false);
        return new PlaylistListAdapter.PromoListView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoListView holder, int position) {
        Log.d("DEBUG", "Normal Bind Playlist "+position);
        PlayList.Music music = musics.get(position);
        holder.bind(music.getId(), position, music.getName());
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistListAdapter.PromoListView holder, int position, @NonNull List<Object> payloads) {
        PlayList.Music music = musics.get(position);
        String name = music.getName();
        if(!payloads.isEmpty()){
            Bundle payload = (Bundle) payloads.get(0);
            if(payload.containsKey(PlaylistDiffUtil.NAME)){
                name = payload.getString(PlaylistDiffUtil.NAME);
            }
        }

        holder.bind(music.getId(), position, name);
    }



    @Override
    public int getItemCount() {
        return musics.size();
    }

    class PromoListView extends RecyclerView.ViewHolder{
        View view;
        TextView indexText, titleText;
        ImageView line;

        public PromoListView(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            indexText = itemView.findViewById(R.id.playlist_list_index);
            titleText = itemView.findViewById(R.id.playlist_list_title);
            line = itemView.findViewById(R.id.playlist_list_line);
        }

        public void bind(final Integer id, final Integer index, String title){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // play music
                    MusicPlayer.getInstance().changeMusicIndex(index);
                }
            });
            indexText.setText(index.toString());
            titleText.setText(title);

            // show line if not last index
            // hide line if last index
            if(index < getItemCount())
                line.setVisibility(View.VISIBLE);
            else
                line.setVisibility(View.INVISIBLE);
        }
    }
}
