package pavelnovak.com.omertex.presenter.impl;

import java.util.List;

import pavelnovak.com.omertex.model.entity.photo.PhotoResult;
import pavelnovak.com.omertex.model.entity.place.PlaceResult;
import pavelnovak.com.omertex.presenter.IMainPresenter;
import pavelnovak.com.omertex.view.IMainView;
import pavelnovak.com.omertex.model.impl.PlaceAndPhotoApi;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter implements IMainPresenter {
    private final PlaceAndPhotoApi _placeAndPhotoApi;
    private final IMainView _view;

    public MainPresenter(IMainView view) {
        _placeAndPhotoApi = new PlaceAndPhotoApi();
        _view = view;
    }

    @Override
    public void getPlacesData() {
        Observable<List<PlaceResult>> placesData = _placeAndPhotoApi.getPlacesResult();

        placesData.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(placeResults -> {
                    if (placeResults == null) _view.setEmptyResponseText("There is no place data");
                    else {
                        _view.hideLoadingIndicator();
                        _view.setPlaces(placeResults);
                    }
                });
    }

    @Override
    public void getPhotosData() {
        Observable<PhotoResult> photosData = _placeAndPhotoApi.getPhotosResult();

        photosData.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoResult -> {
                    if (photoResult == null) _view.setEmptyResponseText("There is no photo data");
                    else _view.setPhotos(photoResult);
                });
    }
}
