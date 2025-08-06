package api.config;

import org.aeonbits.owner.Config;

/** For local lounch:
./gradlew apiTest -Dmode=GUEST
./gradlew apiTest -Dmode=AUTH - !!! not implemented !!! AKAMAI BOT iS WORKING ON THE SITE SIDE
**/

@Config.Sources({
        "classpath:properties/${mode}.properties",  // "GUEST" mode by default
        "system:properties",
        "system:env"
})

public interface TestPropertiesConfig extends org.aeonbits.owner.Config {

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
