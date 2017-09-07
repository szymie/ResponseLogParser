package org.szymie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
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
            String result = line.split(",")[4];
            return Arrays.stream(result.split(":")).collect(Collectors.toMap(o -> o.split("=")[0], o -> o.split("=")[1]));
        }).map(resultMap -> {

            long r = Long.parseLong(resultMap.get("r"));
            long mvr = Long.parseLong(resultMap.get("mvr"));
            long tmvr = Long.parseLong(resultMap.get("tmvr"));
            long a = Boolean.parseBoolean(resultMap.get("a")) ? 1 : 0;
            long at = Long.parseLong(resultMap.get("at"));

            return new Result(1, r, mvr, tmvr, a, at, mvr > 0 ? 1 : 0);
        }).reduce(new Result(0, 0, 0, 0, 0, 0, 0), (result1, result2) ->
            new Result(result1.n + result2.n, result1.numberOfReads + result2.numberOfReads,
                    result1.numberOfMultiValueReads + result2.numberOfMultiValueReads, result1.totalMultiValueReadSize + result2.totalMultiValueReadSize,
                    result1.abort + result2.abort, result1.numberOfAttempts + result2.numberOfAttempts, result1.readMultiValue + result2.readMultiValue)
        );

        double abortRate = resultAcc.abort / (double) resultAcc.n;
        double multiValueReadsRate = resultAcc.numberOfMultiValueReads / (double) resultAcc.numberOfReads;
        double readMultiValueRate = resultAcc.readMultiValue / (double) resultAcc.n;
        double avgMultiValueReadSize = resultAcc.numberOfMultiValueReads != 0 ? resultAcc.totalMultiValueReadSize / (double) resultAcc.numberOfMultiValueReads : 0;

        List<String> results = Arrays.asList(
                "Number of transactions: " + resultAcc.n,
                "Number of attempts: " + resultAcc.numberOfAttempts,
                "Transactions/attempts rate: " + resultAcc.n / (double) resultAcc.numberOfAttempts * 100 + "%",
                "Abort rate: " + abortRate * 100 + "%",
                "Multi value reads rate: " + multiValueReadsRate * 100 + "%",
                "Transaction read multi value rate: " + readMultiValueRate * 100 + "%",
                "Avg. multi value read size: " + avgMultiValueReadSize
        );

        results.forEach(System.err::println);

        if(!outputFileName.isEmpty()) {
            Files.write(Paths.get(outputFileName), results, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
