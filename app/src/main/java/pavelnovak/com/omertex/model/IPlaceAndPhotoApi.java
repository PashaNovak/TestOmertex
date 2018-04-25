package pavelnovak.com.omertex.model;

import java.util.List;

import pavelnovak.com.omertex.model.entity.photo.PhotoResult;
import pavelnovak.com.omertex.model.entity.place.PlaceResult;
import rx.Observable;

public interface IPlaceAndPhotoApi {
    Observable<List<PlaceResult>> getPlacesResult();
    Observable<PhotoResult> getPhotosResult();
}
