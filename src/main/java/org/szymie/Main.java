package org.szymie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {



    public static void main(String[] args) throws IOException {

        String inputFileName = args[0];
        String outputFileName = "";

        if(args.length > 1) {
            outputFileName = args[1];
        }

        Stream<String> lines = Files.lines(Paths.get(inputFileName));

        Result resultAcc = lines.skip(1).map(line -> {

            String[] splitLine = line.split(",");
            String result = splitLine[4];

            Map<String, String> map = Arrays.stream(result.split(":")).collect(Collectors.toMap(o -> o.split("=")[0], o -> o.split("=")[1]));

            map.put("start", splitLine[0]);
            map.put("elapsed", splitLine[1]);
            map.put("label", splitLine[2]);

            return map;
        }).map(resultMap -> {

            long r = Long.parseLong(resultMap.get("r"));
            long mvr = Long.parseLong(resultMap.get("mvr"));
            long tmvr = Long.parseLong(resultMap.get("tmvr"));
            long a = Boolean.parseBoolean(resultMap.get("a")) ? 1 : 0;
            long at = Long.parseLong(resultMap.get("at"));

            boolean ro = resultMap.get("label").startsWith("RO");
            boolean rw = resultMap.get("label").startsWith("RW");
            long start =  Long.parseLong(resultMap.get("start"));
            long elapsed =  Long.parseLong(resultMap.get("elapsed"));

            return new Result(1, r, mvr, tmvr, a, at, mvr > 0 ? 1 : 0, ro ? 1 : 0, rw ? 1 : 0, elapsed, start, 0, 0, 0, 0, ro ? elapsed : 0, rw ? elapsed : 0);
        }).reduce(new Result(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), (acc, result) -> {

            boolean ro = result.roCount > 0;
            boolean rw = result.rwCount > 0;

            long firstRoStartTime = acc.firstRoStartTime == 0 && ro ? result.startTime : acc.firstRoStartTime;
            long firstRwStartTime = acc.firstRwStartTime == 0 && rw ? result.startTime : acc.firstRwStartTime;

            long lastRoEndTime = ro ? result.startTime + result.elapsed : acc.lastRoEndTime;
            long lastRwEndTime = rw ? result.startTime + result.elapsed : acc.lastRwEndTime;

            return acc.newResult(acc.n + result.n, acc.numberOfReads + result.numberOfReads,
                    acc.numberOfMultiValueReads + result.numberOfMultiValueReads, acc.totalMultiValueReadSize + result.totalMultiValueReadSize,
                    acc.abort + result.abort, acc.numberOfAttempts + result.numberOfAttempts, acc.readMultiValue + result.readMultiValue,
                    acc.roCount + result.roCount, acc.rwCount + result.rwCount, 0, 0, firstRoStartTime, firstRwStartTime, lastRoEndTime, lastRwEndTime,
                    acc.roTotalElapsed + (ro ? result.elapsed : 0), acc.rwTotalElapsed + (rw ? result.elapsed : 0), ro ? result.elapsed : Long.MAX_VALUE, ro ? result.elapsed : Long.MIN_VALUE,
                    rw ? result.elapsed : Long.MAX_VALUE, rw ? result.elapsed : Long.MIN_VALUE);
        });

        lines.close();

        double abortRate = resultAcc.abort / (double) resultAcc.n;
        double multiValueReadsRate = resultAcc.numberOfMultiValueReads / (double) resultAcc.numberOfReads;
        double readMultiValueRate = resultAcc.readMultiValue / (double) resultAcc.n;
        double avgMultiValueReadSize = resultAcc.numberOfMultiValueReads != 0 ? resultAcc.totalMultiValueReadSize / (double) resultAcc.numberOfMultiValueReads : 0;

        double roThroughput = resultAcc.roCount > 0 ? resultAcc.roCount / (double) (resultAcc.lastRoEndTime - resultAcc.firstRoStartTime) : 0;
        double rwThroughput = resultAcc.rwCount > 0 ? resultAcc.rwCount / (double) (resultAcc.lastRwEndTime - resultAcc.firstRwStartTime) : 0;
        double throughput;

        if(roThroughput == 0) {
            throughput = rwThroughput;
        } else if(rwThroughput == 0) {
            throughput = roThroughput;
        } else {
            throughput = (resultAcc.roCount + resultAcc.rwCount) / (double) (Math.max(resultAcc.lastRwEndTime, resultAcc.lastRoEndTime) - (Math.min(resultAcc.firstRoStartTime, resultAcc.firstRwStartTime)));
        }

        double avgRoResponseTime = resultAcc.roCount > 0 ? resultAcc.roTotalElapsed / resultAcc.roCount : 0;
        double avgRwResponseTime = resultAcc.rwCount > 0 ? resultAcc.rwTotalElapsed / resultAcc.rwCount : 0;
        double avgResponseTime = (resultAcc.roTotalElapsed + resultAcc.rwTotalElapsed) / (resultAcc.roCount + resultAcc.rwCount);

        StdDevResult stdDevResult = calculateStdDevResults(inputFileName, resultAcc.roCount, avgRoResponseTime, resultAcc.rwCount, avgRwResponseTime, resultAcc.roCount + resultAcc.rwCount, avgResponseTime);

        List<String> results = Arrays.asList(
                "Number of transactions: " + resultAcc.n,
                "Number of attempts: " + resultAcc.numberOfAttempts,
                "Transactions/attempts rate: " + resultAcc.n / (double) resultAcc.numberOfAttempts * 100 + "%",
                "Abort rate: " + abortRate * 100 + "%",
                "Multi value reads rate: " + multiValueReadsRate * 100 + "%",
                "Transaction read multi value rate: " + readMultiValueRate * 100 + "%",
                "Avg. multi value read size: " + avgMultiValueReadSize,
                "RO throughout: " + roThroughput * 1000 + "/sec",
                "RW throughout: " + rwThroughput * 1000 + "/sec",
                "Throughout: " + throughput * 1000 + "/sec",
                "Avg. RO response time: " + avgRoResponseTime + " ms",
                "Avg. RW response time: " + avgRwResponseTime + " ms",
                "Avg. response time: " + avgResponseTime + " ms",
                "RO Std. Dev. response time: " + stdDevResult.roValue + " ms",
                "RW Std. Dev. response time: " + stdDevResult.rwValue + " ms",
                "Std. Dev. response time: " + stdDevResult.value + " ms",
                "RO min. response time: " + resultAcc.minRoResponseTime + " ms",
                "RO max. response time: " + resultAcc.maxRoResponseTime + " ms",
                "RW min. response time: " + resultAcc.minRwResponseTime + " ms",
                "RW max. response time: " + resultAcc.maxRwResponseTime + " ms"
        );

        results.forEach(System.err::println);

        if(!outputFileName.isEmpty()) {
            Files.write(Paths.get(outputFileName), results, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    private static StdDevResult calculateStdDevResults(String inputFileName, long roCount, double roAvg, long rwCount, double rwAvg, long totalCount, double avg) throws IOException {

        Stream<String> lines = Files.lines(Paths.get(inputFileName));

        StdDevResult resultAcc = lines.skip(1).map(line -> {

            String[] splitLine = line.split(",");

            Map<String, String> map = new HashMap<>();

            map.put("elapsed", splitLine[1]);
            map.put("label", splitLine[2]);

            return map;
        }).map(resultMap -> {

            boolean ro = resultMap.get("label").startsWith("RO");

            long elapsed = Long.parseLong(resultMap.get("elapsed"));

            double roValue = 0;
            double rwValue = 0;

            if (ro) {
                roValue = Math.pow(elapsed - roAvg, 2);
            } else {
                rwValue = Math.pow(elapsed - rwAvg, 2);
            }

            double value = Math.pow(elapsed - avg, 2);

            return new StdDevResult(roValue, rwValue, value);
        }).reduce(new StdDevResult(0, 0, 0), (acc, result) ->
                new StdDevResult(acc.roValue + result.roValue, acc.rwValue + result.rwValue, acc.value + result.value)
        );

        double roStdDev = Math.sqrt(resultAcc.roValue / roCount);
        double rwStdDev = Math.sqrt(resultAcc.rwValue / rwCount);
        double totalStdDev = Math.sqrt(resultAcc.value / totalCount);

        lines.close();

        return new StdDevResult(roStdDev, rwStdDev, totalStdDev);
    }
}
