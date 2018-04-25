package pavelnovak.com.omertex.model.impl;

import java.util.List;

import pavelnovak.com.omertex.model.IPlaceAndPhotoService;
import pavelnovak.com.omertex.model.IPlaceAndPhotoApi;
import pavelnovak.com.omertex.model.entity.photo.PhotoResult;
import pavelnovak.com.omertex.model.entity.place.PlaceResult;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class PlaceAndPhotoApi implements IPlaceAndPhotoApi {

    @Override
    public Observable<List<PlaceResult>> getPlacesResult() {
        return createRetrofit("https://jsonplaceholder.typicode.com/").create(IPlaceAndPhotoService.class).getPlaces();
        }

    @Override
    public Observable<PhotoResult> getPhotosResult() {
        return createRetrofit("https://api.flickr.com/services/rest/").create(IPlaceAndPhotoService.class).getPhotos();
    }

    private Retrofit createRetrofit(String URL){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build();
    }
}
