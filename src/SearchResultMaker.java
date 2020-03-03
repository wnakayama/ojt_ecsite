package ojt_ecsite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SearchResultMaker {

    public String makeSearchResult(Map<String, String[]> inputParameterMap) throws JsonProcessingException {
        // 入力チェック
        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(inputParameterMap);

        ObjectMapper mapper = new ObjectMapper();
        String searchResultJson;
        switch (checkResult) {
            case VALID:
                // 検索条件をオブジェクトにセット
                int minPrice = 0;
                int maxPrice = 0;
                if (!StringUtils.isEmpty(inputParameterMap.get("minPrice")[0])) {
                    minPrice = Integer.parseInt(inputParameterMap.get("minPrice")[0]);
                }
                if (!StringUtils.isEmpty(inputParameterMap.get("maxPrice")[0])) {
                    maxPrice = Integer.parseInt(inputParameterMap.get("maxPrice")[0]);
                }
                SearchParameter searchParameter = new SearchParameter(inputParameterMap.get("productName")[0], minPrice,
                        maxPrice);

                // 検索を実行、結果をList<Product>で取得
                List<Product> foundProductList = new ArrayList<>();
                foundProductList = SearchEngine.searchProductData(searchParameter);
                searchResultJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(foundProductList);
                break;

            case INVALID_ALL_INPUT_EMPTY:
                ErrorMessage messageAllEmpty = new ErrorMessage(01);
                searchResultJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageAllEmpty);
                break;

            case INVALID_CONTAINS_QUOTATION:
                ErrorMessage messageContainsQuotation = new ErrorMessage(05);
                searchResultJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageContainsQuotation);
                break;

            case INVALID_EXCEEDS_CHARACTERS:
                ErrorMessage messageExceedsCharacters = new ErrorMessage(02);
                searchResultJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageExceedsCharacters);
                break;

            case INVALID_NOT_UNSIGNED_INTEGER:
                ErrorMessage messageNotUnsignedInteger = new ErrorMessage(03);
                searchResultJson = mapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(messageNotUnsignedInteger);
                break;

            case INVALID_REVERSED_PRICE_RANGE:
                ErrorMessage messageReversedPriceRange = new ErrorMessage(04);
                searchResultJson = mapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(messageReversedPriceRange);
                break;

            default:
                ErrorMessage messageUnexpectedState = new ErrorMessage(99);
                searchResultJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageUnexpectedState);
                break;
        }
        return searchResultJson;

    }

}
