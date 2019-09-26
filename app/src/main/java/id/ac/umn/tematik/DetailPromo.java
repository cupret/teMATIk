package id.ac.umn.tematik;


import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPromo extends Fragment {
    private int promoId;
    private Promo promo;

    TextView name;
    TextView date;
    TextView desc;
    LinearLayout imageLayout;

    public DetailPromo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_promo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set toolbar
        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setActionBar(toolbar);
        ((MainActivity)getActivity()).setHomeButton(android.R.drawable.ic_delete);

        name = view.findViewById(R.id.promo_name);
        date = view.findViewById(R.id.promo_date);
        desc = view.findViewById(R.id.promo_desc);
        imageLayout = (LinearLayout) view.findViewById(R.id.promo_image_layout);

        // access promoId
        promoId = getArguments() != null ? DetailPromoArgs.fromBundle(getArguments()).getPromoId() : 0;

        new fetchPromo().execute(promoId);

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

    class fetchPromo extends AsyncTask<Integer, Void, Promo>{

        @Override
        protected Promo doInBackground(Integer... promoId) {
            promo = LocalDatabase.getInstance(getContext()).promoQuery().getPromo(promoId[0]);
            return promo;
        }

        @Override
        protected void onPostExecute(Promo promo) {
            super.onPostExecute(promo);

            name.setText(promo.getName());
            date.setText(promo.getStart_date()+"-"+promo.getEnd_date());
            desc.setText(promo.getDescription());
            for(int i=0;i<promo.getImages_url().size();i++) new fetchImage().execute(promo.getImages_url().get(i));
        }
    }

    class fetchImage extends AsyncTask<String, Void, Drawable>{

        @Override
        protected Drawable doInBackground(String... imgUrl) {
            Drawable drawImage = null;
            try{
                InputStream imgContent = (InputStream) new URL(imgUrl[0]).getContent();
                drawImage = Drawable.createFromStream(imgContent, "src");
            } catch (MalformedURLException e) {
                Log.e("MALFORMED", "MalformedURLException: " + e.getMessage());
            } catch (IOException e) {
                Log.e("IO", "IOException: " + e.getMessage());
            }
            return drawImage;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);

            ImageView image = new ImageView(getContext());
            image.setMaxHeight(128);
            image.setMaxWidth(128);
            image.setImageDrawable(drawable);
            imageLayout.addView(image);
        }
    }
}
