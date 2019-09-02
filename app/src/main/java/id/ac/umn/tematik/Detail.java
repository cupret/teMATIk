package id.ac.umn.tematik;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Detail extends Fragment {
    int promoId;

    public Detail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set toolbar
        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setActionBar(toolbar);
        ((MainActivity)getActivity()).setHomeButton(android.R.drawable.ic_delete);

        // access promoId
        promoId = getArguments() != null ? DetailArgs.fromBundle(getArguments()).getPromoId() : 0;

        TextView textView = view.findViewById(R.id.textView);
        textView.setText("Detail " + promoId);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
