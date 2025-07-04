package api.config;
import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/auth.properties"
//        "classpath:${env}.properties",
//        "classpath:default.properties"
})

public interface TestPropertiesConfig extends org.aeonbits.owner.Config{

    @Key("mode")
    String getUserMode();

    @Key("baseUrl")
    String getApiBaseUrl();

    @Key("guest.header.authorization")
    String getBasicAuth();

    @Key("username")
    String getUserName();

    @Key("password")
    String getPassword();
}
