package be.technifutur.java.timairport.utils;

import java.time.LocalDateTime;


public record TimeRange(LocalDateTime begin, LocalDateTime end) {
    public static TimeRange of(LocalDateTime begin, LocalDateTime end){
        return new TimeRange(begin, end);
    }
}

//// 1 - tous les fields private final
//// 2 - ctor set tous les fields
//// 3 - getter sur les fields
//public class TimeRange{
//
//    private final LocalDateTime begin, end;
//
//    public TimeRange(LocalDateTime begin, LocalDateTime end) {
//        this.begin = begin;
//        this.end = end;
//    }
//
//    public LocalDateTime getBegin() {
//        return begin;
//    }
//
//    public LocalDateTime getEnd() {
//        return end;
//    }
//    public static TimeRange of(LocalDateTime begin, LocalDateTime end){
//        return new TimeRange(begin, end);
//    }
//}
