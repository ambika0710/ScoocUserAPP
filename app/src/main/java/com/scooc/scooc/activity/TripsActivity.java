package com.scooc.scooc.activity;

import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.HashMap;

import com.google.android.material.snackbar.Snackbar;
import com.scooc.scooc.R;
import com.scooc.scooc.adapter.TripDetailsRecyclerAdapter;
import com.scooc.scooc.listeners.TripListListener;
import com.scooc.scooc.model.TripBean;
import com.scooc.scooc.model.TripListBean;
import com.scooc.scooc.net.DataManager;

public class TripsActivity extends BaseAppCompatNoDrawerActivity {

    private Toolbar toolbarTrips;
    private TripBean recentSearchBean;
    private MapView mMapView;
    private TripListBean tripListBean;
    private TripDetailsRecyclerAdapter recyclerAdapter;
    private RecyclerView rvTrips;
    private GoogleMap map;
    private String tripList;

   /* private static GoogleMapOptions options = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(false)
            .rotateGesturesEnabled(false)
            .tiltGesturesEnabled(false)
            .zoomControlsEnabled(false)
            .scrollGesturesEnabled(false)
            .mapToolbarEnabled(false);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        initViews();

        getSupportActionBar().setTitle(R.string.title_trip_list);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        swipeView.setRefreshing(true);

    }

    public void initViews() {

        fetchTripsList();

        rvTrips = (RecyclerView) findViewById(R.id.rv_trips);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTrips.setLayoutManager(layoutManager);
        rvTrips.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

//        mMapView = (MapView) findViewById(R.id.list_map_view);

    }

    public void fetchTripsList() {

        HashMap<String, String> urlParams = new HashMap<>();

        DataManager.fetchTripList(urlParams, new TripListListener() {

            @Override
            public void onLoadCompleted(TripListBean tripListBeanWS) {

                tripListBean = tripListBeanWS;
                populateTripList();
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }

        });
    }

    private void populateTripList() {

        if (recyclerAdapter == null) {
            recyclerAdapter = new TripDetailsRecyclerAdapter(this, tripListBean);

            recyclerAdapter.setTripDetailsRecyclerAdapterListener(new TripDetailsRecyclerAdapter.TripDetailsRecyclerAdapterListener() {

                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {

                }

                @Override
                public void onItemSelected(TripBean bean) {

                }

                @Override
                public void onRefresh() {

                }

                @Override
                public void onSwipeRefreshingChange(boolean isSwipeResfreshing) {

                }

                @Override
                public void onSnackBarShow(String message) {

                }
            });

            rvTrips.setAdapter(recyclerAdapter);

        } else {

            recyclerAdapter.setTripListBean(tripListBean);
            recyclerAdapter.notifyDataSetChanged();
        }

        if (tripListBean.getTrips() == null || tripListBean.getTrips().isEmpty()) {
            setMessageScreenVisibility(true, getString(R.string.message_no_trips_taken_yet));
        } else {
            setMessageScreenVisibility(false, getString(R.string.message_no_trips_taken_yet));

        }

        setProgressScreenVisibility(false, false);
        swipeView.setRefreshing(false);
    }
}
