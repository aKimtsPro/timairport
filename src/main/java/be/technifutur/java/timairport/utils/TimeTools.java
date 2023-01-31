package be.technifutur.java.timairport.utils;

public final class TimeTools {

    public static boolean checkNoConflict( TimeRange time1, TimeRange time2 ){
        // time1 start should be after time2 end OR time1 end should before time2 start
        return time1.begin().isAfter( time2.end() ) || time1.end().isBefore(time2.begin());
    }


}
