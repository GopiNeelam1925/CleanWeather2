package acodexm.cleanweather.netwoking;


import acodexm.cleanweather.data.model.forecast.WeatherDataForecast;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    //    http://api.apixu.com/v1/forecast.json?key=e9a7857a3f2d4e3596c145511161711&q=warszawa&days=7&lang=pl
    @GET("forecast.json?")
    Observable<WeatherDataForecast> getWeatherDataForecast(@Query("key") String key,
                                                           @Query("q") String location,
                                                           @Query("days") int days,
                                                           @Query("lang") String lang);

}
