package api.config;

import org.aeonbits.owner.Config;


/* Для локального запуска:
./gradlew test -Dmode=GUEST
./gradlew test -Dmode=AUTH
**/

@Config.Sources({
        "classpath:properties/${mode}.properties", //./gradlew test -Dmode=AUTH  # Запуск в режиме AUTH
    // ./gradlew test # Запуск в режиме GUEST (по умолчанию)
        "system:properties",
        "system:env"  // Резерв: переменные окружения (для GitHub Actions)
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
