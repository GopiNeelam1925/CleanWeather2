package acodexm.cleanweather.repository;


import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import za.co.riggaroo.datecountdown.data.entity.Event;


public interface EventRepository {

    Completable addEvent(Event event);

    LiveData<List<Event>> getEvents();

    Completable deleteEvent(Event event);


}
