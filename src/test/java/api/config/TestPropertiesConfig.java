package api.config;

import org.aeonbits.owner.Config;


/* Для локального запуска:
./gradlew test -Dmode=GUEST
./gradlew test -Dmode=AUTH
**/

@Config.Sources({
  //      "classpath:properties/guest.properties"
//        "classpath:${env}.properties",
//        "classpath:default.properties"

       //Порядок получения параметров:
        "classpath:properties/${mode}.properties",
        "system:properties",  // Чтение из -D параметров

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
