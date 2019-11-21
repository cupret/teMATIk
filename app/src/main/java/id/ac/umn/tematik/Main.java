package id.ac.umn.tematik;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.io.File;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Main extends Fragment {
    private RecyclerView playlistList;
    private PlaylistListAdapter playlistListAdapter = new PlaylistListAdapter();
    private NavController navController;
    private TextView companyName;

    final DisplayMetrics metrics = new DisplayMetrics();
    WindowManager windowManager;

    private MusicPlayer musicPlayer;
    private Thread thr;
    private SeekBar bar;
    private boolean barMove;
    private final int PERMISSIONS_WRITE_STORAGE= 1;
    private boolean isPlaying = false;
    private boolean isOpenPlaylist = false;

    // carousel view
    List<Promo> promos = new ArrayList<Promo>();
    CarouselView promoImg;
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            ArrayList<String> urls = promos.get(position).getImages_url();

            if(urls != null && urls.size() > 0) {
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Picasso.get().load(urls.get(0)).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navController.navigate(MainDirections.actionMainToDetailPromo(promos.get(position).getId()));
                    }
                });
            }
        }
    };

    public Main() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("asd", "hai");

        windowManager = ((Activity) getContext()).getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        // set navController
        navController =  NavHostFragment.findNavController(this);

        // set feature to refresh
        companyName = view.findViewById(R.id.judulid);
        companyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiServiceProvider.getInstance().update(getContext());
            }
        });

        // init carouselview
        // set carouselview data and behavior and height programatically
        promoImg = view.findViewById(R.id.fragment_main_promo);
        ViewGroup.LayoutParams params = promoImg.getLayoutParams();
        params.height = metrics.heightPixels;

        promoImg.setImageListener(imageListener);
        LocalDatabase.getInstance(getContext()).promoQuery().getAllLiveDataPromo().observe(this, new Observer<List<Promo>>() {
            @Override
            public void onChanged(List<Promo> inputPromos) {
                Log.d("DEBUG", "Promo list updated");
                for (Promo promo: inputPromos) {
                    Log.d("DEBUG", "Promolist onchanged: "+promo.getId());
                }

                promos = inputPromos;
                if(promos.size() > 0)
                    promoImg.setPageCount(promos.size());
                else
                    promoImg.setPageCount(0);
            }
        });

        // init playlist list
        playlistList = view.findViewById(R.id.fragment_main_playlist);
        LocalDatabase.getInstance(getContext()).playListQuery().getAllLiveDataPlayList().observe(this, new Observer<List<PlayList>>() {
            @Override
            public void onChanged(List<PlayList> playLists) {
                Log.d("DEBUG", "Playlist list updated");

                // get active playlist
                ArrayList<PlayList.Music> musics = PlayList.getActivePlaylistMusics((ArrayList<PlayList>) playLists);
                if(!musics.isEmpty()) {
                    playlistListAdapter.SetData(musics);
                    playlistListAdapter.notifyDataSetChanged();
                } else {
                    Log.e("DEBUG", "Playlist list is empty");
                }
            }
        });

        playlistList.setLayoutManager(new LinearLayoutManager(getContext()));
        playlistList.setAdapter(playlistListAdapter);

        //ask permission
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.e("asd", "req stroage granted bro2");
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_WRITE_STORAGE);
        } else {
            Log.e("asd", "stroage already granted bro2");
            initMP();
        }

    }

    /*
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.main_menu_about:
                navController.navigate(MainDirections.actionMainToAbout());
                return true;
            case R.id.main_menu_update:
                ApiServiceProvider.getInstance().update(getContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    */

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_WRITE_STORAGE : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("asd", "stroage granted bro2");
                    initMP();
                } else {
                    Log.e("asd", "stroage not granted bro2");
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    startActivity(intent);

                    getActivity().finish();
                }
            }
            return;
        }
    }

    public void initMP(){
        View view = getView();
        // nyalain media player
        final ImageButton play = view.findViewById(R.id.fragment_main_playpause);
        ImageButton next = view.findViewById(R.id.fragment_main_next);
        ImageButton prev = view.findViewById(R.id.fragment_main_prev);
        Button btn = view.findViewById(R.id.button);
        // idle video
        final VideoView vv = view.findViewById(R.id.videoView);
        final MediaController media_controller = new MediaController(this.getContext());
        vv.setMediaController(media_controller);
        File idv = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Video/" + "hMan.mp4");
        vv.setVideoURI(Uri.parse(idv.toString()));
        vv.setVisibility(View.VISIBLE);
        vv.start();
        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.setVisibility(View.INVISIBLE);
            }
        });
        vv.setVisibility(View.INVISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.setVisibility(View.VISIBLE);
            }
        });


        // playlist layout
        final ImageButton showPlaylist = view.findViewById(R.id.fragment_main_openplaylist);
        final RelativeLayout musicPlayerLayout = view.findViewById(R.id.fragment_main_musicplayer);

        if(musicPlayer == null) musicPlayer = new MusicPlayer(view,getContext());
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.mpPlay();

                isPlaying = !isPlaying;
                if(isPlaying)
                    play.setImageResource(android.R.drawable.ic_media_pause);
                else
                    play.setImageResource(android.R.drawable.ic_media_play);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.mpNext();
                bar.setMax(musicPlayer.mp.getDuration());
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.mpPrev();
                bar.setMax(musicPlayer.mp.getDuration());
            }
        });
        showPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpenPlaylist = !isOpenPlaylist;
                ViewGroup.LayoutParams params = musicPlayerLayout.getLayoutParams();

                // open music player by changing music player height into > 50dp
                // and rotate button
                if(isOpenPlaylist) {
                    params.height = (int)(metrics.heightPixels * 0.8);
                    showPlaylist.setRotation(180);
                } else {
                    params.height = (int)(50 * getResources().getDisplayMetrics().density);
                    showPlaylist.setRotation(0);
                }
                musicPlayerLayout.setLayoutParams(params);
            }
        });

        thr = new Thread(){
            @Override
            public void run() {
                Log.e("asd", "haix1");
                int totalDuration = 100;
                if(musicPlayer.canPlay) totalDuration = musicPlayer.mp.getDuration();
                int currentPosition = 0;
                bar.setMax(totalDuration);
                while (currentPosition < totalDuration) {
                    try {
                        sleep(1000);
                        if(!barMove){
                            if(musicPlayer.canPlay) currentPosition = musicPlayer.mp.getCurrentPosition();
                            bar.setProgress(currentPosition);
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        thr.start();

        bar = view.findViewById(R.id.seekBar);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
                musicPlayer.mpSeek(arg0.getProgress());
                barMove = false;
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
                barMove = true;
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                // TODO Auto-generated method stub
//                if(barMove)musicPlayer.mpSeek(arg0.getProgress());
            }
        });

    }

}
