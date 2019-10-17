package id.ac.umn.tematik;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Main extends Fragment {
    private RecyclerView promoList;
    private PromoListAdapter promoListAdapter;
    private LinearLayoutManager layoutManager;
    private NavController navController;

    private MusicPlayer musicPlayer;
    private Thread thr;
    private SeekBar bar;
    private boolean barMove;
    private final int PERMISSIONS_WRITE_STORAGE= 1;

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

        // set toolbar
        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setActionBar(toolbar);

        // set navController
        navController =  NavHostFragment.findNavController(this);

        // init recycle list
        promoList = view.findViewById(R.id.fragment_main_list);
        layoutManager = new LinearLayoutManager(getContext());
        promoListAdapter = new PromoListAdapter(navController);
        LocalDatabase.getInstance(getContext()).promoQuery().getAllLiveDataPromo().observe(this, new Observer<List<Promo>>() {
            @Override
            public void onChanged(List<Promo> promos) {
                Log.d("DEBUG", "Promo list updated");
                for (Promo promo: promos) {
                    Log.d("DEBUG", "Promolist onchanged: "+promo.getId());
                }
                promoListAdapter.SetData(promos);
                promoListAdapter.notifyDataSetChanged();
            }
        });

        promoList.setLayoutManager(layoutManager);
        promoList.setAdapter(promoListAdapter);

        //ask permission
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.e("asd", "req stroage granted bro2");
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_WRITE_STORAGE);
        } else {
            Log.e("asd", "stroage already granted bro2");
            initMP();
        }

    }

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
        ImageButton play = view.findViewById(R.id.fragment_main_playpause);
        ImageButton next = view.findViewById(R.id.fragment_main_next);
        ImageButton prev = view.findViewById(R.id.fragment_main_prev);

        if(musicPlayer == null) musicPlayer = new MusicPlayer(view,getContext());
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.mpPlay();
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
