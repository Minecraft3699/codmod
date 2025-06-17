package com.mc3699.codmod.signals;

public enum SignalSize {
    HUGE, LARGE, MEDIUM, SMALL, TINY;

    public static final long HUGE_THRESHOLD = (long) (1024 * 1024 * 1.3d); // 1.3mb
    public static final long LARGE_THRESHOLD = (long) (1024 * 1024 * 1d); // 1mb
    public static final long MEDIUM_THRESHOLD = (long) (1024 * 1024 * 0.3d); // 0.3mb
    public static final long SMALL_THRESHOLD = (long) (1024 * 1024 * 0.1d); // 0.1mb

    public static SignalSize forBytes(long bytes) {
        if (bytes >= HUGE_THRESHOLD) {
            return HUGE;
        } else if (bytes >= LARGE_THRESHOLD) {
            return LARGE;
        } else if (bytes >= MEDIUM_THRESHOLD) {
            return MEDIUM;
        } else if (bytes >= SMALL_THRESHOLD) {
            return SMALL;
        } else {
            return TINY;
        }
    }
}
