package ojt_ecsite;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * ユーザー入力で指定された検索条件がバリデーション条件に違反していないか検証するクラスのテスト.
 *
 * @author nakayama
 *
 */
public class InputCheckerTest {

    /**
     * 商品名,下限価格,上限価格のいずれも正しく指定しているとき, 合格を示す結果"VALID"を返す.
     *
     * @param map ユーザー入力で指定された検索条件を格納したMap
     * @return checkResult 検証結果
     */
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

    /**
     * 商品名,下限価格,上限価格のいずれも指定していないとき, 不合格を示す結果"INVALID_ALL_INPUT_EMPTY"を返す.
     */
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

    /**
     * シングルクォーテーション(')を含む商品名を指定しているとき, 不合格を示す結果"INVALID_CONTAINS_QUOTATION"を返す.
     */
    @Test
    public void containsSingleQuotation() {
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

    /**
     * ダブルクォーテーション(")を含む商品名を指定しているとき, 不合格を示す結果"INVALID_CONTAINS_QUOTATION"を返す.
     */
    @Test
    public void containsDoubleQuotation() {
        Map<String, String[]> map = new HashMap<>();
        String[] NameArray = { "\"" };
        String[] minPriceArray = { "100" };
        String[] maxPriceArray = { "500" };
        map.put("productName", NameArray);
        map.put("minPrice", minPriceArray);
        map.put("maxPrice", maxPriceArray);

        InputChecker inputChecker = new InputChecker();
        ojt_ecsite.InputChecker.CheckResult checkResult = inputChecker.validateSearchParameters(map);

        assertThat(checkResult, is(checkResult.INVALID_CONTAINS_QUOTATION));
    }

    /**
     * 文字数制限(250文字)に達した商品名を指定しているとき, 合格を示す結果"VALID"を返す.
     */
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

    /**
     * 文字数制限(250文字)を超えた商品名を指定しているとき, 不合格を示す結果"INVALID_EXCEEDS_CHARACTERS"を返す.
     */
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

    /**
     * 文字数制限(9文字)に達した下限価格を指定しているとき, 合格を示す結果"VALID"を返す.
     */
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

    /**
     * 文字数制限(9文字)を超えた下限価格を指定しているとき, 不合格を示す結果"INVALID_EXCEEDS_CHARACTERS"を返す.
     */
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

    /**
     * 文字数制限(9文字)に達した上限価格を指定しているとき, 合格を示す結果"VALID"を返す.
     */
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

    /**
     * 文字数制限(9文字)を超えた上限価格を指定しているとき, 不合格を示す結果"INVALID_EXCEEDS_CHARACTERS"を返す.
     */
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

    /**
     * 下限価格に0を指定しているとき, 不合格を示す結果"INVALID_NOT_UNSIGNED_INTEGER"を返す.
     */
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

    /**
     * 下限価格に負の値を指定しているとき, 不合格を示す結果"INVALID_NOT_UNSIGNED_INTEGER"を返す.
     */
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

    /**
     * 下限価格に文字列を指定しているとき, 不合格を示す結果"INVALID_NOT_UNSIGNED_INTEGER"を返す.
     */
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

    /**
     * 上限価格に0を指定しているとき, 不合格を示す結果"INVALID_NOT_UNSIGNED_INTEGER"を返す.
     */
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

    /**
     * 上限価格に負の値を指定しているとき, 不合格を示す結果"INVALID_NOT_UNSIGNED_INTEGER"を返す.
     */
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

    /**
     * 上限価格に文字列を指定しているとき, 不合格を示す結果"INVALID_NOT_UNSIGNED_INTEGER"を返す.
     */
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

    /**
     * 指定した下限価格が上限価格を上回っているとき, 不合格を示す結果"INVALID_REVERSED_PRICE_RANGE"を返す.
     */
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
