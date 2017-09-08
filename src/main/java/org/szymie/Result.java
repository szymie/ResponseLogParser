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

    long minRoResponseTime;
    long maxRoResponseTime;
    long minRwResponseTime;
    long maxRwResponseTime;

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

        minRoResponseTime = Long.MAX_VALUE;
        maxRoResponseTime = Long.MIN_VALUE;
        minRwResponseTime = Long.MAX_VALUE;
        maxRwResponseTime = Long.MIN_VALUE;
    }

    public Result(long n, long numberOfReads, long numberOfMultiValueReads, long totalMultiValueReadSize, long abort, long numberOfAttempts, long readMultiValue, long roCount, long rwCount, long elapsed, long startTime, long firstRoStartTime, long firstRwStartTime, long lastRoEndTime, long lastRwEndTime, long roTotalElapsed, long rwTotalElapsed, long minRoResponseTime, long maxRoResponseTime, long minRwResponseTime, long maxRwResponseTime) {
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
        this.minRoResponseTime = minRoResponseTime;
        this.maxRoResponseTime = maxRoResponseTime;
        this.minRwResponseTime = minRwResponseTime;
        this.maxRwResponseTime = maxRwResponseTime;
    }

    public Result newResult(long n, long numberOfReads, long numberOfMultiValueReads, long totalMultiValueReadSize, long abort, long numberOfAttempts, long readMultiValue, long roCount, long rwCount, long elapsed, long startTime, long firstRoStartTime, long firstRwStartTime, long lastRoEndTime, long lastRwEndTime, long roTotalElapsed, long rwTotalElapsed,
                            long minRoResponseTime, long maxRoResponseTime, long minRwResponseTime, long maxRwResponseTime) {
        return new Result(n, numberOfReads, numberOfMultiValueReads, totalMultiValueReadSize, abort, numberOfAttempts, readMultiValue, roCount, rwCount, elapsed, startTime, firstRoStartTime,
                firstRwStartTime, lastRoEndTime, lastRwEndTime, roTotalElapsed, rwTotalElapsed, Math.min(this.minRoResponseTime, minRoResponseTime), Math.max(this.maxRoResponseTime, maxRoResponseTime), Math.min(this.minRwResponseTime, minRwResponseTime), Math.max(this.maxRwResponseTime, maxRwResponseTime));
    }
}