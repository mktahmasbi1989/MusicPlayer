package com.example.mohamdkazem.musicplayer;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mohamdkazem.musicplayer.model.Music;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistMusicListFragment extends Fragment implements AllMusicFragment.CallBacks {

    private static final String ARTIST_NAME = "ArtistName";
    private String artist_name;
    private RecyclerView recyclerView;
    private ArtistListAdaptor artistListAdaptor;
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



    public static ArtistMusicListFragment newInstance(String artistName) {

        Bundle args = new Bundle();
        args.putString(ARTIST_NAME, artistName);

        ArtistMusicListFragment fragment = new ArtistMusicListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artist_name = getArguments().getString(ARTIST_NAME);
    }

    public ArtistMusicListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_artist_music_list, container, false);
        recyclerView= view.findViewById(R.id.recycle_view_Artist_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        artistListAdaptor = new ArtistListAdaptor(MusicLab.getInstance(getContext()).getArtistMusicList(artist_name));
        recyclerView.setAdapter(artistListAdaptor);

        return view;
    }

    @Override
    public void playMusic(Long musicId) {

    }

    private class MusicHolderArtist extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayout;
        private TextView textView;
        private ImageView imageView;
        private Music music;

        MusicHolderArtist(@NonNull View itemView) {
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

    public class ArtistListAdaptor extends RecyclerView.Adapter<MusicHolderArtist>{
        private List<Music> musicList;

        public ArtistListAdaptor(List<Music> musicList) {
            this.musicList = musicList;
        }

        @NonNull
        @Override
        public MusicHolderArtist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.music_holder, parent, false);
            return new MusicHolderArtist(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MusicHolderArtist holder, int position) {
            Music music = musicList.get(position);
            holder.bindMusic(music);
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }
}
