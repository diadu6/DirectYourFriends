package com.example.joshua.directyourfriends;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.MapFragment;

//import com.google.api.client.util.Key;
//import com.google.api.client.http.HttpTransport;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnClickListener {

    private final LatLng LOCATION_FROM = new LatLng(49.27645, -122.917587);
    private final LatLng LOCATION_TO = new LatLng(49.187500, -122.849000);
    private Button ViewDirections;
    private AutoCompleteTextView From;
    private AutoCompleteTextView To;

    private GoogleMap mMap;

    //static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    //static final JsonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //Input database

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        mMap.addMarker(new MarkerOptions().position(LOCATION_FROM).title("Go From Here"));

        ViewDirections = (Button) findViewById(R.id.button);
        From = (AutoCompleteTextView) findViewById(R.id.FromEditText);
        To = (AutoCompleteTextView) findViewById(R.id.ToEditText);

        ViewDirections.setOnClickListener(this);
        //From.setAdapter(new PlacesAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line));
        //To.setAdapter(new PlacesAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line));
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
        //mMap.setExtent(directions.get(index).getGeometry());
    }


    public void onNothingSelected(AdapterView<?> parent) { }




    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button:
                showDirections();
                startActivity(new Intent(MapsActivity.this, DirectYourFriends.class)); //Switch to DirectYourFriends Activity
                break;
        }
    }

    public void showDirections()
    {


        //////////////////////////////////move map to this location
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_FROM, 16);
        mMap.animateCamera(update);

        /////////////////////////////////Add Marker
        mMap.addMarker(new MarkerOptions().position(LOCATION_FROM).title("Find Me Here!"));




        //Output database
        db inData = new db();//////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////////////////
        //Parser
        //////////////////////////////////////////////////////////////


/*
        List<Route> routes = mResults.getRoutes();
        Route mRoute = routes.get(0);


        // Get the list of directions from the Route object
        final List<RouteDirection> directions = mRoute.getRoutingDirections();
        String[] flatArray = new String[directions.size()];

        // Iterate through all of the directions and create formatted Strings
        for(int i = 0; i < directions.size(); i++) {
            RouteDirection direction = directions.get(i);
            flatArray[i] = String.format("%s%nTime: %.1f minutes, Length: %.1f miles", direction.getText(), direction.getMinutes(), direction.getLength()));
        }

        // Create an array adapter for the formatted directions Strings
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, flatArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
*/
    }
}



/*

    //Autocomplete

    private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected android.widget.Filter.FilterResults performFiltering(CharSequence constraint) {
                    android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, android.widget.Filter.FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    }
                    else {
                        notifyDataSetInvalidated();
                    }
                }};
            return filter;
        }
    }

    private static final String PLACES_AUTOCOMPLETE_API = "https://maps.googleapis.com/maps/api/place/autocomplete/json";

    private ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = new ArrayList<String>();

        try {

            HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                                                                                        @Override
                                                                                        public void initialize(HttpRequest request) {
                                                                                            request.setParser(new JsonObjectParser(JSON_FACTORY));
                                                                                        }
                                                                                    }
            );

            GenericUrl url = new GenericUrl(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            url.put("input", input);
            url.put("key", PLACES_API_KEY);
            url.put("sensor",false);

            HttpRequest request = requestFactory.buildGetRequest(url);
            HttpResponse httpResponse = request.execute();
            PlacesResult directionsResult = httpResponse.parseAs(PlacesResult.class);

            List<Prediction> predictions = directionsResult.predictions;
            for (Prediction prediction : predictions) {
                resultList.add(prediction.description);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultList;
    }
    public static class PlacesResult {

        @Key("predictions")
        public List<Prediction> predictions;

    }

    public static class Prediction {
        @Key("description")
        public String description;

        @Key("id")
        public String id;

    }
}
*/








