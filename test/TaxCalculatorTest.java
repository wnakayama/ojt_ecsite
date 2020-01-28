package ojt_ecsite;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 税込み価格を算出するクラスのテスト.
 *
 * @author nakayama
 *
 */
public class TaxCalculatorTest {

    /**
     * 商品カテゴリが"飲食料品"であったとき,軽減税率を適用して税抜き価格を1.08倍する. 税込み価格はint型にキャストし,小数点以下を切り捨てる.
     *
     * @param category        商品カテゴリ
     * @param priceExcludeTax 税抜き価格
     */
    @Test
    public void applyReducedTaxrate() {
        String category = "飲食料品";
        int priceExcludeTax = 1000;
        TaxCalculator taxCalculator = new TaxCalculator();
        int priceIncludeTax = taxCalculator.calculatePriceIncludeTax(category, priceExcludeTax);

        assertThat(priceIncludeTax, is((int) (priceExcludeTax * 1.08)));
    }

    /**
     * 商品カテゴリが"飲食料品"以外のとき,標準税率を適用して税抜き価格を1.10倍する. 税込み価格はint型にキャストし,小数点以下を切り捨てる.
     */
    @Test
    public void applyStandardTaxrate() {
        String category = "酒類";
        int priceExcludeTax = 1000;
        TaxCalculator taxCalculator = new TaxCalculator();
        int priceIncludeTax = taxCalculator.calculatePriceIncludeTax(category, priceExcludeTax);

        assertThat(priceIncludeTax, is((int) (priceExcludeTax * 1.10)));
    }

    /**
     * 商品カテゴリがnullのとき,TaxCalculatorクラス単体ではNullPointerExceptionが発生する
     */
    @Test(expected = NullPointerException.class)
    public void categoryIsNull() {
        String category = null;
        int priceExcludeTax = 1000;
        TaxCalculator taxCalculator = new TaxCalculator();
        taxCalculator.calculatePriceIncludeTax(category, priceExcludeTax);
    }

    /**
     * 商品カテゴリが空文字列のとき,TaxCalculatorクラス単体では正常に税込み価格を算出する.
     * "飲食料品"ではないと判定し,標準税率を適用して税抜き価格を1.10倍する. 税込み価格はint型にキャストし,小数点以下を切り捨てる.
     */
    @Test
    public void categoryIsEmptyString() {
        String category = "";
        int priceExcludeTax = 1000;
        TaxCalculator taxCalculator = new TaxCalculator();
        int priceIncludeTax = taxCalculator.calculatePriceIncludeTax(category, priceExcludeTax);

        assertThat(priceIncludeTax, is((int) (priceExcludeTax * 1.10)));
    }

    /**
     * 税抜き価格にマイナスの値が渡されたとき,TaxCalculatorクラス単体では正常に税込み価格を算出する.
     * 商品カテゴリが"飲食料品"であったとき,軽減税率を適用して税抜き価格を1.08倍する. 税込み価格はint型にキャストし,小数点以下を切り捨てる.
     */
    @Test
    public void applyReducedTaxrateToMinusPrice() {
        String category = "飲食料品";
        int priceExcludeTax = -1000;
        TaxCalculator taxCalculator = new TaxCalculator();
        int priceIncludeTax = taxCalculator.calculatePriceIncludeTax(category, priceExcludeTax);

        assertThat(priceIncludeTax, is((int) (priceExcludeTax * 1.08)));
    }

    /**
     * 税抜き価格にマイナスの値が渡されたとき,TaxCalculatorクラス単体では正常に税込み価格を算出する.
     * 商品カテゴリが"飲食料品"以外のとき,標準税率を適用して税抜き価格を1.10倍する. 税込み価格はint型にキャストし,小数点以下を切り捨てる.
     */
    @Test
    public void applyStandardTaxrateToMinusPrice() {
        String category = "酒類";
        int priceExcludeTax = -1000;
        TaxCalculator taxCalculator = new TaxCalculator();
        int priceIncludeTax = taxCalculator.calculatePriceIncludeTax(category, priceExcludeTax);

        assertThat(priceIncludeTax, is((int) (priceExcludeTax * 1.10)));
    }

}
