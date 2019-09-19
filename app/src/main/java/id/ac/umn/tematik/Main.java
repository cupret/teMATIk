package id.ac.umn.tematik;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Main extends Fragment {
    RecyclerView promoList;
    PromoListAdapter promoListAdapter;
    LinearLayoutManager layoutManager;

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

        // set toolbar
        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setActionBar(toolbar);

        // init recycle list
        promoList = view.findViewById(R.id.fragment_main_list);
        layoutManager = new LinearLayoutManager(getContext());
        promoListAdapter = new PromoListAdapter();
        LocalDatabase.getInstance(getContext()).promoQuery().getAllLiveDataPromo().observe(this, new Observer<List<Promo>>() {
             @Override
             public void onChanged(List<Promo> promos) {
                promoListAdapter.SetData(promos);
                promoListAdapter.notifyDataSetChanged();
             }
        });

        promoList.setLayoutManager(layoutManager);
        promoList.setAdapter(promoListAdapter);
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
                NavHostFragment.findNavController(this).navigate(MainDirections.actionMainToAbout());
                return true;
                default:
                    return super.onOptionsItemSelected(item);

        }
    }
}
