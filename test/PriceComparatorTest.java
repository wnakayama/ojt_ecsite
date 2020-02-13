package ojt_ecsite;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 税込み価格を比較するクラスのテスト.
 *
 * @author nakayama
 *
 */
public class PriceComparatorTest {

    /**
     * 2つの商品データのうち,1つ目の商品の税込み価格が高いとき,compareメソッドは1を返却する.
     */
    @Test
    public void firstProductIsMoreExpensive() {
        int priceIncludeTax = 0;
        Product firstProduct = new Product(1, null, null, 0, priceIncludeTax + 1, null);
        Product secondProduct = new Product(2, null, null, 0, priceIncludeTax, null);
        PriceComparator priceComparator = new PriceComparator();

        int compareResult = priceComparator.compare(firstProduct, secondProduct);
        assertThat(compareResult, is(1));
    }

    /**
     * 2つの商品データの税込み価格が同じであるとき,compareメソッドは0を返却する.
     */
    @Test
    public void evenPrice() {
        int priceIncludeTax = 0;
        Product firstProduct = new Product(1, null, null, 0, priceIncludeTax, null);
        Product secondProduct = new Product(2, null, null, 0, priceIncludeTax, null);
        PriceComparator priceComparator = new PriceComparator();

        int compareResult = priceComparator.compare(firstProduct, secondProduct);

        assertThat(compareResult, is(0));
    }

    /**
     * 2つの商品データのうち,2つ目の商品の税込み価格が高いとき,compareメソッドは-1を返却する.
     */
    @Test
    public void secondProductIsMoreExpensive() {
        int priceIncludeTax = 0;
        Product firstProduct = new Product(1, null, null, 0, priceIncludeTax, null);
        Product secondProduct = new Product(2, null, null, 0, priceIncludeTax + 1, null);
        PriceComparator priceComparator = new PriceComparator();

        int compareResult = priceComparator.compare(firstProduct, secondProduct);
        assertThat(compareResult, is(-1));
    }

}
