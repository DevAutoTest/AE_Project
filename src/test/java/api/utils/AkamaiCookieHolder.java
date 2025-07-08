package api.utils;

import java.util.Map;

/** Потокобезопасный «контейнер» для 4 Akamai-cookie. */
public final class AkamaiCookieHolder {
    private static volatile Map<String,String> cookies = Map.of();

    private AkamaiCookieHolder() {}

    public static Map<String,String> get()              { return cookies; }
    public static void               set(Map<String,String> m) { cookies = m; }
}