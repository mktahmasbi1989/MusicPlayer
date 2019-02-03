package com.example.mohamdkazem.musicplayer;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.mohamdkazem.musicplayer.model.Music;
import java.util.concurrent.TimeUnit;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerControlFragment extends Fragment {
    private static final String MUSIC_ID = "misicId";
    private ImageButton btnBack,btnPlay,btnNext,btnPrevious,btnrepeate,btnShuffle,btnFavorite;
    private TextView textViewMusicTitle,totalDuration,currentDuration;
    private Music music;
    private Long music_id;
    private SeekBar mSeekBar;
    private int duration;
    private RecyclerView recyclerView;
    private boolean favoriteState;



    private Handler mSeekBarUpdateHandler = new Handler();

    private Runnable mUpdateSeekBar = new Runnable() {
        @Override
        public void run() {
            mSeekBar.setProgress(MusicLab.getInstance(getActivity()).getMediaPlayer().getCurrentPosition());
            mSeekBar.setProgress(MusicLab.getInstance(getActivity()).getMediaPlayer().getCurrentPosition());
            mSeekBarUpdateHandler.postDelayed(this, 50);
            setCurrentDuration();
        }
    };

    private void setTotalDuration() {
        duration = MusicLab.getInstance(getActivity()).getMediaPlayer().getDuration();
        String currTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        totalDuration.setText(currTime);
    }
    private void setCurrentDuration() {
        duration = MusicLab.getInstance(getActivity()).getMediaPlayer().getCurrentPosition();
        duration=MusicLab.getInstance(getActivity()).getMediaPlayer().getCurrentPosition();
        String currTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        currentDuration.setText(currTime);
    }

    public PlayerControlFragment() {
        // Required empty public constructor
    }

    public static PlayerControlFragment newInstance(Long musicId) {
        
        Bundle args = new Bundle();
        args.putLong(MUSIC_ID,musicId);
        PlayerControlFragment fragment = new PlayerControlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        music_id=getArguments().getLong(MUSIC_ID);
        music=MusicLab.getInstance(getActivity()).getMusic(music_id);
        favoriteState=music.isFavorite();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_player_control, container, false);
        btnBack=view.findViewById(R.id.btn_back);
        textViewMusicTitle=view.findViewById(R.id.songTitle);
        btnPlay=view.findViewById(R.id.btnPlayControlPage);
        btnNext=view.findViewById(R.id.btnNext);
        btnPrevious=view.findViewById(R.id.btnPrevious);
        totalDuration=view.findViewById(R.id.songTotalDurationLabel);
        currentDuration=view.findViewById(R.id.songCurentDurationLabel);
        mSeekBar=view.findViewById(R.id.songProgressBar);
        btnFavorite=view.findViewById(R.id.btnFavorite);
        recyclerView=view.findViewById(R.id.beat_box_recycler_view);

        setFavoriteButton();


        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoriteState) {

                    MusicLab.getInstance(getActivity()).setFavoiteMusic(music_id, false);
                    favoriteState=false;
                    setFavoriteButton();

                }else
                    MusicLab.getInstance(getActivity()).setFavoiteMusic(music_id,true);
                    favoriteState=true;
                    setFavoriteButton();

            }
        });
        setTotalDuration();

        mSeekBar.setMax(MusicLab.getInstance(getActivity()).getMediaPlayer().getDuration());
        mUpdateSeekBar.run();
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mSeekBar.setProgress(progress);
                    MusicLab.getInstance(getActivity()).getMediaPlayer().seekTo(progress);
                    mUpdateSeekBar.run();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.getProgress();
            }
        });



        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicLab.getInstance(getActivity()).previous();

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicLab.getInstance(getActivity()).nextMusic();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicLab.getInstance(getActivity()).PlayAndPause();

            }
        });

        textViewMusicTitle.setText(music.getTitle());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .remove(getFragmentManager()
                                .findFragmentById(R.id.activity_player))
                        .commit();
            }
        });

        return view;
    }

    private void setFavoriteButton() {
        favoriteState=music.isFavorite();
        if (favoriteState){
            btnFavorite.setBackgroundResource(R.drawable.icons_heart_outline);
        }if (!favoriteState)
            btnFavorite.setBackgroundResource(R.drawable.favoteicons);
    }

}
