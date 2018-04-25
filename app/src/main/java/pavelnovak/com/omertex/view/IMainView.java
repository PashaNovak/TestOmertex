package pavelnovak.com.omertex.view;

import java.util.List;

import pavelnovak.com.omertex.model.entity.photo.PhotoResult;
import pavelnovak.com.omertex.model.entity.place.PlaceResult;

public interface IMainView {
    void setPlaces(List<PlaceResult> placeResult);
    void setPhotos(PhotoResult photoResult);
    void hideLoadingIndicator();
    void setEmptyResponseText(String text);
    void showNoConnectionMessage();
}
