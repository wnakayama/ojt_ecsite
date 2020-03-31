package ojt_ecsite;

import java.util.Map;

/**
 * ユーザー入力で指定された検索条件がバリデーション条件に違反していないか検証するクラス.
 *
 * @author nakayama
 *
 */
public class InputValidator {
    private final int KEYWORD_UPPERLIMIT = 250;
    private final int PRICE_DIGIT_UPPERLIMIT = 9;

    enum ValidationResult {
        VALID, INVALID_ALL_INPUT_EMPTY, INVALID_CONTAINS_QUOTATION, INVALID_EXCEEDS_CHARACTERS,
        INVALID_NOT_UNSIGNED_INTEGER, INVALID_REVERSED_PRICE_RANGE,

        INVALID_NECESSARY_INPUT_EMPTY, INVALID_NOT_IMAGE_FILE, INVALID_EXCEEDS_FILE_SIZE
    }

    /**
     * ユーザー入力で指定された検索条件がバリデーション条件に違反していないか検証するクラス.
     *
     * @param inputParameterMap ユーザー入力で指定された検索条件を格納したMap
     * @return ValidationResult 検証結果
     */
    public ValidationResult validateSearchParameters(Map<String, String[]> inputParameterMap) {
        String inputProductName = "";
        String inputMinPrice = "";
        String inputMaxPrice = "";

        if (inputParameterMap.get(InputConstant.KEY_PRODUCTNAME) != null
                && inputParameterMap.get(InputConstant.KEY_PRODUCTNAME)[InputConstant.FIRST_VALUE] != null
                && !inputParameterMap.get(InputConstant.KEY_PRODUCTNAME)[InputConstant.FIRST_VALUE].isEmpty()) {
            inputProductName = inputParameterMap.get(InputConstant.KEY_PRODUCTNAME)[InputConstant.FIRST_VALUE];
        }

        if (inputParameterMap.get(InputConstant.KEY_MINPRICE) != null
                && inputParameterMap.get(InputConstant.KEY_MINPRICE)[InputConstant.FIRST_VALUE] != null
                && !inputParameterMap.get(InputConstant.KEY_MINPRICE)[InputConstant.FIRST_VALUE].isEmpty()) {
            inputMinPrice = inputParameterMap.get(InputConstant.KEY_MINPRICE)[InputConstant.FIRST_VALUE];
        }

        if (inputParameterMap.get(InputConstant.KEY_MAXPRICE) != null
                && inputParameterMap.get(InputConstant.KEY_MAXPRICE)[InputConstant.FIRST_VALUE] != null
                && !inputParameterMap.get(InputConstant.KEY_MAXPRICE)[InputConstant.FIRST_VALUE].isEmpty()) {
            inputMaxPrice = inputParameterMap.get(InputConstant.KEY_MAXPRICE)[InputConstant.FIRST_VALUE];
        }

        // 必須入力項目(商品名,カテゴリ,税抜き価格)のパラメータが3つともnullもしくは空であればエラーを返す
        if ((inputProductName == null || inputProductName.isEmpty())
                && (inputMinPrice == null || inputMinPrice.isEmpty())
                && (inputMaxPrice == null || inputMaxPrice.isEmpty())) {
            return ValidationResult.INVALID_ALL_INPUT_EMPTY;
        }

        //
        // 商品名にクォーテーションが含まれていたらエラーを返す
        if (inputProductName != null && !inputProductName.isEmpty()) {
            if (inputProductName.contains("'") || inputProductName.contains("\"")) {
                return ValidationResult.INVALID_CONTAINS_QUOTATION;
            }
        }

        // 商品名が251文字以上,価格が10文字以上のいずれかでエラーを返す
        if (inputProductName.length() > KEYWORD_UPPERLIMIT) {
            return ValidationResult.INVALID_EXCEEDS_CHARACTERS;
        }
        if (inputMinPrice.length() > PRICE_DIGIT_UPPERLIMIT) {
            return ValidationResult.INVALID_EXCEEDS_CHARACTERS;
        }
        if (inputMaxPrice.length() > PRICE_DIGIT_UPPERLIMIT) {
            return ValidationResult.INVALID_EXCEEDS_CHARACTERS;
        }

        // 下限価格,上限価格に入力があるとき
        // String型→Int型への変換を実施し,正常に出来なければエラーを返す
        // 1以上の整数でなければエラーを返す
        try {
            int parsedMinPrice = 1;
            int parsedMaxPrice = 1;
            if (inputMinPrice != null && !inputMinPrice.isEmpty()) {
                parsedMinPrice = Integer.parseInt(inputMinPrice);
            }
            if (inputMaxPrice != null && !inputMaxPrice.isEmpty()) {
                parsedMaxPrice = Integer.parseInt(inputMaxPrice);
            }

            if (parsedMinPrice <= 0 || parsedMaxPrice <= 0) {
                return ValidationResult.INVALID_NOT_UNSIGNED_INTEGER;
            }

            // 下限価格と上限価格の両方に指定があったときのみ,以下のチェックを実施
            // 下限価格 > 上限価格であればエラーを返す
            if ((inputMinPrice != null && !inputMinPrice.isEmpty())
                    && (inputMaxPrice != null && !inputMaxPrice.isEmpty())) {
                if (parsedMinPrice > parsedMaxPrice) {
                    return ValidationResult.INVALID_REVERSED_PRICE_RANGE;
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ValidationResult.INVALID_NOT_UNSIGNED_INTEGER;
        }

        // 正しく条件指定されている場合
        return ValidationResult.VALID;
    }

    /**
     * ユーザーが入力した商品情報がバリデーション条件に違反していないか検証するクラス.
     *
     * @param inputParameterMap ユーザー入力で指定された検索条件を格納したMap
     * @return ValidationResult 検証結果
     */
    public ValidationResult validateRegisterParameters(Map<String, String[]> inputParameterMap) {
        String inputProductName = "";
        String inputCategory = "";
        String inputPriceExcludeTax = "";
        /*
         * 【未実装】画像データファイルの変数
         */

        if (inputParameterMap.get(InputConstant.KEY_PRODUCTNAME) != null
                && inputParameterMap.get(InputConstant.KEY_PRODUCTNAME)[InputConstant.FIRST_VALUE] != null
                && !inputParameterMap.get(InputConstant.KEY_PRODUCTNAME)[InputConstant.FIRST_VALUE].isEmpty()) {
            inputProductName = inputParameterMap.get(InputConstant.KEY_PRODUCTNAME)[InputConstant.FIRST_VALUE];
        }

        if (inputParameterMap.get(InputConstant.KEY_CATEGORY) != null
                && inputParameterMap.get(InputConstant.KEY_CATEGORY)[InputConstant.FIRST_VALUE] != null
                && !inputParameterMap.get(InputConstant.KEY_CATEGORY)[InputConstant.FIRST_VALUE].isEmpty()) {
            inputCategory = inputParameterMap.get(InputConstant.KEY_CATEGORY)[InputConstant.FIRST_VALUE];
        }

        if (inputParameterMap.get(InputConstant.KEY_PRICE_EXCLUDE_TAX) != null
                && inputParameterMap.get(InputConstant.KEY_PRICE_EXCLUDE_TAX)[InputConstant.FIRST_VALUE] != null
                && !inputParameterMap.get(InputConstant.KEY_PRICE_EXCLUDE_TAX)[InputConstant.FIRST_VALUE].isEmpty()) {
            inputPriceExcludeTax = inputParameterMap
                    .get(InputConstant.KEY_PRICE_EXCLUDE_TAX)[InputConstant.FIRST_VALUE];
        }

        // 必須入力項目(商品名,カテゴリ,画像データ)のうち,1つでもnullもしくは空であればエラーを返す
        if ((inputProductName == null || inputProductName.isEmpty())
                || (inputCategory == null || inputCategory.isEmpty())
                || (inputPriceExcludeTax == null || inputPriceExcludeTax.isEmpty())) {
            return ValidationResult.INVALID_NECESSARY_INPUT_EMPTY;
        }

        //
        // 商品名にクォーテーションが含まれていたらエラーを返す
        if (inputProductName != null && !inputProductName.isEmpty()) {
            if (inputProductName.contains("'") || inputProductName.contains("\"")) {
                return ValidationResult.INVALID_CONTAINS_QUOTATION;
            }
        }

        // 商品名が251文字以上,税抜き価格が10文字以上のいずれかでエラーを返す
        if (inputProductName.length() > KEYWORD_UPPERLIMIT) {
            return ValidationResult.INVALID_EXCEEDS_CHARACTERS;
        }
        if (inputPriceExcludeTax.length() > PRICE_DIGIT_UPPERLIMIT) {
            return ValidationResult.INVALID_EXCEEDS_CHARACTERS;
        }

        try {
            int parsedPriceExcludeTax = 1;
            if (inputPriceExcludeTax != null && !inputPriceExcludeTax.isEmpty()) {
                // String型→Int型への変換を実施し,正常に出来なければエラーを返す
                parsedPriceExcludeTax = Integer.parseInt(inputPriceExcludeTax);
            }
            // 1以上の整数でなければエラーを返す
            if (parsedPriceExcludeTax <= 0) {
                return ValidationResult.INVALID_NOT_UNSIGNED_INTEGER;
            }

            /*
             * 【未実装】画像ファイルの拡張子がjpeg gif pngのいずれかになっていることを確認
             */

            /*
             * 【未実装】画像ファイルのデータサイズが10MB以下であることを確認
             */

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ValidationResult.INVALID_NOT_UNSIGNED_INTEGER;
        }

        // 正しく条件指定されている場合
        return ValidationResult.VALID;
    }
}
