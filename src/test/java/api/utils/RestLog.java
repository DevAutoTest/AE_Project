package api.utils;


import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public final class RestLog {
    private RestLog() {}

    /** Полный лог запроса + ответа (headers, body, cookies). */
    public static RequestLoggingFilter rq() {
        return new RequestLoggingFilter(LogDetail.ALL, System.out);
    }
    public static ResponseLoggingFilter rs() {
        return new ResponseLoggingFilter(LogDetail.ALL, System.out);
    }
}
