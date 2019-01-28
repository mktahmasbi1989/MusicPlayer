package com.example.mohamdkazem.musicplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mohamdkazem.musicplayer.model.Album;
import com.example.mohamdkazem.musicplayer.model.Artist;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlbumFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AlbumFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private AlbumAdaptor albumAdaptor;
    private MusicPlayer musicPlayer;

    public static AlbumFragment newInstance() {
        Bundle args = new Bundle();

        AlbumFragment fragment = new AlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicPlayer = new MusicPlayer(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView=view.findViewById(R.id.recycle_view_albums);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        albumAdaptor = new AlbumAdaptor(musicPlayer.getAlbumList());
        recyclerView.setAdapter(albumAdaptor);

        return view;
    }

    public class AlbumHolder extends RecyclerView.ViewHolder{

        private Button button;
        private Album mAlbum;

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.list_item_sound_button);

        }

        public void bindAlbum(Album album) {
            mAlbum = album;
            button.setText(album.getAlbumName());
        }
    }

    public class AlbumAdaptor extends RecyclerView.Adapter<AlbumHolder>{
        private List<Album> albumList;

        public AlbumAdaptor(List<Album> albumList) {
            this.albumList = albumList;
        }

        public void setAlbumList(List<Album> albumList) {
            this.albumList = albumList;
        }


        @NonNull
        @Override
        public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_sound, parent, false);
            return new AlbumHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
            Album album=albumList.get(position);
            holder.bindAlbum(album);
        }

        @Override
        public int getItemCount() {
            return albumList.size();
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
