package pavelnovak.com.omertex.view.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pavelnovak.com.omertex.adapter.UnionRVAdapter;
import pavelnovak.com.omertex.listener.RecyclerClickListener;
import pavelnovak.com.omertex.model.entity.Union;
import pavelnovak.com.omertex.model.entity.photo.PhotoResult;
import pavelnovak.com.omertex.model.entity.place.PlaceResult;
import pavelnovak.com.omertex.view.IMainView;
import pavelnovak.com.omertex.R;
import pavelnovak.com.omertex.presenter.impl.MainPresenter;

public class MainActivity extends AppCompatActivity implements IMainView {
    private List<PlaceResult> _placeResult;
    private RecyclerView unionRecyclerView;
    private List<Union> unionList;
    private ProgressBar _loadingIndicatorMain;
    private TextView _oopsTextView;
    private MainPresenter _presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _oopsTextView = findViewById(R.id.oopsTextView);

        unionRecyclerView = findViewById(R.id.union_recycler_view);
        unionRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        unionRecyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(unionRecyclerView.getContext(), llm.getOrientation());
        unionRecyclerView.addItemDecoration(dividerItemDecoration);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr != null ? connMgr.getActiveNetworkInfo() : null;

        if (networkInfo != null && networkInfo.isConnected()) {
            if (_presenter == null) _presenter = new MainPresenter(this);

            _presenter.getPhotosData();
            _presenter.getPlacesData();

            unionRecyclerView.addOnItemTouchListener(new RecyclerClickListener(this) {
                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }

                @Override
                public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                    Intent intent = new Intent(MainActivity.this, MoreInfoActivity.class);
                    intent.putExtra(MoreInfoActivity.EXTRA_URL, unionList.get(position).getUrl());
                    intent.putExtra(MoreInfoActivity.EXTRA_TITLE, unionList.get(position).getTitle());
                    intent.putExtra(MoreInfoActivity.EXTRA_BODY, unionList.get(position).getBody());
                    startActivity(intent);
                }

            });

        } else {
            showNoConnectionMessage();
        }

        _loadingIndicatorMain = findViewById(R.id.loading_spinner_main);
        _loadingIndicatorMain.getIndeterminateDrawable().setColorFilter(ContextCompat
                .getColor(this, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void setPlaces(List<PlaceResult> placeResult) {
        _placeResult = placeResult;
    }

    @Override
    public void setPhotos(PhotoResult photoResult){
        unionList = new ArrayList<>();
        for (int i = 0; i < photoResult.getPhotos().getPhoto().size(); i++){
            unionList.add(new Union(_placeResult.get(i).getBody(), _placeResult.get(i).getTitle(), photoResult.getPhotos().getPhoto().get(i).getUrlZ()));
        }
        RecyclerView.Adapter _adapter = new UnionRVAdapter(unionList);
        unionRecyclerView.setAdapter(_adapter);
    }

    @Override
    public void hideLoadingIndicator() {
        _loadingIndicatorMain.setVisibility(View.GONE);
    }

    @Override
    public void setEmptyResponseText(String text) {
        _oopsTextView.setText(text);
    }

    @Override
    public void showNoConnectionMessage() {
        _loadingIndicatorMain.setVisibility(View.GONE);
        _oopsTextView.setText(R.string.no_internet_connection);
    }
}
