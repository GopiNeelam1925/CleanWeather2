package acodexm.cleanweather.view.activities;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import acodexm.cleanweather.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class WelcomeActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    //    private final String STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    @BindView(R.id.right_btn)
    ImageView rightBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if this activity is opened from MainActivity than it means user want to exit application
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }
        setContentView(R.layout.welcome_activity);
        ButterKnife.bind(this);

        //if permissions are granted go straight to MainActivity
        if (checkAndRequestPermissions()) {
            openMainActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkAndRequestPermissions()) {
            //show button (to start MainActivity) if permissions are granted
            rightBtn.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.right_btn)
    void onStartClickListener() {
        openMainActivity();
    }

    private void openMainActivity() {
        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private boolean checkAndRequestPermissions() {
        int cameraPerm = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
//        int storagePerm = ContextCompat.checkSelfPermission(this, STORAGE);
        List<String> neededPerms = new ArrayList<>();
//        if (storagePerm != PackageManager.PERMISSION_GRANTED) {
//            neededPerms.add(STORAGE);
//        }
        if (cameraPerm != PackageManager.PERMISSION_GRANTED) {
            neededPerms.add(ACCESS_FINE_LOCATION);
        }
        if (!neededPerms.isEmpty()) {
            ActivityCompat.requestPermissions(this, neededPerms.toArray(
                    new String[neededPerms.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
//                perms.put(STORAGE, PackageManager.PERMISSION_GRANTED);
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            /*&& perms.get(STORAGE) == PackageManager.PERMISSION_GRANTED*/) {
                        rightBtn.setVisibility(View.VISIBLE);
                    } else {
                        Timber.d("Some permissions are not granted ask again ");
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION) /*||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, STORAGE)*/) {
                            showDialogOK("TODO", (dialog, which) -> {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        checkAndRequestPermissions();
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            });
                        } else {
                            Toast.makeText(this, "TODO", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("CANCEL", okListener)
                .create()
                .show();
    }
}
