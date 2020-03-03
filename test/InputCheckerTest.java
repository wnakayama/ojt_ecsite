package ojt_ecsite;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class InputCheckerTest {

    @Test
    public void valid() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "AP" };
        String[] minPriceArray = { "100" };
        String[] maxPriceArray = { "500" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.VALID));
    }

    @Test
    public void allInputEmpty() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "" };
        String[] maxPriceArray = { "" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_ALL_INPUT_EMPTY));
    }

    @Test
    public void containsQuotation() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "'" };
        String[] minPriceArray = { "100" };
        String[] maxPriceArray = { "500" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_CONTAINS_QUOTATION));
    }

    @Test
    public void productNameMaxLength() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "親譲りの無鉄砲で小供の時から損ばかりしている。小学校に居る時分学校の二階から飛び降りて一週間ほど腰を抜かした事がある。"
                + "なぜそんな無闇をしたと聞く人があるかも知れぬ。別段深い理由でもない。新築の二階から首を出していたら、"
                + "同級生の一人が冗談に、いくら威張っても、そこから飛び降りる事は出来まい。弱虫やーい。と囃したからである。"
                + "小使に負ぶさって帰って来た時、おやじが大きな眼をして二階ぐらいから飛び降りて腰を抜かす奴があるかと云ったから、" + "この次は抜かさずに飛んで見せますと答えた。（青空文庫より）親譲りの無" };
        String[] minPriceArray = { "" };
        String[] maxPriceArray = { "" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.VALID));
    }

    @Test
    public void productNameExceedsLimit() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "親譲りの無鉄砲で小供の時から損ばかりしている。小学校に居る時分学校の二階から飛び降りて一週間ほど腰を抜かした事がある。"
                + "なぜそんな無闇をしたと聞く人があるかも知れぬ。別段深い理由でもない。新築の二階から首を出していたら、"
                + "同級生の一人が冗談に、いくら威張っても、そこから飛び降りる事は出来まい。弱虫やーい。と囃したからである。"
                + "小使に負ぶさって帰って来た時、おやじが大きな眼をして二階ぐらいから飛び降りて腰を抜かす奴があるかと云ったから、" + "この次は抜かさずに飛んで見せますと答えた。（青空文庫より）親譲りの無鉄" };
        String[] minPriceArray = { "" };
        String[] maxPriceArray = { "" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_EXCEEDS_CHARACTERS));
    }

    @Test
    public void minPriceMaxDigit() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "123456789" };
        String[] maxPriceArray = { "" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.VALID));
    }

    @Test
    public void minPriceExceedsLimit() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "1234567890" };
        String[] maxPriceArray = { "" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_EXCEEDS_CHARACTERS));
    }

    @Test
    public void maxPriceMaxDigit() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "" };
        String[] maxPriceArray = { "123456789" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.VALID));
    }

    @Test
    public void maxPriceExceedsLimit() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "" };
        String[] maxPriceArray = { "1234567890" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_EXCEEDS_CHARACTERS));
    }

    @Test
    public void minPriceZero() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "0" };
        String[] maxPriceArray = { "" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_NOT_UNSIGNED_INTEGER));
    }

    @Test
    public void minPriceMinus() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "-1" };
        String[] maxPriceArray = { "" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_NOT_UNSIGNED_INTEGER));
    }

    @Test
    public void minPriceNotInteger() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "abc" };
        String[] maxPriceArray = { "" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_NOT_UNSIGNED_INTEGER));
    }

    @Test
    public void maxPriceZero() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "" };
        String[] maxPriceArray = { "0" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_NOT_UNSIGNED_INTEGER));
    }

    @Test
    public void maxPriceMinus() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "" };
        String[] maxPriceArray = { "-1" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_NOT_UNSIGNED_INTEGER));
    }

    @Test
    public void maxPriceNotInteger() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "" };
        String[] maxPriceArray = { "abc" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_NOT_UNSIGNED_INTEGER));
    }

    @Test
    public void reversedPriceRange() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "" };
        String[] minPriceArray = { "301" };
        String[] maxPriceArray = { "300" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_REVERSED_PRICE_RANGE));
    }

}
