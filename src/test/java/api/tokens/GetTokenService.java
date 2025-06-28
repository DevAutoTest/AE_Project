package api.tokens;

import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;

public class GetTokenService {

    public static final String TOKEN_URL = "https://www.ae.com/ugp-api";
    static String username = "test7.Elena_Danilova7788@outlook.com";
    static String password = "kjfdjkfcjkhhfjh2125_6";
    static String formData = "grant_type=password"
            + "&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8)
            + "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8);
    static String cookie = "acquisition_value=https://www.ae.com/us/en; acquisition_location=https://www.ae.com/us/en/c/women/womens?pagetype=plp; acquisition_location_gtm=https://www.ae.com/us/en/c/women/womens?pagetype=plp; acquisition_value_gtm=https://www.ae.com/us/en; _geffran=3; aeoUserJourney=journey-user-scrolled%2Cjourney-quickview_launch-example2%2Cjourney-users-who-added-item-to-bag; __rtbh.uid=%7B%22eventType%22%3A%22uid%22%2C%22id%22%3A%22%22%2C%22expiryDate%22%3A%222026-06-27T15%3A37%3A13.797Z%22%7D; TLTUID=0C3F29DDF1A41C8BCCF52BB2BA1C34A8; akaalb_PROD_ALB=~op=PROD_LB_ALL_DCs:PROD_LB_Origin_East4_AGWA_Service_Mesh|~rv=93~m=PROD_LB_Origin_East4_AGWA_Service_Mesh:0|~os=db0ecb8ead961a2983478ea211c488b6~id=63f47dde08f0d3b394f202babc084935; optimizelyEndUserId=oeu1751113735786r0.8853530385933616; brand=aeo; c_uuid=250100646453736137000537365864153624; akaalb_PROD_ALB_API=~op=PROD_LB_APICG_EAST5:PROD_LB_Origin_APICG_East5|~rv=24~m=PROD_LB_Origin_APICG_East5:0|~os=db0ecb8ead961a2983478ea211c488b6~id=cec196a0d44dad2b1ef18aba49320f52; tkbl_session=b6200922-5ec7-4f26-ad96-ba368999105b; swim_ten=a; ConstructorioID_client_id=0993bf82-9a34-4fec-9702-ee4dbe37b290; ConstructorioID_session_id=4; ConstructorioID_session={\"sessionId\":4,\"lastTime\":1751113737851}; _ga_XGBGNYD4S1=GS2.1.s1751113738$o1$g0$t1751113738$j60$l0$h556111363; _ga=GA1.1.1125570101.1751113738; _br_uid_2=uid%3D575928670755%3Av%3D17.0%3Ats%3D1750985444605%3Ahc%3D4; __rtbh.lid=%7B%22eventType%22%3A%22lid%22%2C%22id%22%3A%22D32wJfstenHpRPwQaiAD%22%2C%22expiryDate%22%3A%222026-06-28T12%3A28%3A58.700Z%22%7D; cjConsent=MHxZfDB8Tnww; _scid=DJ4nuTE9eUZxdkz4AKpfFv4j4zjsSEmKy5JsVw; _scid_r=DJ4nuTE9eUZxdkz4AKpfFv4j4zjsSEmKy5JsVw; _derived_epik=dj0yJnU9dzJPNHlxUXp0LXcyelNRMmRZeGd6TmdWLUhiSWxZTUImbj1ydUs0RUIzcDJ5bG9vVzNqVmFhUEFBJm09NyZ0PUFBQUFBR2hmNEFrJnJtPTEmcnQ9QUFBQUFHaGR6VzQmc3A9Mg; _pin_unauth=dWlkPU5qVm1aVFJrTVRRdFpUQmtPQzAwWlRNNUxUaGpNVE10TUdSa05XWTBOREF3TldVMg; _tt_enable_cookie=1; _ttp=01JYV86B0C0GCKRW0C8WY590SR_.tt.1; _fbp=fb.1.1751113739322.159835674290103056; __exponea_etc__=e9a939d8-3f3d-451b-94cc-e6a6ab8def28; _ScCbts=%5B%5D; QuantumMetricUserID=04c899c2106ac62cc91b2274dd15b8bf; _li_dcdm_c=.ae.com; _lc2_fpi=564cdb68d47c--01jyv86w7mja0mzd6sgkjsba60; _lc2_fpi_js=564cdb68d47c--01jyv86w7mja0mzd6sgkjsba60; _gegeo=JTdCJTIyY291bnRyeSUyMiUzQSUyMlNwYWluJTIyJTJDJTIyY291bnRyeUNvZGUlMjIlM0ElMjJFUyUyMiUyQyUyMmhvc3RpbmclMjIlM0FmYWxzZSUyQyUyMm1vYmlsZSUyMiUzQXRydWUlMkMlMjJwcm94eSUyMiUzQWZhbHNlJTJDJTIyc3RhdHVzJTIyJTNBJTIyc3VjY2VzcyUyMiU3RA==; geuid=3892f68d-ea79-4a5d-8812-c30c97720061; _gecntaos=MQ==; _gepi=true; _geps=true; _gekf=dGVzdDcuZWxlbmFfZGFuaWxvdmE3Nzg4QG91dGxvb2suY29t; _gess=true; _getdran=5; QuantumMetricSessionID=061289bcf05485a82c1e49d72e7fe371; ae_clarip_consent=0,1,2,3; optimizelySession=0; ak_bmsc=EB32BAE3940C220DB084CE09C832F4CB~000000000000000000000000000000~YAAQJ7sUAgKRu6+XAQAA3kn0thxirrV7seSzKPgibS1tyevF1WBwHgxTaQGIci8HqeMITphZraG4Sw0GJZAsPyJHGnHg7m+Kqr7LOfuI5Rnjp+8KOcLcMHrmQaBtMWZm7czngosyc7TpPupqjmJcor2lcTO3TCTz9UkPoWBDm1lDW8sKgWPwHAd7/MGEeRVuJpLa4dPxaFDfgrsxbZGxnG8VOwqkS2d7WB9jfWsoRjJOfXCTMD7izRkfbwV3rdDQCiaUg37+x+BU1Xvz0nmSNUI9vfXRijERAiFXhY+yfhveJ1IHECK5wm9a1T1SKHbwm2GVaVboajTDfkfBCd3DTI4vw2mvS3MckIQoKmBEVLYT/EYFQDu+9immUEzRfi7XhPbxF2ZcXIwc2D/BkOok+c3NIbxGOxL8RAItixgDZBk=; user_profile_id=undefined; utag_main=v_id:019791e07fc2001a00764246e2060506f001806700978$_sn:33$_se:14$_ss:0$_st:1751124860134$dc_visit:25$ses_id:1751120070690%3Bexp-session$_pn:1%3Bexp-session$_prevpage:AE%3Ahome%3Bexp-1751126660135; ttcsid_C0HVR0KP76SVVJ0UU9SG=1751120062556::VZNkv4weF6LiJmsDxGQR.6.1751123079460; ttcsid=1751120087950::nnJg86iQwoHToGAtbhSh.6.1751123079460; _gcl_au=1.1.559578683.1751113739.2117705170.1751120088.1751123079; bm_sv=9FAC650517DA893BCC424E1B03F20013~YAAQFbsUAs0jzKmXAQAAJK8RtxxAZBSigOcrBGvb/SQfaT99Ijw3RDSLKC7huGBAJl+6sWcuXFvoEsQdMURrdhIRhOHhmVlz2dcguOHv+bQ7u27vZuLm+9GRpKA2nMqjpn7+pJ0V9F+UZr/jO7cTSGKwRgzOuxxAHQ1SaNyw22STrrhOg15IlqoFzPv4fnqvc99g+WUHYYGfn11+DoMaiXLXg6qKAeA788NHSt43l27rtV1f+dkKJyEEp/qR~1; _abck=7A9A240E44ACA1A8EE967CA02261BC3E~-1~YAAQFbsUAtUjzKmXAQAA+LERtw5UhG4r3fF+yZBqfE5FsTtLjoXKmfuoHze5JtUEsDk1AMROMH270tw6xQrzy9Fv3WY8yWHlOKaWEQtDsPkBTMJNcBig8d0T4oQ5WfJsfGBTqCXV2l5I5EpuBhBx6KcMKgCyAJZr1vluJX7Znjfbxq+TKJ+kMpAqDzOfano1o0LFbCj8ijlWS5pn8ABoosZ14cZtFbba7yPxlU/ILEFqLeZTbQz+j/2k3B+2vtyn38uqis4mT43mye5KLNqFX819mBlK7p1snpUVU/Kruo44fJBG1sylFP27wyHvHPa+ZtkwpsR9Ap81aB0gfGP/rDG+EwtqjYAXfo5w0OxkbZWQdfmMyp04neD6j+BHEqgdRW6FrbrZLIKTlo3Jsf2vGaRzUpEG0deGeuxi30lkr/4nI0v8wd45Nwkzgs9F0wKLtMjwz3PdcXRu9ikZf8yCaeDE14cG08O1u8i78jhxx0xI2S3dghhXjtWFo58hV2ZNaOvdRkhWbWas2OtT40KpaufF3dItndpPDHR0aVcHLggXp+qGIji36BY6iSEPSjhH+wTmN4rjRaOXKLTNoE/NryQ9SNrJAfDjnmoIVNIGEu2Puv37lZOKtHTUUKPMXl502Wk/FSjUXD6ES0lyEyU7dQhFg92t5MsOeR19+TCeck2+0dcjQX0jEs8hLJ3Yaeprp7tTIrYXMoBWOTelK8ri0dgnFvQSXUaN1VnS9MJA3o5YzOeyeUCUSORRA9Bs/iEzc9bJZkjoMOSlnvjXHV/mHsKtHO90UE1KffdvM/5Dy57ps4xpUgFamarXSYakul6O/GTqzA3jrXnUWbEtaRqXvyL+YdbbGHoC1hjyMb8cyehScGQrbaUWsco0koRtl7oUKYBEirkjs8rLXMKHcGr60Xsxmn4sBHTFxJOREE7czHjH/BrXtqDN5x1qhHKnvyL0IKteSSaUY8241Z3+7Ht/Hw/RViCP75+0QeRYNCzLyldA72PDq4DN1F9ja9bcvL2jt0BjVV8Daucxyq1am7a6lls8Kr0NUOtJZd18IcorYCrVOe04iUmCxPgxNeKMVgp1Cg==~-1~-1~-1; bm_sz=3D579818D6DD11D0782EA857544FD7FC~YAAQFbsUAtYjzKmXAQAA+LERtxx2PMwOPDyBSfQyQ4Z5VkL9SoYPdU/gar8yf5wmAzzkM1B0i+7MBbKZ9Brm7w/jm+rHKeAoZRr5Nbxc0RTZERhraah0PQmS868U8BTf7SX0E64ZRqtYlNh/H2H5MBUQOTcpYpT3fMPXMJtB3UU4PsgJJWu+s8FTF+gfcoMwg0VpAuTkSBQjRV75wBAbkWkopcCzF7LOS5zOvIlAp2E5YKs7a+hErq0rpeZfvElsiV7Ye2XFoYmol8hxQCD1JBHzGcP97bqO7uSesbJfxr3a0T1EjjgI+9T+W6qmmBHE6GgF5eVGT+jHrXHFvB0mRB3EGKAApPmV9z0gYHkNAh9+nqs/DhkvRHYhCKw0AJmSBO7EmrUJDdAuhuN7iU1phYicOVKqP2bt6jAvoQCNKce3vy+zExhOrmEGm3gu7POlZNRohLpIwIGETVEgvBSICHOtIRqygq+8Ab687uJDum1CUNhJubCgLmpCJrPBnJTmG0+K5QcRey8rJBY=~4470323~3420741";
    static String xAccessType = "eyJlcGsiOnsia3R5IjoiRUMiLCJjcnYiOiJQLTI1NiIsIngiOiJnMmZVa2pzZGVpai12dWk4Sy02OXdvQ2JMLTdTVzJHbVhFLUp5aWRmYkxZIiwieSI6IklTTUZhZXAxY1lkWkFTbGtPeGdaNE1WbDJ4Y1dXMENOaXlJdDU3ZjFrX2sifSwia2lkIjoiS0VZMSIsImN0eSI6IkpXVCIsInR5cCI6IkpXVCIsImVuYyI6IkExMjhDQkMtSFMyNTYiLCJhbGciOiJFQ0RILUVTK0EyNTZLVyJ9.XmNruTkANG9A6cB1Yp7i3MKEwDphQxpg5IkMmV_DJLqB3AtFuPhgaw.V3t2hURc47BAOy3gkMzWGw.RrEm-NqqHsEZeUsozt2gSOi1EUAc7xoki4LQWptL4-h7_e1mX-RLubGQ6rKTN8U4RaM_058dHln7RoHNIQD5vBuCd2mVOuFSvIEIJpDEQnUJfsQ6aydSmJfLNSnis4I7IQmvgSzgAj9ZBcgF9RHbadGe8kemy0VV5A77AyATnMmHCZpUifPl3UQFpy4A2w8TexD0JJ9pLOJQJ2YW7qZH7ME9q_k1fNMQSbjiIbFidTN3y8FbGo9t6ED00VlnA1jvsjNb2F_fmy_akZcydlVGh0vOAP0rhEUJqTGoRzDu3RWWunqjWBjb8LUwr4bmcuXAxUHmtayZ9Ne_HMGFiO8Ke6vxnJGSonXtUzzAadIjsWqgCzNM8kEVPtrSBdZ8hogd42Qmu4m9QvuvUqNeT7XpmWQ_0rX_gSx3sdvqUekbJCFIGrPggrrLH_5v-WKLvY5PUi2G1xE_i1cp-WEBr2BtyIsndpOXV4VcY9HJzVAf-OdBy2z1XjoM7x_E-G4MATqW0oTaqzBoHNEnr4DTm0qn6-tuea-raXYYmzV5rI6RSm8dSqLz_hZaAMjc_lltUyZ2lE3JQ843hxjyxhLubm2UWlsUy8zoB1bbt5rw_TbQC94SWzsWPnB1C_AV0IWVLh1zGJTGzJXkjhmiuArc25o79Q3mrK4ey0pWIZx53FRQFpYolCNY6xQgQFyWexCLNaskqxrRdxP6IGuQ4_pXyCUWWUk7aOK8N-Hw1Jcvrflxpu_HxWbAVjX4GBVTkVFa5dkcIpVCSCJIV3yM_EgvqXglPgL4RhfmfjHoXQhDBtWPg-5AtZhpYde3WplZimrMDyXxFsgROPiBUe5C6vjOVerNVRTUhTyHaaLWg3lrQDNrfW0.cwK635cfq04ittA0waP7vw";



    public static String getGuestToken() {
        Response response = given()
                .baseUri("https://www.ae.com/ugp-api")
                .header("accept", "application/vnd.oracle.resource+json")
                .header("aesite", "AEO_US")
               // .header("aelang", "en_US")
                .header("authorization", "Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==") // 🔐 Сюда подставь реальное значение
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("grant_type", "client_credentials")
                .post("/auth/oauth/v5/token");

        return response.jsonPath().getString("access_token");
    }

    public static String getAuthToken(String username, String password) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_URL + "/auth/oauth/v4/token"))
                .header("accept", "application/vnd.oracle.resource+json")
                .header("aesite", "AEO_US")
               // .header("aelang", "en_US")
                .header("authorization", "Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==") // 🔐 То же самое: client_id:secret
                .header("x-access-token", xAccessType)
                .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")

                .header("aelang", "en_US")
                .header("origin", "https://www.ae.com")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36")
                .header("cookie", cookie)
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .build();

        System.out.println("➡️ Request URL: " + request.uri());
        System.out.println("🧾 Headers: " + request.headers().map());
        System.out.println("📨 Body: " + formData);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String json = response.body();
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString("access_token");
        } else {
            throw new RuntimeException("Failed to get token. Status code: " + response.statusCode()
                    + "\nResponse: " + response.body());
        }
    }
}
