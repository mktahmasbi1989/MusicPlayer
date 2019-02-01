package com.example.mohamdkazem.musicplayer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamdkazem.musicplayer.model.Music;

import java.util.Calendar;
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
public class MusicListDialodFragment extends DialogFragment implements AllMusicFragment.CallBacks {


    private static final String ALBUM_ID = "albumId";
    private String albumName;
    private RecyclerView recyclerView;
    private MusicAdaptorDialog musicAdaptor;
    private MusicPlayer musicPlayer;
    private MediaPlayer mediaPlayer;
    private AllMusicFragment.CallBacks mCallBacks;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallBacks = (AllMusicFragment.CallBacks) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks=null;
    }



    public static MusicListDialodFragment newInstance(String albumName) {

        Bundle args = new Bundle();
        args.putString(ALBUM_ID, albumName);
        MusicListDialodFragment fragment = new MusicListDialodFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MusicListDialodFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        albumName = getArguments().getString(ALBUM_ID);
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

    @Override
    public void playMusic(Long musicId) {

    }


    private class MusicHolderDialog extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayout;
        private TextView textView;
        private ImageView imageView;
        private Music music;

        MusicHolderDialog(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.holder);
            textView=itemView.findViewById(R.id.textView);
            imageView=itemView.findViewById(R.id.image_view);
            constraintLayout=itemView.findViewById(R.id.holder);

        }
        void bindMusic(Music musi) {
            music = musi;
            textView.setText(music.getTitle());
            imageView.setImageURI(Uri.parse(music.getImageUri()));
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBacks.playMusic(music.getMusicId());
                    getFragmentManager().beginTransaction()
                            .remove(getFragmentManager()
                                    .findFragmentById(R.id.fragment_container))
                            .commit();

                }
            });
        }
    }
    public class MusicAdaptorDialog extends RecyclerView.Adapter<MusicHolderDialog>{

        private List<Music> musicList;

        MusicAdaptorDialog(List<Music> musicList) {
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
