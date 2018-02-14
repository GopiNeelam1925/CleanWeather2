package acodexm.cleanweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.Stetho;

import acodexm.cleanweather.dependences.DaggerDeps;
import acodexm.cleanweather.dependences.Deps;
import acodexm.cleanweather.netwoking.NetworkModule;

public class BaseApp extends AppCompatActivity {


    private Deps mDeps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        mDeps = DaggerDeps.builder().networkModule(new NetworkModule()).build();
    }

    public Deps getDeps() {
        return mDeps;
    }

}
