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

    long minResponseTime;
    long maxResponseTime;

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

        minResponseTime = Long.MAX_VALUE;
        maxResponseTime = Long.MIN_VALUE;
    }

    public Result(long n, long numberOfReads, long numberOfMultiValueReads, long totalMultiValueReadSize, long abort, long numberOfAttempts, long readMultiValue, long roCount, long rwCount, long elapsed, long startTime, long firstRoStartTime, long firstRwStartTime, long lastRoEndTime, long lastRwEndTime, long roTotalElapsed, long rwTotalElapsed, long minResponseTime, long maxResponseTime) {
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
        this.minResponseTime = minResponseTime;
        this.maxResponseTime = maxResponseTime;
    }

    public Result newResult(long n, long numberOfReads, long numberOfMultiValueReads, long totalMultiValueReadSize, long abort, long numberOfAttempts, long readMultiValue, long roCount, long rwCount, long elapsed, long startTime, long firstRoStartTime, long firstRwStartTime, long lastRoEndTime, long lastRwEndTime, long roTotalElapsed, long rwTotalElapsed, long minResponseTime, long maxResponseTime) {
        return new Result(n, numberOfReads, numberOfMultiValueReads, totalMultiValueReadSize, abort, numberOfAttempts, readMultiValue, roCount, rwCount, elapsed, startTime, firstRoStartTime,
                firstRwStartTime, lastRoEndTime, lastRwEndTime, roTotalElapsed, rwTotalElapsed, Math.min(this.minResponseTime, minResponseTime), Math.max(this.maxResponseTime, maxResponseTime));
    }
}