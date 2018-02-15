package acodexm.cleanweather.repository;


import android.arch.lifecycle.LiveData;

import acodexm.cleanweather.data.model.search.SearchData;

public interface SearchRepository {
    LiveData<SearchData> getSearchData();
}
