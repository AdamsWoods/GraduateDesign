package com.example.news.setting;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.news.R;
import com.example.news.feedback.FeedBackActivity;
import com.example.news.util.ClearCacheUtil;

import de.psdev.licensesdialog.LicensesDialog;

public class SettingActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_act);

        //set status block color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction().replace(R.id.setting, new PrefsFragment()).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public static class PrefsFragment extends PreferenceFragment
            implements Preference.OnPreferenceClickListener{

        Preference cache;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_headers);
            Preference protocal = findPreference("pref_key_protocol");
            protocal.setOnPreferenceClickListener(this);
            Preference feedback = findPreference("pref_key_feedback");
            feedback.setOnPreferenceClickListener(this);
            cache = findPreference("pref_key_cache");
            cache.setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            switch (preference.getKey()){
                case "pref_key_protocol":
                    showApacheLicenseDialog();
                    break;
                case "pref_key_feedback":
                    Intent intent = new Intent(getActivity(), FeedBackActivity.class);
                    startActivity(intent);
//                    getActivity().finish();
                    break;
                case "pref_key_cache":
                    ClearCacheUtil.deleteDir(getActivity().getCacheDir());
                    cache.setSummary(ClearCacheUtil.getCacheSize());
                    Toast.makeText(getActivity(),"清除缓存成功", Toast.LENGTH_LONG).show();
                    break;
            }
            return false;
        }

        private void showApacheLicenseDialog() {
            new LicensesDialog
                    .Builder(getActivity())
                    .setNotices(R.raw.notices)
                    .setIncludeOwnLicense(true)
                    .setDividerColorId(R.color.colorPrimary)
                    .build()
                    .showAppCompat();
        }

    }
}
