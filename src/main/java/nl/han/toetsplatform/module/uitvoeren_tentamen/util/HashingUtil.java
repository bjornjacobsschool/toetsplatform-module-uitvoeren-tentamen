package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import net.jpountz.xxhash.StreamingXXHash32;
import net.jpountz.xxhash.XXHashFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class HashingUtil {


    public static String generateHash(String input) throws IOException {
        return generateHash(input, 0x9747b28c);
    }

    public static String generateHash(String input, int seed) throws IOException {
        XXHashFactory factory = XXHashFactory.fastestInstance();

        byte[] data = input.getBytes("UTF-8");
        ByteArrayInputStream in = new ByteArrayInputStream(data);

        // value you want, but always the same
        StreamingXXHash32 hash32 = factory.newStreamingHash32(seed);
        byte[] buf = new byte[8192]; // for real-world usage, use a larger buffer, like 8192 bytes
        for (;;) {
            int read = in.read(buf);
            if (read == -1) {
                break;
            }
            hash32.update(buf, 0, read);
        }
        int hash = hash32.getValue();

        return String.valueOf(hash);
    }
}
