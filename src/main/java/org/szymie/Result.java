package org.szymie;

public class Result {

    long n;
    long numberOfReads;
    long numberOfMultiValueReads;
    long totalMultiValueReadSize;
    long abort;
    long numberOfAttempts;
    long readMultiValue;

    long roCount;
    long rwCount;

    long elapsed;
    long startTime;

    long firstRoStartTime;
    long firstRwStartTime;
    long lastRoEndTime;
    long lastRwEndTime;

    long roTotalElapsed;
    long rwTotalElapsed;

    public Result(long n, long numberOfReads, long numberOfMultiValueReads, long totalMultiValueReadSize, long abort, long numberOfAttempts, long readMultiValue, long roCount, long rwCount, long elapsed, long startTime, long firstRoStartTime, long firstRwStartTime, long lastRoEndTime, long lastRwEndTime, long roTotalElapsed, long rwTotalElapsed) {
        this.n = n;
        this.numberOfReads = numberOfReads;
        this.numberOfMultiValueReads = numberOfMultiValueReads;
        this.totalMultiValueReadSize = totalMultiValueReadSize;
        this.abort = abort;
        this.numberOfAttempts = numberOfAttempts;
        this.readMultiValue = readMultiValue;
        this.roCount = roCount;
        this.rwCount = rwCount;
        this.elapsed = elapsed;
        this.startTime = startTime;
        this.firstRoStartTime = firstRoStartTime;
        this.firstRwStartTime = firstRwStartTime;
        this.lastRoEndTime = lastRoEndTime;
        this.lastRwEndTime = lastRwEndTime;
        this.roTotalElapsed = roTotalElapsed;
        this.rwTotalElapsed = rwTotalElapsed;
    }
}