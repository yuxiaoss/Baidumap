package com.imooc.baidumap0924;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    public static List<String> sNeedReqPermissions = new ArrayList<>();

    static {
        sNeedReqPermissions.add(Manifest.permission.READ_PHONE_STATE);
        sNeedReqPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        sNeedReqPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        sNeedReqPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private PermissionUtils mPermissionUtils;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPermissionUtils = new PermissionUtils(this);

        mPermissionUtils.request(sNeedReqPermissions, 100, new PermissionUtils.CallBack() {
            @Override
            public void grantAll() {
                toMainActivity();
                finish();
            }

            @Override
            public void denied() {
                finish();
            }
        });
    }

    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
