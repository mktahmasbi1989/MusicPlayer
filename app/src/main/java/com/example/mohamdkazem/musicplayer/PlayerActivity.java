package com.example.mohamdkazem.musicplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mohamdkazem.musicplayer.model.Music;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class PlayerActivity extends SingleFragmentActivity implements AllMusicFragment.CallBacks {


    private MusicPlayer musicPlayer;
    private MediaPlayer mediaPlayer;
    private ImageButton btnPlay,btnrepeate,btnNext,btnPrevious,btnShuffle,btnSearch;
    private TextView textTotalDuration, textDuration;
    private SeekBar mSeekBar;
    private int duration;
    private ConstraintLayout constraintLayout;
    private boolean flagRepeat=false,shuffle=false;
    private Long musicIndex;
    private  int listSize=1;
    private Music music;
    private androidx.appcompat.widget.Toolbar toolbar;

    private Handler mSeekBarUpdateHandler = new Handler();

    private Runnable mUpdateSeekBar = new Runnable() {
        @Override
        public void run() {
//            mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            mSeekBar.setProgress(MusicLab.getInstance(getApplicationContext()).getMediaPlayer().getCurrentPosition());
            mSeekBarUpdateHandler.postDelayed(this, 50);
            setCurrentDuration();
        }
    };

    private void setTotalDuration() {
        duration = MusicLab.getInstance(getApplicationContext()).getMediaPlayer().getDuration();
        String currTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        textTotalDuration.setText(currTime);
    }

    private void setCurrentDuration() {
//        duration = mediaPlayer.getCurrentPosition();
        duration=MusicLab.getInstance(getApplicationContext()).getMediaPlayer().getCurrentPosition();
        String currTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        textDuration.setText(currTime);
    }


    @Override
    public Fragment createFragment() {
        return PlayerFragment.newInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search People");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setIconified(false);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);

        textTotalDuration = findViewById(R.id.seek_bar_total_duration);
        textDuration = findViewById(R.id.seek_bar_duration);
        mSeekBar = findViewById(R.id.seekBar_activity);
        btnPlay = findViewById(R.id.btn_play_control);
        btnPrevious=findViewById(R.id.btnPrevious);
        btnrepeate=findViewById(R.id.btnRepeat);
        btnShuffle=findViewById(R.id.btnShuffle);
//        btnSearch=findViewById(R.id.btnSearch);
        constraintLayout=findViewById(R.id.player_controller);
        btnNext=findViewById(R.id.btnNext);

        toolbar=findViewById(R.id.tool_bar);
        toolbar.inflateMenu(R.menu.menu);

        toolbar.setOnMenuItemClickListener(new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SearchMusicFragment searchMusicFragment=SearchMusicFragment.newInstance();
                searchMusicFragment.show(getSupportFragmentManager(),"search");

                return true;
            }
        });


        musicPlayer = new MusicPlayer(getApplicationContext());
        listSize=musicPlayer.getMusicList().size();
        mediaPlayer = new MediaPlayer();

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().
                        add(R.id.activity_player,PlayerControlFragment.newInstance(MusicLab.getInstance(getApplicationContext()).getCurrentId()))
                        .commit();
            }
        });

        btnrepeate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flagRepeat){
                    flagRepeat=true;
                    btnrepeate.setBackgroundResource(R.drawable.img_btn_repeat_pressed);
                }else if (flagRepeat){
                    btnrepeate.setBackgroundResource(R.drawable.img_btn_repeat);
                    flagRepeat=false;

                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicLab.getInstance(getApplicationContext()).nextMusic();

            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicLab.getInstance(getApplicationContext()).previous();

            }
        });

//        btnShuffle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shuffleCheck();
//            }
//        });
//

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicLab.getInstance(getApplicationContext()).PlayAndPause();
                checkBtnPlay();

            }
        });

//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager=getSupportFragmentManager();
//                fragmentManager.beginTransaction().add(R.id.activity_player,AllMusicFragment.newInstance())
//                        .commit();
//                AllMusicFragment allMusicFragment=AllMusicFragment.newInstance();
//                allMusicFragment.show(getSupportFragmentManager(),"search");
//
//            }
//        });
    }

    private void checkBtnPlay() {
        if(MusicLab.getInstance(getApplicationContext()).chechPlay()){
        btnPlay.setBackgroundResource(R.drawable.btn_play);
        }else
            btnPlay.setBackgroundResource(R.drawable.btn_pause);
    }
//
//    private void shuffleCheck() {
//        if (!shuffle) {
//            shuffle = true;
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.stop();
//                Random random = new Random();
//                int random_id=random.nextInt(listSize);
//                Long randomid= Long.valueOf(random_id);
//                playMusic(randomid);
//            }
//        }
//    }

    @Override
    public void playMusic(Long musicId) {
        try {
            mediaPlayer = new MediaPlayer();
            music = musicPlayer.getMusic(musicId);
            musicIndex=musicId;
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            shuffleCheck();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(music.getUri()));
            mediaPlayer.prepare();
            mediaPlayer.start();
            if (flagRepeat){
                mediaPlayer.setLooping(true);
            }

            setTotalDuration();
            mSeekBar.setMax(mediaPlayer.getDuration());
            mUpdateSeekBar.run();
            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mSeekBar.setProgress(progress);
                        mediaPlayer.seekTo(progress);
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

        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
