package acodexm.cleanweather.netwoking;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import acodexm.cleanweather.util.Constants;
import retrofit2.HttpException;
import timber.log.Timber;

public class NetworkError extends Throwable {
    private final Throwable error;

    public NetworkError(Throwable e) {
        super(e);
        this.error = e;
    }

    public String getMessage() {
        return error.getMessage();
    }

    public String getAppErrorMessage() {
        if (this.error instanceof IOException) return Constants.NETWORK_ERROR_MESSAGE;
        if (!(this.error instanceof HttpException)) return Constants.DEFAULT_ERROR_MESSAGE;
        retrofit2.Response<?> response = ((HttpException) this.error).response();
        if (response != null) {
            String status = getJsonStringFromResponse(response);
            if (!TextUtils.isEmpty(status)) return status;

            Map<String, List<String>> headers = response.headers().toMultimap();
            if (headers.containsKey(Constants.ERROR_MESSAGE_HEADER))
                return headers.get(Constants.ERROR_MESSAGE_HEADER).get(0);
        }

        return Constants.DEFAULT_ERROR_MESSAGE;
    }

    private String getJsonStringFromResponse(final retrofit2.Response<?> response) {
        try {
            String jsonString = response.errorBody().string();
            ErrorResponse errorResponse = new Gson().fromJson(jsonString, ErrorResponse.class);
            return errorResponse.getError().getMessage();
        } catch (Exception e) {
            Timber.e("JsonStringFromResponse %s", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkError that = (NetworkError) o;

        return error != null ? error.equals(that.error) : that.error == null;

    }

    @Override
    public int hashCode() {
        return error != null ? error.hashCode() : 0;
    }
}
