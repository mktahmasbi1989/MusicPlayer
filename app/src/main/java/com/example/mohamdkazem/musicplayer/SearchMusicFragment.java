package com.example.mohamdkazem.musicplayer;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamdkazem.musicplayer.model.Music;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMusicFragment extends DialogFragment {
    private EditText editText;
    private RecyclerView recyclerView;
    private SearchMusicAdaptor searchMusicAdaptor;


    public SearchMusicFragment() {
        // Required empty public constructor
    }

    public static SearchMusicFragment newInstance() {

        Bundle args = new Bundle();

        SearchMusicFragment fragment = new SearchMusicFragment();
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search_music, container, false);
        editText=view.findViewById(R.id.edit_query);
        recyclerView=view.findViewById(R.id.recycle_view_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchMusicAdaptor = new SearchMusicAdaptor(MusicLab.getInstance(getContext()).getMusicList());
        recyclerView.setAdapter(searchMusicAdaptor);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Music> list = MusicLab.getInstance(getContext()).getMusicList();
                List<Music> searchTaskList = new ArrayList<>();
                if (s != null || !s.equals("")) {
                    for (int i = 0; i < list.size(); i++) {

                            if (list.get(i).getArtistName().contentEquals(s) || list.get(i).getTitle().contains(s)) {
                                searchTaskList.add(list.get(i));
                        }
                    }
                    searchMusicAdaptor.setMusicList(searchTaskList);
                    searchMusicAdaptor.notifyDataSetChanged();

                } else{

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view ;
    }


    private class MusicHolder extends RecyclerView.ViewHolder {
        private Music mMusic;
        private TextView textView;
        private ImageView imageView;
        private ConstraintLayout constraintLayout;

        MusicHolder(@NonNull final View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            imageView=itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        MusicLab.getInstance(getActivity()).playMusic(mMusic.getMusicId(),getActivity());
//                        mCallBacks.playMusic(mMusic.getMusicId());

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        void bindMusic(Music music) {
            mMusic = music;
            textView.setText(music.getTitle());
            imageView.setImageURI(Uri.parse(mMusic.getImageUri()));
        }
    }

    public class SearchMusicAdaptor extends RecyclerView.Adapter<MusicHolder>{
        private List<Music> musicList;

        public SearchMusicAdaptor(List<Music> musicList) {
            this.musicList = musicList;
        }

        public void setMusicList(List<Music> musicList) {
            this.musicList = musicList;
        }

        @NonNull
        @Override
        public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.music_holder, parent, false);
            return new MusicHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
            Music music = musicList.get(position);
            holder.bindMusic(music);
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }

}
