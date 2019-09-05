package id.ac.umn.tematik;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiServiceProvider.getInstance();

        ApiServiceProvider.getProduk();
        ApiServiceProvider.getPromo();
        ApiServiceProvider.getPlayList();

    }

    public void setActionBar(Toolbar toolbar){
        setSupportActionBar(toolbar);
    }

    public void setHomeButton(int icon){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(icon);
    }
}
