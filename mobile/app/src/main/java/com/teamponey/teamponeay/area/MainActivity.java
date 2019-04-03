package com.teamponey.teamponeay.area;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.teamponey.teamponeay.area.Fragments.ProfileFrag;
import com.teamponey.teamponeay.area.Fragments.SettingsFrag;
import com.teamponey.teamponeay.area.Fragments.WidgetsFrag;
import com.teamponey.teamponeay.area.Services.WidgetService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private static MainActivity instance;

    private Fragment fragmentWidgets;
    private Fragment fragmentProfile;
    private Fragment fragmentSettings;

    private static final int FRAGMENT_WIDGETS = 0;
    private static final int FRAGMENT_PROFILE = 1;
    private static final int FRAGMENT_SETTINGS = 2;

    private static String ACCESS_TOKEN_KEY = "access_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        instance = this;

        Bundle bundle = getIntent().getExtras();
        String token;

        if (bundle != null && Constants.getClientToken() == null) {
            token = bundle.getString(ACCESS_TOKEN_KEY);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(ACCESS_TOKEN_KEY, token);
            editor.apply();
        }

        this.showFragment(FRAGMENT_WIDGETS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_widgets :
                this.showFragment(FRAGMENT_WIDGETS);
                break;
            case R.id.activity_main_drawer_profile:
                this.showFragment(FRAGMENT_PROFILE);
                break;
            case R.id.activity_main_drawer_settings:
                this.showFragment(FRAGMENT_SETTINGS);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configureToolBar(){
        this.toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        this.navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_PROFILE :
                this.showProfileFragment();
                break;
            case FRAGMENT_WIDGETS:
                this.showWidgetsFragment();
                break;
            case FRAGMENT_SETTINGS:
                this.showSettingsFragment();
                break;
            default:
                break;
        }
    }

    private void showProfileFragment(){
        if (this.fragmentProfile == null) this.fragmentProfile = ProfileFrag.newInstance();
        this.startTransactionFragment(this.fragmentProfile);
    }

    private void showSettingsFragment(){
        if (this.fragmentSettings == null) this.fragmentSettings = SettingsFrag.newInstance();
        this.startTransactionFragment(this.fragmentSettings);
    }

    private void showWidgetsFragment(){
        if (this.fragmentWidgets == null) this.fragmentWidgets = WidgetsFrag.newInstance();
        this.startTransactionFragment(this.fragmentWidgets);
    }

    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }

    public static MainActivity getInstance() {
        return instance;
    }

}