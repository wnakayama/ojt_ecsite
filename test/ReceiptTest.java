package ojt_ecsite;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 購入明細を扱うデータクラスのテスト.
 *
 * @author nakayama
 *
 */
public class ReceiptTest {
    @Test
    public void testGetPurchaseDateTime() {
        Receipt receipt = new Receipt("2020/02/10", null, 0, 0);
        String result = receipt.getPurchasedDateTime();
        assertThat(result, is("2020/02/10"));
    }

    @Test
    public void testGetPurchasedProductList() {
        List<Product> productListForTest = new ArrayList<>();
        List<Product> expected = new ArrayList<>();
        Product productForTest1 = new Product(1, "テスト1", "飲食料品", 100, 108, null);
        Product productForTest2 = new Product(2, "テスト2", "飲食料品", 200, 216, null);
        Product productForTest3 = new Product(3, "テスト3", "酒類", 300, 330, null);
        productListForTest.add(productForTest1);
        productListForTest.add(productForTest2);
        productListForTest.add(productForTest3);

        expected.add(productForTest1);
        expected.add(productForTest2);
        expected.add(productForTest3);

        Receipt receipt = new Receipt(null, productListForTest, 0, 0);
        List<Product> result = receipt.getPurchasedProductList();

        assertThat(result, is(expected));
        assertThat(result.get(0).getName(), is(expected.get(0).getName()));
        assertThat(result.get(1).getName(), is(expected.get(1).getName()));
        assertThat(result.get(2).getName(), is(expected.get(2).getName()));
    }

    @Test
    public void testGetTotalPriceExcludeTax() {
        Receipt receipt = new Receipt(null, null, 410, 442);
        int result = receipt.getTotalPriceExcludeTax();
        assertThat(result, is(410));
    }

    @Test
    public void testGetTotalPriceIncludeTax() {
        Receipt receipt = new Receipt(null, null, 410, 442);
        int result = receipt.getTotalPriceIncludeTax();
        assertThat(result, is(442));
    }

}
