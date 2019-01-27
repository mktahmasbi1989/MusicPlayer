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
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.mohamdkazem.musicplayer.model.Music;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class PlayerFragment extends Fragment {

    private static final String MUSIC_FOLDER = "chaartaar";
    private RecyclerView mRecyclerView;
    private MusicPlayer musicPlayer;
    private MusicAdaptor musicAdaptor;
    private MediaPlayer mediaPlayer;
    private AssetManager assetManager;
    private ImageButton btnStop,btnPause;
    private int duration;
    private TextView textTotalDurataion,textDuration;
    private SeekBar mSeekBar;
    private Handler mSeekbarUpdateHandler = new Handler();



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
        mSeekBar=view.findViewById(R.id.songProgressBar);
        btnPause=view.findViewById(R.id.btnPlay);
        textTotalDurataion=view.findViewById(R.id.songTotalDurationLabel);
        mRecyclerView = view.findViewById(R.id.beat_box_recycler_view);
        textDuration=view.findViewById(R.id.songCurentDurationLabel);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        musicAdaptor=new MusicAdaptor(musicPlayer.getMusicList());
        mRecyclerView.setAdapter(musicAdaptor);

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer!=null){
                    mediaPlayer.pause();
                    btnPause.setBackgroundResource(R.drawable.btn_play);
                }
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
                                btnPause.setBackgroundResource(R.drawable.btn_pause);
                                setTotalDuration();
                                mSeekBar.setMax(mediaPlayer.getDuration());
                                mUpdateSeekbar.run();

                                mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        if (fromUser){
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
        MusicAdaptor(List<Music> musicList) {
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
    private void setTotalDuration() {
        duration=mediaPlayer.getDuration();
        String currTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        textTotalDurataion.setText(currTime);
    }
    private void setCurrentDuration(){
        duration=mediaPlayer.getCurrentPosition();
        String currTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        textDuration.setText(currTime);
    }
    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            mSeekbarUpdateHandler.postDelayed(this, 50);
            setCurrentDuration();
        }
    };

    }




