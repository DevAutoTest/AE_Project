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

        "classpath:properties/${mode}.properties",  // Динамический выбор файла
        "system:env",                                   // Переопределение через ENV (CI)
        "system:properties"  // Добавьте эту строку для чтения из -D параметров
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
