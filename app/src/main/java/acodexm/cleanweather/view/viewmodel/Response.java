package acodexm.cleanweather.view.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.netwoking.NetworkError;

import static acodexm.cleanweather.view.viewmodel.Status.ERROR;
import static acodexm.cleanweather.view.viewmodel.Status.LOADING;
import static acodexm.cleanweather.view.viewmodel.Status.SUCCESS;


public class Response {

    public final Status status;

    @Nullable
    public final WeatherData data;

    @Nullable
    public final NetworkError error;

    private Response(Status status, @Nullable WeatherData data, @Nullable NetworkError error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static Response loading() {
        return new Response(LOADING, null, null);
    }

    public static Response success(@NonNull WeatherData data) {
        return new Response(SUCCESS, data, null);
    }

    public static Response error(@NonNull NetworkError error) {
        return new Response(ERROR, null, error);
    }
}
