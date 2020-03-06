package ojt_ecsite;

/**
 * コンストラクタでエラーIDを引数に受け取り,IDに応じたエラーメッセージのオブジェクトを生成するクラス.
 *
 * @author nakayama
 *
 */
public class ErrorMessage {
    private final String errorMessage;

    private static final int ID_ALL_INPUT_EMPTY = 1;
    private static final int ID_EXCEEDS_CHARACTERS = 2;
    private static final int ID_NOT_UNSIGNED_INTEGER = 3;
    private static final int ID_REVERSED_PRICE_RANGE = 4;
    private static final int ID_CONTAINS_QUOTATION = 5;
    private static final int ID_UNEXPECTED_STATE = 9;

    public String getErrorMessage() {
        return errorMessage;
    }

    ErrorMessage(int errorId) {
        switch (errorId) {
            case ID_ALL_INPUT_EMPTY:
                this.errorMessage = "すべてのフォームに入力がありませんでした 条件を指定してから検索を実行してください";
                break;
            case ID_EXCEEDS_CHARACTERS:
                this.errorMessage = "商品名は250文字,価格は9文字まで入力を受け付けます 入力を減らして再度お試しください";
                break;
            case ID_NOT_UNSIGNED_INTEGER:
                this.errorMessage = "検索フォーム(価格)の入力が正しくありません(整数で価格を指定してください)";
                break;
            case ID_REVERSED_PRICE_RANGE:
                this.errorMessage = "価格の範囲指定が正しくありません(価格フォームの左側に下限値,右側に上限値を入力してください)";
                break;
            case ID_CONTAINS_QUOTATION:
                this.errorMessage = "シングルクォーテーション(\')またはダブルクォーテーション(\")が含まれる文字列では検索できません";
                break;
            case ID_UNEXPECTED_STATE:
                this.errorMessage = "予期せぬエラーが発生しました お手数ですが開発者までご連絡ください";
                break;
            default:
                this.errorMessage = "予期せぬエラーが発生しました お手数ですが開発者までご連絡ください";
                break;
        }
    }

}
