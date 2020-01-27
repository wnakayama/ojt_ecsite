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
     * @param priceWithoutTax 税抜き価格
     */
    @Test
    public void applyReducedTaxrate() {
        String category = "飲食料品";
        int priceWithoutTax = 1000;
        TaxCalculator taxCalculator = new TaxCalculator();
        int priceIncludeTax = taxCalculator.calculatePriceIncludeTax(category, priceWithoutTax);

        assertThat(priceIncludeTax, is((int) (priceWithoutTax * 1.08)));
    }

    /**
     * 商品カテゴリが"飲食料品"以外のとき,標準税率を適用して税抜き価格を1.10倍する. 税込み価格はint型にキャストし,小数点以下を切り捨てる.
     */
    @Test
    public void applyStandardTaxrate() {
        String category = "酒類";
        int priceWithoutTax = 1000;
        TaxCalculator taxCalculator = new TaxCalculator();
        int priceIncludeTax = taxCalculator.calculatePriceIncludeTax(category, priceWithoutTax);

        assertThat(priceIncludeTax, is((int) (priceWithoutTax * 1.10)));
    }

}
