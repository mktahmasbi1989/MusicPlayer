package com.example.mohamdkazem.musicplayer;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mohamdkazem.musicplayer.model.Album;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AlbumFragment extends Fragment {

    private RecyclerView recyclerView;
    private AlbumAdaptor albumAdaptor;

    static AlbumFragment newInstance() {
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView=view.findViewById(R.id.recycle_view_albums);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        albumAdaptor = new AlbumAdaptor(MusicLab.getInstance(getContext()).getAlbumList());
        recyclerView.setAdapter(albumAdaptor);

        return view;
    }

    class AlbumHolder extends RecyclerView.ViewHolder{

        private Album mAlbum;
        private ImageView imageView;
        private TextView textViewAlbumName,textViewArtistName;


        AlbumHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view_card_artist);
            textViewAlbumName =itemView.findViewById(R.id.textView_album_name);
            imageView=itemView.findViewById(R.id.image_view_card_artist);
            textViewArtistName=itemView.findViewById(R.id.textView_artistName);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.fragment_container,MusicListDialodFragment.newInstance(mAlbum.getAlbumName()),"album")
                            .commit();
                }
            });
        }

        void bindAlbum(Album album) {
            mAlbum = album;
            textViewAlbumName.setText(album.getAlbumName());
            textViewArtistName.setText(album.getAlbumArtistKey());
            imageView.setImageDrawable(Drawable.createFromPath(album.getAlbumArtistName()));

        }
    }

    public class AlbumAdaptor extends RecyclerView.Adapter<AlbumHolder>{
        private List<Album> albumList;

        AlbumAdaptor(List<Album> albumList) {
            this.albumList = albumList;
        }

        public void setAlbumList(List<Album> albumList) {
            this.albumList = albumList;
        }


        @NonNull
        @Override
        public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.albums_holder, parent, false);
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

}
