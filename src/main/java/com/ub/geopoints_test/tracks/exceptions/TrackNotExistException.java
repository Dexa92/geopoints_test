package com.ub.geopoints_test.tracks.exceptions;

public class TrackNotExistException extends Exception {

    public TrackNotExistException() {
    }

    public TrackNotExistException(String message) {
        super(message);
    }
}
