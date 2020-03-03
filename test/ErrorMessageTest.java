package ojt_ecsite;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ErrorMessageTest {

    @Test
    public void allInputEmpty() {
        ErrorMessage result = new ErrorMessage(01);
        assertThat(result.getErrorMessage(), is("すべてのフォームに入力がありませんでした 条件を指定してから検索を実行してください"));
    }

    @Test
    public void exceedCharacters() {
        ErrorMessage result = new ErrorMessage(02);
        assertThat(result.getErrorMessage(), is("商品名は250文字,価格は9文字まで入力を受け付けます 入力を減らして再度お試しください"));
    }

    @Test
    public void inputNotUnsignedInteger() {
        ErrorMessage result = new ErrorMessage(03);
        assertThat(result.getErrorMessage(), is("検索フォーム(価格)の入力が正しくありません(整数で価格を指定してください)"));
    }

    @Test
    public void reversedPriceRange() {
        ErrorMessage result = new ErrorMessage(04);
        assertThat(result.getErrorMessage(), is("価格の範囲指定が正しくありません(価格フォームの左側に下限値,右側に上限値を入力してください)"));
    }

    @Test
    public void containsQuotation() {
        ErrorMessage result = new ErrorMessage(05);
        assertThat(result.getErrorMessage(), is("シングルクォーテーション(\')またはダブルクォーテーション(\")が含まれる文字列では検索できません"));
    }

    @Test
    public void unexpectedValidationError() {
        ErrorMessage result = new ErrorMessage(99);
        assertThat(result.getErrorMessage(), is("予期せぬエラーが発生しました お手数ですが開発者までご連絡ください"));
    }

}
