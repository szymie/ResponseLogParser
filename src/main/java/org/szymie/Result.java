package org.szymie;

public class Result {

    long n;
    long numberOfReads;
    long numberOfMultiValueReads;
    long totalMultiValueReadSize;
    long abort;
    long numberOfAttempts;
    long readMultiValue;

    public Result(long n, long numberOfReads, long numberOfMultiValueReads, long totalMultiValueReadSize, long abort, long numberOfAttempts, long readMultiValue) {
        this.n = n;
        this.numberOfReads = numberOfReads;
        this.numberOfMultiValueReads = numberOfMultiValueReads;
        this.totalMultiValueReadSize = totalMultiValueReadSize;
        this.abort = abort;
        this.numberOfAttempts = numberOfAttempts;
        this.readMultiValue = readMultiValue;
    }
}