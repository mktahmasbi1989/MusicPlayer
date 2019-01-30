package com.example.mohamdkazem.musicplayer;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamdkazem.musicplayer.model.Music;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicListDialodFragment extends DialogFragment {


    private static final String ARTIST_ID = "albumId";
    private String albumName;
    private RecyclerView recyclerView;
    private MusicAdaptorDialog musicAdaptor;
    private MusicPlayer musicPlayer;
    private MediaPlayer mediaPlayer;


    public static MusicListDialodFragment newInstance(String albumName) {

        Bundle args = new Bundle();
        args.putString(ARTIST_ID, albumName);
        MusicListDialodFragment fragment = new MusicListDialodFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MusicListDialodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        albumName = getArguments().getString(ARTIST_ID);
        musicPlayer = new MusicPlayer(getActivity());
        mediaPlayer=new MediaPlayer();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_list_dialod, container, false);
        recyclerView= view.findViewById(R.id.recycle_view_dialog);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        musicAdaptor = new MusicAdaptorDialog(musicPlayer.getalbumMusicList(albumName));
        recyclerView.setAdapter(musicAdaptor);
        return view;
    }

    private class MusicHolderDialog extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayout;
        private TextView textView;
        private ImageView imageView;
        private Music music;

        public MusicHolderDialog(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.holder);
            textView=itemView.findViewById(R.id.textView);
            imageView=itemView.findViewById(R.id.image_view);
        }
        public void bindMusic(Music music) {
            music = music;
            textView.setText(music.getTitle());
            imageView.setImageURI(Uri.parse(music.getImageUri()));
        }
    }
    public class MusicAdaptorDialog extends RecyclerView.Adapter<MusicHolderDialog>{

        private List<Music> musicList;

        public MusicAdaptorDialog(List<Music> musicList) {
            this.musicList = musicList;
        }

        @NonNull
        @Override
        public MusicHolderDialog onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.music_holder, parent, false);
            return new MusicHolderDialog(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MusicHolderDialog holder, int position) {
            Music music = musicList.get(position);
            holder.bindMusic(music);
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }
}