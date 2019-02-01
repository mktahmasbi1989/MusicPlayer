package com.example.mohamdkazem.musicplayer;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mohamdkazem.musicplayer.model.Artist;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ArtistFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArtistAdaptor artistAdaptor;


    public static ArtistFragment newInstance() {

        Bundle args = new Bundle();

        ArtistFragment fragment = new ArtistFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public ArtistFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_artist, container, false);
        recyclerView=view.findViewById(R.id.recycle_view_artists);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        artistAdaptor = new ArtistAdaptor(MusicLab.getInstance(getContext()).getArtistList());
        recyclerView.setAdapter(artistAdaptor);
        return view;
    }

    class ArtistsHolder extends RecyclerView.ViewHolder{

        private Artist mArtist;
        private ImageView imageView;
        private TextView textViewAlbumName,textViewArtistName;

        ArtistsHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view_card_artist);
            textViewAlbumName =itemView.findViewById(R.id.textView_album_name);
            imageView=itemView.findViewById(R.id.image_view_card_artist);
            textViewArtistName=itemView.findViewById(R.id.textView_artistName);
        }

        void bindArtist(final Artist artist) {
            mArtist = artist;
            textViewAlbumName.setText(artist.getArtistName());
            textViewArtistName.setText(artist.getNumberOfAlbums()+"  album /  " +artist.getNumberOfTraks()+" trak ");
            imageView.setImageDrawable(Drawable.createFromPath(artist.getArtistArtPath()));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.fragment_container,ArtistMusicListFragment.newInstance(artist.getArtistName()),"artist")
                            .commit();
                }
            });
        }
    }



    public class ArtistAdaptor extends RecyclerView.Adapter<ArtistsHolder>{
        private List<Artist> artistList;


        ArtistAdaptor(List<Artist> artistList) {
            this.artistList = artistList;
        }

        public void setArtistList(List<Artist> artistList) {
            this.artistList = artistList;
        }


        @NonNull
        @Override
        public ArtistsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.albums_holder, parent, false);
            return new ArtistsHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ArtistsHolder holder, int position) {
            Artist artist = artistList.get(position);
            holder.bindArtist(artist);
        }

        @Override
        public int getItemCount() {
            return artistList.size();
        }
    }

}
