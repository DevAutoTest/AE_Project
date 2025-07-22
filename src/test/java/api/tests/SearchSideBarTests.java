package api.tests;

import api.controllers.search.GET_cstr_v1_search;
import api.dto.SearchResponse;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchSideBarTests {

    private final GET_cstr_v1_search search = new GET_cstr_v1_search();
    SearchResponse searchResp;

    @Test
    void getSearchResultTest() {
        int countResponse;
        int totalNum;
        searchResp = search.getAllSearchResponse();
        countResponse = searchResp.getData().relationships.products.data.size();
        totalNum = searchResp.getData().attributes.totalNumResults;

        assertThat(searchResp).isNotNull();
        assertThat(totalNum).isEqualTo(countResponse);
    }

    @Test
    void getSearchResultByBrandTest() {
        List<SearchResponse.Included> searchItemsResult = search.getResponseFilteredByBrand().included;
        int size = searchItemsResult.size();


        for (SearchResponse.Included included : searchItemsResult) {
            assertThat(included.attributes.brandName)
                    .isEqualTo("AE");
        }
    }

    @Test
    void getSearchResultByGenderTest() {

        searchResp = search.getResponseFilteredByGender();
        System.out.println(searchResp);
        String expectedGender = "Men";
        List<String> filteredRslts = searchResp.getData().attributes.request.filters.getGender();

        assertThat(searchResp).isNotNull();
        assertThat(filteredRslts.contains(expectedGender))
                .as("Filtered results contain «%s»", expectedGender)
                .isTrue();
    }

    @Test
    void checkSearchResultFilteredByBrandTest() {
        searchResp = search.getResponseFilterWasCheckedByBrand();
        System.out.println(searchResp);

        Optional<SearchResponse.Filter> brandFilterOpt =
                searchResp.getData().getAttributes().getFilters().stream()
                        .filter(f -> "brand".equals(f.getName()))
                        .findFirst();

        Optional<SearchResponse.Option> exist = brandFilterOpt
                .get()
                .options
                .stream()
                .filter(o -> "selected".equals(o.status) &&
                        "Aerie".equals(o.getValue()))
                .findFirst();
        assertThat(exist).isNotNull();
    }

    @Test
    void getZeroResultsTest() {
        searchResp = search.getZeroResults();
        int actualNum = searchResp.getData().attributes.totalNumResults;
        int expNum = 0;
        assertThat(actualNum).isEqualTo(expNum);
    }
}

