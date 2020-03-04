package ojt_ecsite;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * ユーザー入力で指定された検索条件がバリデーション条件に違反していないか検証するクラス.
 *
 * @author nakayama
 *
 */
public class InputChecker {

    private final int KEYWORD_UPPERLIMIT = 250;
    private final int PRICE_DIGIT_UPPERLIMIT = 9;

    enum CheckResult {
        VALID, INVALID_ALL_INPUT_EMPTY, INVALID_CONTAINS_QUOTATION, INVALID_EXCEEDS_CHARACTERS,
        INVALID_NOT_UNSIGNED_INTEGER, INVALID_REVERSED_PRICE_RANGE,
    }

    /**
     * ユーザー入力で指定された検索条件がバリデーション条件に違反していないか検証するクラス.
     *
     * @param inputParameterMap ユーザー入力で指定された検索条件を格納したMap
     * @return CheckResult 検証結果
     */
    public CheckResult validateSearchParameters(Map<String, String[]> inputParameterMap) {
        String inputProductName = inputParameterMap.get("productName")[0];
        String inputMinPrice = inputParameterMap.get("minPrice")[0];
        String inputMaxPrice = inputParameterMap.get("maxPrice")[0];

        // パラメータが3つともnullもしくは空であればエラーを返す
        if (StringUtils.isEmpty(inputProductName) && StringUtils.isEmpty(inputMinPrice)
                && StringUtils.isEmpty(inputMaxPrice)) {
            return CheckResult.INVALID_ALL_INPUT_EMPTY;
        }

        // 商品名にクォーテーションが含まれていたらエラーを返す
        if (inputProductName.contains("'") || inputProductName.contains("\"")) {
            return CheckResult.INVALID_CONTAINS_QUOTATION;
        }

        // 商品名が251文字以上,価格が10文字以上のいずれかでエラーを返す
        if (inputProductName.length() > KEYWORD_UPPERLIMIT) {
            return CheckResult.INVALID_EXCEEDS_CHARACTERS;
        }
        if (inputMinPrice.length() > PRICE_DIGIT_UPPERLIMIT) {
            return CheckResult.INVALID_EXCEEDS_CHARACTERS;
        }
        if (inputMaxPrice.length() > PRICE_DIGIT_UPPERLIMIT) {
            return CheckResult.INVALID_EXCEEDS_CHARACTERS;
        }

        // String型→Int型への変換を実施し,正常に出来なければエラーを返す
        // 1以上の整数でなければエラーを返す
        try {
            int parsedMinPrice = 1;
            int parsedMaxPrice = 1;
            if (!StringUtils.isEmpty(inputMinPrice)) {
                parsedMinPrice = Integer.parseInt(inputMinPrice);
            }
            if (!StringUtils.isEmpty(inputMaxPrice)) {
                parsedMaxPrice = Integer.parseInt(inputMaxPrice);
            }

            if (parsedMinPrice <= 0 || parsedMaxPrice <= 0) {
                return CheckResult.INVALID_NOT_UNSIGNED_INTEGER;
            }

            // 下限価格 > 上限価格であればエラーを返す
            if (!StringUtils.isEmpty(inputMinPrice) && !StringUtils.isEmpty(inputMaxPrice)) {
                if (parsedMinPrice > parsedMaxPrice) {
                    return CheckResult.INVALID_REVERSED_PRICE_RANGE;
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return CheckResult.INVALID_NOT_UNSIGNED_INTEGER;
        }

        // 正しく条件指定されている場合
        return CheckResult.VALID;
    }
}
