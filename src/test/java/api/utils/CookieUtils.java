package api.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CookieUtils {
    private CookieUtils() {}

    /** Склеивает 4 Akamai + «гостевые» куки в одну строку. */
    public static String buildCookies(String guestRawCookies) {

        Stream<Map.Entry<String,String>> akamai =
                api.utils.AkamaiCookieHolder.get().entrySet().stream();

        Stream<Map.Entry<String,String>> guest =
                Arrays.stream(guestRawCookies.split(";"))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(s -> s.split("=", 2))
                        .map(a -> Map.entry(a[0], a[1]));

        return Stream.concat(akamai, guest)
                .map(e -> e.getKey() + '=' + e.getValue())
                .collect(Collectors.joining("; "));
    }
}