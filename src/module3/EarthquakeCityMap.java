package module3;

//Java utilities libraries

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

//import java.util.Collections;
//import java.util.Comparator;
//Processing library
//Unfolding libraries
//Parsing library

/**
 * EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 *
 * @author Your name here
 * Date: July 17, 2015
 */
public class EarthquakeCityMap extends PApplet {

    // You can ignore this.  It's to keep eclipse from generating a warning.
    private static final long serialVersionUID = 1L;

    // IF YOU ARE WORKING OFFLINE, change the value of this variable to true
    private static final boolean offline = false;

    // Less than this threshold is a light earthquake
    public static final float THRESHOLD_MODERATE = 5;
    // Less than this threshold is a minor earthquake
    public static final float THRESHOLD_LIGHT = 4;

    private final int GREEN = color(0, 255, 0);
    private final int YELLOW = color(255, 255, 0);
    private final int RED = color(255, 0, 0);

    private static final float SMALL_R = 5.0f;
    private static final float MED_R = 10.0f;
    private static final float BIG_R = 15.0f;

    /**
     * This is where to find the local tiles, for working without an Internet connection
     */
    public static String mbTilesString = "blankLight-1-3.mbtiles";

    // The map
    private UnfoldingMap map;

    // The legend

    //feed with magnitude 2.5+ Earthquakes
    private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";


    public void setup() {
        size(950, 600, OPENGL);

        if (offline) {
            map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
            earthquakesURL = "2.5_week.atom";    // Same feed, saved Aug 7, 2015, for working offline
        } else {
            map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
            // IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
            //earthquakesURL = "2.5_week.atom";
        }

        map.zoomToLevel(2);
        MapUtils.createDefaultEventDispatcher(this, map);

        // The List you will populate with new SimplePointMarkers
        List<Marker> markers = new ArrayList<Marker>();

        //Use provided parser to collect properties for each earthquake
        //PointFeatures have a getLocation method
        List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);


        //TODO (Step 3): Add a loop here that calls createMarker (see below)
        // to create a new SimplePointMarker for each PointFeature in
        // earthquakes.  Then add each new SimplePointMarker to the
        // List markers (so that it will be added to the map in the line below)

        for (PointFeature eq :
                earthquakes) {
            markers.add(createMarker(eq));
        }

        // Add the markers to the map so that they are displayed
        map.addMarkers(markers);
    }


    /* createMarker: A suggested helper method that takes in an earthquake
     * feature and returns a SimplePointMarker for that earthquake
     *
     * In step 3 You can use this method as-is.  Call it from a loop in the
     * setup method.
     *
     * TODO (Step 4): Add code to this method so that it adds the proper
     * styling to each marker based on the magnitude of the earthquake.
     */
    private SimplePointMarker createMarker(PointFeature feature) {
        // To print all of the features in a PointFeature (so you can see what they are)
        // uncomment the line below.  Note this will only print if you call createMarker
        // from setup
        //System.out.println(feature.getProperties());

        // Create a new SimplePointMarker at the location given by the PointFeature
        SimplePointMarker marker = new SimplePointMarker(feature.getLocation());

        Object magObj = feature.getProperty("magnitude");
        float mag = Float.parseFloat(magObj.toString());

        // Here is an example of how to use Processing's color method to generate
        // an int that represents the color yellow.


        marker.setColor(color(0, 0, 0));
        if (mag < THRESHOLD_LIGHT) {
            marker.setColor(GREEN);
            marker.setRadius(SMALL_R);
        }
        if (mag >= THRESHOLD_LIGHT && mag < THRESHOLD_MODERATE) {
            marker.setColor(YELLOW);
            marker.setRadius(MED_R);
        }
        if (mag >= THRESHOLD_MODERATE) {
            marker.setColor(RED);
            marker.setRadius(BIG_R);

        }


        // TODO (Step 4): Add code below to style the marker's size and color
        // according to the magnitude of the earthquake.
        // Don't forget about the constants THRESHOLD_MODERATE and
        // THRESHOLD_LIGHT, which are declared above.
        // Rather than comparing the magnitude to a number directly, compare
        // the magnitude to these variables (and change their value in the code
        // above if you want to change what you mean by "moderate" and "light")


        // Finally return the marker
        return marker;
    }

    // helper method to draw key in GUI
    // TODO: Implement this method to draw the key
    private void addKey() {
//        map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());

        // Remember you can use Processing's graphics methods here
        fill(255);
        rect(20, 50, 160, 500);

        fill(GREEN);
        ellipse(40, 90, SMALL_R, SMALL_R);

        fill(YELLOW);
        ellipse(40, 110, MED_R, MED_R);

        fill(RED);
        ellipse(40, 130, BIG_R, BIG_R);

        fill(0);
        textSize(18);

        text("Legend", 40, 70);

        textSize(12);

        text("Light", 60, 90 + SMALL_R / 2);
        text("Moderate", 60, 110 + MED_R / 2);
        text("Massive", 60, 130 + BIG_R / 2);


    }

    public void draw() {
        background(10);
        map.draw();
        addKey();
    }


}
