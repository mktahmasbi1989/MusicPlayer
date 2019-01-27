package com.example.mohamdkazem.musicplayer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohamdkazem.musicplayer.model.Music;
import com.google.android.material.button.MaterialButton;

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
    private Button btnStop;

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
    public void onDestroy() {
        super.onDestroy();
        musicPlayer.release();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        mRecyclerView = view.findViewById(R.id.beat_box_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        musicAdaptor=new MusicAdaptor(musicPlayer.getMusicList());
        mRecyclerView.setAdapter(musicAdaptor);
        btnStop=view.findViewById(R.id.btn_stop);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
            }
        });

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
                            AssetFileDescriptor afd = assetManager.openFd(mMusic.getmAssetPath());
                            mediaPlayer=new MediaPlayer();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                mediaPlayer.setDataSource(afd);
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                                afd.close();
                            }

                        }catch (IllegalArgumentException e) {
                               e.printStackTrace();
                           } catch (IllegalStateException e) {
                               e.printStackTrace();
                           } catch (IOException e) {
                               e.printStackTrace();

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




