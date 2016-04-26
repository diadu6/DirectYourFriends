package com.example.joshua.directyourfriends;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.MapFragment;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



//import com.google.api.client.util.Key;
//import com.google.api.client.http.HttpTransport;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnClickListener {

    private Button ViewDirections;
    private Button send;
    private EditText DirectionsEditText;
    private EditText emailEditText;

    private AutoCompleteTextView From;
    private AutoCompleteTextView To;

    private GoogleMap mMap;

    //static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    //static final JsonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //FrameLayout layout = new FrameLayout(this);//fragment
        //layout.setId(R.id.map);//fragment

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);


        //Input database

        //mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        //mMap.addMarker(new MarkerOptions().position(LOCATION_FROM).title("Go From Here"));

        ViewDirections = (Button) findViewById(R.id.button);
        send = (Button) findViewById(R.id.SendButton);
        From = (AutoCompleteTextView) findViewById(R.id.FromEditText);
        To = (AutoCompleteTextView) findViewById(R.id.ToEditText);
        DirectionsEditText = (EditText) findViewById(R.id.DirectionsEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);

        ViewDirections.setOnClickListener(this);
        send.setOnClickListener(this);
        //From.setAdapter(new PlacesAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line));
        //To.setAdapter(new PlacesAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line));
        dbRead();

        //Create and add a fragment to frame layout created above.
        FragmentTransaction t = getFragmentManager().beginTransaction();
        MapFragment myFragment = new MapFragment();
        //t.add(layout.getId(), myFragment, "myFirstFragment");
        t.commit();
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


    public void onNothingSelected(AdapterView<?> parent) {
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Intent intent = new Intent("com.example.joshua.directyourfriends.DirectYourFriends");
                intent.putExtra("to", To.getText());
                intent.putExtra("from", From.getText());
                //startActivity(intent); //Switch to DirectYourFriends Activity
                showDirections();
                store(); //Database store
                break;
            case R.id.SendButton:
                sendMessage();
                Toast.makeText(MapsActivity.this, "Message Was Sent", Toast.LENGTH_LONG).show();
        }
    }


    public void showDirections() {


        //Output database
        //db inData = new db();


        //Parse text directions from Google
        //Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr=location_start" + "daddr=location_end"));
        //intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        //startActivity(intent);



        //String instruction = Object.getString("html_instructions");
        //DirectionsEditText.setText(Html.fromHtml(instruction));


        StringBuilder urlString = new StringBuilder();
        Document doc = null;
        urlString
                .append("http://maps.google.com/maps/api/directions/xml?origin=");
        urlString.append(39.2904);      //lat
        urlString.append(",");
        urlString.append(76.6122);      //Long
        urlString.append("&destination=");// to
        urlString.append(38.4719);      //lat
        urlString.append(",");
        urlString.append(78.8793);      //long
        urlString.append("&sensor=true&mode=driving");
        Log.d("url", "::" + urlString.toString());
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            url = new URL(urlString.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = (Document) db.parse(urlConnection.getInputStream());// Util.XMLfromString(response);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }






        NodeList nl1, nl2;

        nl1 = doc.getElementsByTagName("step");
        if (nl1.getLength() > 0) {
            String instruction = "";
            for (int i = 0; i < nl1.getLength(); i++) {
                Node node1 = nl1.item(i);
                nl2 = node1.getChildNodes();
                Node directionNode = nl2.item(getNodeIndex(nl2, "html_instructions"));

                instruction = instruction + directionNode.getTextContent() + "\n";
            }
            DirectionsEditText.setText(instruction);
        }

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


    public void sendMessage() {
        //Send message through email
        Log.i("Send email", "");

        String[] TO = new String[]{emailEditText.getText().toString()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);                               //Sending To
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Direct Your Friends!");         //Subject of Email
        emailIntent.putExtra(Intent.EXTRA_TEXT, DirectionsEditText.getText());      //Body of email

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            //Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MapsActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    private int getNodeIndex(NodeList nl, String nodename) {
        for(int i = 0 ; i < nl.getLength() ; i++) {
            if(nl.item(i).getNodeName().equals(nodename))
                return i;
        }
        return -1;
    }
    public void store()
    {
        //search_history search_history = new search_history();
        getDirectionsDB search = new getDirectionsDB("string being stored");
        //long insertId = search_history.insertDirections(search);

    }
    public void dbRead()
    {
        /*
        ArrayList<getDirectionsDB> search = search_history.getSearches("uvihvb");
        for (getDirectionsDB t : searches)
        {
            sb.append(t.getId() + "|" + t.getSearch() + "\n");
        }
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
///////////////////////////////////Map Location Stuff
/*
 //////////////////////////////////move map to this location
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_FROM, 16);
        mMap.animateCamera(update);

        /////////////////////////////////Add Marker
        mMap.addMarker(new MarkerOptions().position(LOCATION_FROM).title("Find Me Here!"));
 */






