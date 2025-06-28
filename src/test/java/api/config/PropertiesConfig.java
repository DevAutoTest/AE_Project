package api.config;

import static api.tests.ApiGuestTests.BASE_URL;

public class PropertiesConfig {
public String getBaseUrl(){
    return System.getProperty("baseUrl", BASE_URL);
}


}
