package com.example.mohamdkazem.musicplayer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mohamdkazem.musicplayer.model.Music;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;


public class PlayerFragment extends Fragment {

    private static final String MUSIC_FOLDER = "chaartaar";
    private RecyclerView mRecyclerView;
    private MusicPlayer musicPlayer;
    private MusicAdaptor musicAdaptor;
    private MediaPlayer mediaPlayer;
    private AssetManager assetManager;

    public PlayerFragment() {
        // Required empty public constructor
    }



    public static PlayerFragment newInstance() {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicPlayer=new MusicPlayer(getActivity());
        assetManager=getActivity().getAssets();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        mRecyclerView = view.findViewById(R.id.beat_box_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        musicAdaptor=new MusicAdaptor(musicPlayer.getMusicList());
        mRecyclerView.setAdapter(musicAdaptor);
        return view;
    }


    private class MusicdHolder extends RecyclerView.ViewHolder {

        private Button mButton;
        private Music mMusic;

        public MusicdHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.list_item_sound_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    musicPlayer.play(mMusic,1);
                    try {
                        String[] fileNames =  assetManager.list(MUSIC_FOLDER);
                        for (String fileName : fileNames) {
                            String assetPath = MUSIC_FOLDER + File.separator + fileName;
                            Music music = new Music(assetPath);
                            AssetFileDescriptor afd = assetManager.openFd(music.getmAssetPath());
                            mediaPlayer=new MediaPlayer();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                mediaPlayer.setDataSource(afd);
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                            }

                        }
                    } catch (IOException e) {
                        Log.e(TAG, "file cannot be loaded", e);
                    }
                }
            });
        }

        public void bindMusic(Music music) {
            mMusic = music;
            mButton.setText(music.getTitle());
        }
    }

    private class MusicAdaptor extends RecyclerView.Adapter<MusicdHolder> {

        private List<Music> musicList;
        public MusicAdaptor(List<Music> musicList) {
            this.musicList = musicList;
        }
        private void setMusicList(List<Music> list){
            musicList=list;
        }

        @NonNull
        @Override
        public MusicdHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_sound, viewGroup, false);
            return new MusicdHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MusicdHolder musicdHolder, int i) {
            Music music=musicList.get(i);
            musicdHolder.bindMusic(music);
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }


    }




