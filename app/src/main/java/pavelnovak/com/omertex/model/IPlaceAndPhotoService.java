package pavelnovak.com.omertex.model;

import java.util.List;

import pavelnovak.com.omertex.model.entity.photo.PhotoResult;
import pavelnovak.com.omertex.model.entity.place.PlaceResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface IPlaceAndPhotoService {
    @GET("posts")
    Observable<List<PlaceResult>> getPlaces();

    @GET("?method=flickr.photos.getRecent&format=json&nojsoncallback=1&extras=url_z&api_key=ed95a9f903f0986df84c6b678a60914c&per_page=100&page=1")
    Observable<PhotoResult> getPhotos();
}
