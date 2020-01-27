package ojt_ecsite;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 商品データを扱うデータクラスのテスト.
 *
 * @author nakayama
 *
 */
public class ProductTest {

    @Test
    public void testGetProductID() {
        Product product = new Product(1, null, null, 0, 0);
        int result = product.getProductID();
        assertThat(result, is(1));
    }

    @Test
    public void testGetName() {
        Product product = new Product(0, "きのみジュース", null, 0, 0);
        String result = product.getName();
        assertThat(result, is("きのみジュース"));
    }

    @Test
    public void testGetCategory() {
        Product product = new Product(0, null, "酒類", 0, 0);
        String result = product.getCategory();
        assertThat(result, is("酒類"));
    }

    @Test
    public void testGetPriceWithoutTax() {
        Product product = new Product(0, null, null, 100, 0);
        int result = product.getPriceWithoutTax();
        assertThat(result, is(100));
    }

    @Test
    public void testGetPriceIncludeTax() {
        Product product = new Product(1, null, null, 0, 108);
        int result = product.getPriceIncludeTax();
        assertThat(result, is(108));
    }
}
