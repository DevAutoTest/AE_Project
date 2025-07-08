package api.config;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({
        "classpath:properties/guest.properties"
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
    @DefaultValue("")
    String getUserName();

    @Key("password")
    @DefaultValue("")
    String getPassword();
}
