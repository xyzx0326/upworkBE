package kodlamaio.northwind.core.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessUtil {
    static Logger logger = LoggerFactory.getLogger(ProcessUtil.class);
    public static String runProcess(ProcessBuilder pb) throws IOException, InterruptedException {
        StringBuilder builder = new StringBuilder();
        String line = null;
        logger.info(String.join(" ", pb.command()));
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        Throwable var3 = null;

        try {
            String s;
            try {
                while((s = reader.readLine()) != null) {
                    logger.info(s);
                    builder.append(s);
                }
            } catch (Throwable var12) {
                var3 = var12;
                throw var12;
            }
        } finally {
            if (reader != null) {
                if (var3 != null) {
                    try {
                        reader.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    reader.close();
                }
            }

        }

        p.waitFor();
        return builder.toString();
    }
}
