package ojt_ecsite;

/**
 * コンストラクタでエラーID(列挙型)を引数に受け取り,IDに応じたエラーメッセージのオブジェクトを生成するクラス.
 *
 * @author nakayama
 *
 */
public class ErrorMessage {
    private final String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    ErrorMessage(ErrorId errorId) {
        switch (errorId) {
            case ALL_INPUT_EMPTY:
                this.errorMessage = "すべてのフォームに入力がありませんでした 条件を指定してから検索を実行してください";
                break;
            case EXCEEDS_CHARACTERS:
                this.errorMessage = "商品名は250文字,価格は9文字まで入力を受け付けます 入力を減らして再度お試しください";
                break;
            case NOT_UNSIGNED_INTEGER:
                this.errorMessage = "検索フォーム(価格)の入力が正しくありません(整数で価格を指定してください)";
                break;
            case REVERSED_PRICE_RANGE:
                this.errorMessage = "価格の範囲指定が正しくありません(価格フォームの左側に下限値,右側に上限値を入力してください)";
                break;
            case CONTAINS_QUOTATION:
                this.errorMessage = "シングルクォーテーション(\')またはダブルクォーテーション(\")が含まれる文字列では検索できません";
                break;

            case NECESSARY_INPUT_EMPTY:
                this.errorMessage = "必須入力項目に入力がありません(商品名,カテゴリ,税抜き価格はすべて入力する必要があります)";
                break;
            case NOT_IMAGE_FILE:
                this.errorMessage = "ファイルアップロードは.jpeg .gif .pngのみ受け付けます";
                break;
            case EXCEEDS_FILE_SIZE:
                this.errorMessage = "アップロードできる画像ファイルサイズの上限を超えています(10MB以下の画像をご用意ください)";
                break;

            case UNEXPECTED_STATE:
                this.errorMessage = "予期せぬエラーが発生しました お手数ですが開発者までご連絡ください";
                break;
            default:
                this.errorMessage = "予期せぬエラーが発生しました お手数ですが開発者までご連絡ください";
                break;
        }
    }

}
