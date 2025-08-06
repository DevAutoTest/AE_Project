package api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class JwtUtils {

    private JwtUtils() { }   // util-class

    /** UNIX-time (ms) from field exp; 0 – in case if it wasn't parsed. */
    public static long expirationMillis(String jwt) {
        try {
            String payloadJson = new String(
                    Base64.getUrlDecoder().decode(jwt.split("\\.")[1]),
                    StandardCharsets.UTF_8);

            long expSec = new ObjectMapper()
                    .readTree(payloadJson)
                    .path("exp").asLong(0);

            return expSec * 1_000L;
        } catch (Exception e) {
            return 0;
        }
    }

    /** true – if token expired */
    public static boolean isExpired(String jwt) {
        return expirationMillis(jwt) <= System.currentTimeMillis();
    }
}