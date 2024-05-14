package com.techindia.mgggggggg.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.techindia.mgggggggg.Fragments.HomeFragment;
import com.techindia.mgggggggg.Fragments.JobFragment;
import com.techindia.mgggggggg.R;

public class DashBoardActivity extends AppCompatActivity {
    BottomNavigationView bottomview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        inits();

        bottomview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.navigation_home)
                {
                    loadFragment(new HomeFragment(),true);
                }
                else if(id==R.id.navigation_job)
                {
                   loadFragment(new JobFragment(),false);

                }

                return true;
            }
        });
        bottomview.setSelectedItemId(R.id.navigation_home);
    }

    private void inits() {
        bottomview=findViewById(R.id.bottomview);
    }
    public void loadFragment(Fragment fragment,boolean flag)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction  fragmentTransaction=fragmentManager.beginTransaction();
//        if(flag) {
//            fragmentTransaction.add(R.id.container, fragment);
//        } else {
            fragmentTransaction.replace(R.id.container, fragment);
      //
        //
        //  }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}