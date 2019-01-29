package com.example.mohamdkazem.musicplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mohamdkazem.musicplayer.model.Music;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PlayerFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MusicPlayer musicPlayer;
    private MusicAdaptor musicAdaptor;
    private MediaPlayer mediaPlayer ;
    private ImageButton btnPause;
    private int duration;
    private TextView textTotalDurataion, textDuration;
    private SeekBar mSeekBar;
    private Handler mSeekbarUpdateHandler = new Handler();


    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            mSeekbarUpdateHandler.postDelayed(this, 50);
            setCurrentDuration();
        }
    };


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
        musicPlayer = new MusicPlayer(getActivity());
        mediaPlayer=new MediaPlayer();
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        mSeekBar = view.findViewById(R.id.songProgressBar);
        btnPause = view.findViewById(R.id.btnPlay);
        textTotalDurataion = view.findViewById(R.id.songTotalDurationLabel);
        mRecyclerView = view.findViewById(R.id.beat_box_recycler_view);
        textDuration = view.findViewById(R.id.songCurentDurationLabel);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        musicAdaptor = new MusicAdaptor(musicPlayer.getMusicList());
        mRecyclerView.setAdapter(musicAdaptor);

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        btnPause.setBackgroundResource(R.drawable.btn_pause);
                    }else
                        mediaPlayer.start();
                        btnPause.setBackgroundResource(R.drawable.btn_play);
                }
            }
        });

        return view;
    }

    private void setTotalDuration() {
        duration = mediaPlayer.getDuration();
        String currTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        textTotalDurataion.setText(currTime);
    }

    private void setCurrentDuration() {
        duration = mediaPlayer.getCurrentPosition();
        String currTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        textDuration.setText(currTime);
    }

    private class MusicHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout constraintLayout;
        private Music mMusic;
        private TextView textView;
        private ImageView imageView;

        public MusicHolder(@NonNull final View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.holder);
            textView=itemView.findViewById(R.id.textView);
            imageView=itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mediaPlayer=new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(getActivity(), Uri.parse(mMusic.getUri()));
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        btnPause.setBackgroundResource(R.drawable.btn_pause);
                        setTotalDuration();
                        mSeekBar.setMax(mediaPlayer.getDuration());
                        mUpdateSeekbar.run();

                        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (fromUser) {
                                    mSeekBar.setProgress(progress);
                                    mediaPlayer.seekTo(progress);
                                    mUpdateSeekbar.run();
                                }
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        });

                    } catch (IllegalArgumentException e) {
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
            textView.setText(music.getTitle());
        }
    }

    private class MusicAdaptor extends RecyclerView.Adapter<MusicHolder> {

        private List<Music> musicList;

        MusicAdaptor(List<Music> musicList) {
            this.musicList = musicList;
        }

        private void setMusicList(List<Music> list) {
            musicList = list;
        }

        @NonNull
        @Override
        public MusicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.music_holder, viewGroup, false);
            return new MusicHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MusicHolder musicdHolder, int i) {
            Music music = musicList.get(i);
            musicdHolder.bindMusic(music);
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }

}




