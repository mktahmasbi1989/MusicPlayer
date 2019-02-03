package com.example.mohamdkazem.musicplayer;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamdkazem.musicplayer.model.Music;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavaritMusicsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FavifavoriteMusicAdaptro favifavoriteMusicAdaptro;

    public FavaritMusicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favarit_musics, container, false);
        mRecyclerView = view.findViewById(R.id.recycle_view_all_music);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        favifavoriteMusicAdaptro = new FavifavoriteMusicAdaptro(MusicLab.getInstance(getContext()).getFavoriteMusics());
        mRecyclerView.setAdapter(favifavoriteMusicAdaptro);


        return view;
    }

    private class MusicHolder extends RecyclerView.ViewHolder {

        private Music mMusic;
        private TextView textView;
        private ImageView imageView;
        private ConstraintLayout constraintLayout;

        MusicHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        MusicLab.getInstance(getActivity()).playMusic(mMusic.getMusicId(), getActivity());
//                        mCallBacks.playMusic(mMusic.getMusicId());

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        void bindMusic(Music music) {
            mMusic = music;
            textView.setText(music.getTitle());
            imageView.setImageURI(Uri.parse(mMusic.getImageUri()));
        }
    }

    public class FavifavoriteMusicAdaptro extends RecyclerView.Adapter<MusicHolder>{

        private List<Music> musicList;

        public FavifavoriteMusicAdaptro(List<Music> musicList) {
            this.musicList = musicList;
        }

        @NonNull
        @Override
        public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.music_holder, parent, false);
            return new MusicHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
            Music music = musicList.get(position);
            holder.bindMusic(music);
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }
}

