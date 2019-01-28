package com.example.mohamdkazem.musicplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mohamdkazem.musicplayer.model.Artist;
import com.example.mohamdkazem.musicplayer.model.Music;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ArtistFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private MusicPlayer musicPlayer;
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
        musicPlayer = new MusicPlayer(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_artist, container, false);
        recyclerView=view.findViewById(R.id.recycle_view_artists);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        artistAdaptor = new ArtistAdaptor(musicPlayer.getArtistList());
        recyclerView.setAdapter(artistAdaptor);
        return view;
    }

    public class ArtistsHolder extends RecyclerView.ViewHolder{

        private Button mButton;
        private Artist mArtist;

        public ArtistsHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.list_item_sound_button);

        }


        public void bindArtist(Artist artist) {
            mArtist = artist;
            mButton.setText(artist.getArtistName());
        }
    }



    public class ArtistAdaptor extends RecyclerView.Adapter<ArtistsHolder>{
        private List<Artist> artistList;


        public ArtistAdaptor(List<Artist> artistList) {
            this.artistList = artistList;
        }

        public void setArtistList(List<Artist> artistList) {
            this.artistList = artistList;
        }


        @NonNull
        @Override
        public ArtistsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_sound, parent, false);
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




    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
