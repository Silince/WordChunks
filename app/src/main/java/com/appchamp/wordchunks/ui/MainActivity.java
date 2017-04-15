package com.appchamp.wordchunks.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.appchamp.wordchunks.R;
import com.appchamp.wordchunks.models.Unscramble;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity {

    private SlidingMenu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initLeftMenu();

        initRealm();
    }

    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.showContent(true);
            return;
        }

        super.onBackPressed();
    }

    public void onPlayClick(View v) {
        Intent intent = new Intent(this, PacksActivity.class);
        startActivity(intent);
    }

    public void onDailyClick(View v) {

    }

    public void onShopClick(View view) {

    }

    public void onSettingsClick(View v) {
        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        v.startAnimation(animFadeIn);
        menu.toggle();
    }

    private void initLeftMenu() {
        // Configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.settings_menu);
    }

    private void initRealm() {

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        // Load from file "initial.json" first time
        if (sharedPreferences.getBoolean("FIRST", true)) {
            InputStream stream;
            try {
                stream = getAssets().open("initial.json");
            } catch (IOException e) {
                return;
            }
            // Clear the realm from last time
            Realm.deleteRealm(realmConfiguration);
            // Create a new empty instance of Realm

            Realm realm = Realm.getInstance(realmConfiguration);

            realm.beginTransaction();
            realm.createOrUpdateObjectFromJson(Unscramble.class, stream);
            realm.commitTransaction();
            realm.close(); // Remember to close Realm when done.
            sharedPreferences.edit().putBoolean("FIRST", false).apply();
        }
    }

}