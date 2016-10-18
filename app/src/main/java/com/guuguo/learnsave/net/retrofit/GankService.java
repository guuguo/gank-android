package com.guuguo.learnsave.net.retrofit;

import com.guuguo.learnsave.bean.GankDays;
import com.guuguo.learnsave.bean.Ganks;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by gaohailong on 2016/5/17.
 */
public interface GankService {
    @GET("data/{type}/{count}/{page}")
    Observable<Ganks> getGanHuo(@Path("type") String type, @Path("count") int count, @Path("page") int page);

    @GET("day/{date}")
    Observable<GankDays> getGankOneDay(@Path("date") String date);
    
    @GET("day/{history}")
    Observable<GankDays> getGankDates();
}
