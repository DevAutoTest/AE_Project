package api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class JwtUtils {

    private JwtUtils() { }   // util-class

    /** UNIX-time (ms) из поля exp; 0 – если не удалось распарсить. */
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

    /** true – если токен уже истёк. */
    public static boolean isExpired(String jwt) {
        return expirationMillis(jwt) <= System.currentTimeMillis();
    }
}