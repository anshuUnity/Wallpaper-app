package com.hangzup.worldofmeters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hangzup.worldofmeters.fragments.CreatorFragment;
import com.hangzup.worldofmeters.fragments.HomeFragment;
import com.hangzup.worldofmeters.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{

    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new CreatorFragment();
    final Fragment fragment3 = new ProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    BottomNavigationView bottomNavigationView;
    private long back_press_button;
    private Fragment mFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        if(null == savedInstanceState){
            fm.beginTransaction().add(R.id.frame_container, fragment3, "frag1").hide(fragment3).commit();
            fm.beginTransaction().add(R.id.frame_container, fragment2, "frag2").hide(fragment2).commit();
            fm.beginTransaction().add(R.id.frame_container,fragment1, "frag3").commit();
        }


    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
        switch(item.getItemId()){
            case R.id.homeFragment2:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;
            case R.id.creatorFragment3:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;

            case R.id.profileFragment3:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    return true;

        }
        return false;
    };

//    public void prevnt_infite(String tag, Fragment mFrag){
//        mFrag = getSupportFragmentManager().findFragmentByTag(tag);
//        if(mFrag == null){
//            Log.d("TAG INFINITE", "prevnt_infite: "+getSupportFragmentManager().findFragmentByTag(tag));
//            getSupportFragmentManager().beginTransaction().attach(mFrag);
//        }
//    }

//    public void managefragment(Fragment fragment, Fragment active_frag, String Tag){
//        //Get current fragment placed in container
//        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
//        if(currentFragment.getClass() == fragment.getClass()){
//            return;
//        }
//
//        //If fragment is already on stack, we can pop back stack to prevent stack infinite growth
//        if(getSupportFragmentManager().findFragmentByTag(Tag) != null){
//            getSupportFragmentManager().popBackStack(Tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        }
//
//        //Otherwise, just replace fragment
//
////        getSupportFragmentManager().
////                beginTransaction().
////                addToBackStack(Tag).
////                replace(R.id.frame_container, fragment, Tag).
////                commit();
//
//        getSupportFragmentManager().beginTransaction().hide(active_frag).show(fragment).addToBackStack(Tag).commit();
//
//    }



    @Override
    public void onBackPressed() {
        if(bottomNavigationView.getSelectedItemId() == R.id.homeFragment2){
            if(back_press_button + 2000 > System.currentTimeMillis()){
                super.onBackPressed();
                return;
            }else{
                Toast.makeText(this, "Press Again to exit", Toast.LENGTH_LONG).show();
            }

            back_press_button = System.currentTimeMillis();

        }else {
            bottomNavigationView.setSelectedItemId(R.id.homeFragment2);
        }
    }

    @Override
    public void onBackStackChanged() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        for(int i = count-1; i >=0 ; i--){
                FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(i);
            Log.d("STACK STATUS", "onBackStackChanged: "+entry);
        }
    }
}