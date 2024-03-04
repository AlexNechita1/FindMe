package com.example.findme;

public class LocationManager {

        private static double latitude;
        private static double longitude;

        public static void saveLocation(double lat, double lng) {
            latitude = lat;
            longitude = lng;
        }

        public static double getLatitude() {
            return latitude;
        }

        public static double getLongitude() {
            return longitude;
        }

}
