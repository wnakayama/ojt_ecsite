package ojt_ecsite;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * 利用者が選択した商品の合計金額を算出するクラスのテスト.
 *
 * @author nakayama
 *
 */
public class TotalPriceCalculatorTest {
    /**
     * 選択した各商品の税抜き価格が正常に合算されることをテストする.
     *
     * @param productListForTest 利用者が選択した商品のリストに見立てて用意した,サンプル商品のリスト
     */
    @Test
    public void calculateTotalPriceExcludeTax() {
        List<Product> productListForTest = new ArrayList<>();

        Product productForTest1 = new Product(1, null, null, 100, 108, null);
        Product productForTest2 = new Product(2, null, null, 200, 216, null);
        Product productForTest3 = new Product(3, null, null, 300, 330, null);
        productListForTest.add(productForTest1);
        productListForTest.add(productForTest2);
        productListForTest.add(productForTest3);

        TotalPriceCalculator totalCalc = new TotalPriceCalculator();
        int result = totalCalc.calculateTotalPriceExcludeTax(productListForTest);

        assertThat(result, is(600));
    }

    /**
     * 空のリストを引数で受け取った場合, 税抜き合計金額の算出は実施せず0を返却する.
     *
     * @param emptyListForTest 利用者が選択した商品のリストに見立てて用意した,空のリスト
     */
    @Test
    public void calculateTotalPriceExcludeTaxEmptyList() {
        List<Product> emptyListForTest = Collections.emptyList();

        TotalPriceCalculator totalCalc = new TotalPriceCalculator();
        int result = totalCalc.calculateTotalPriceExcludeTax(emptyListForTest);
        assertThat(result, is(0));
    }

    /**
     * 各商品の税抜き価格がすべてマイナスの値であるとき,正常に税抜き価格を合算する.
     *
     * @param productListForTest 利用者が選択した商品のリストに見立てて用意した,サンプル商品のリスト
     */
    @Test
    public void calculateTotalPriceExcludeTaxAllMinus() {
        List<Product> productListForTest = new ArrayList<>();

        Product productForTest1 = new Product(1, null, null, -100, -108, null);
        Product productForTest2 = new Product(2, null, null, -200, -216, null);
        Product productForTest3 = new Product(3, null, null, -300, -330, null);
        productListForTest.add(productForTest1);
        productListForTest.add(productForTest2);
        productListForTest.add(productForTest3);

        TotalPriceCalculator totalCalc = new TotalPriceCalculator();
        int result = totalCalc.calculateTotalPriceExcludeTax(productListForTest);
        assertThat(result, is(-600));
    }

    /**
     * 各商品の税抜き価格の一部がマイナスの値であるとき,正常に税抜き価格を合算する.
     *
     * @param productListForTest 利用者が選択した商品のリストに見立てて用意した,サンプル商品のリスト
     */
    @Test
    public void calculateTotalPriceExcludeTaxContainsMinus() {
        List<Product> productListForTest = new ArrayList<>();

        Product productForTest1 = new Product(1, null, null, 100, 108, null);
        Product productForTest2 = new Product(2, null, null, -200, -216, null);
        Product productForTest3 = new Product(3, null, null, 300, 330, null);
        productListForTest.add(productForTest1);
        productListForTest.add(productForTest2);
        productListForTest.add(productForTest3);

        TotalPriceCalculator totalCalc = new TotalPriceCalculator();
        int result = totalCalc.calculateTotalPriceExcludeTax(productListForTest);
        assertThat(result, is(200));
    }

    /**
     * 選択した各商品の税込み価格が正常に合算されることをテストする.
     *
     * @param productListForTest 利用者が選択した商品のリストに見立てて用意した,サンプル商品のリスト
     */
    @Test
    public void calculateTotalPriceIncludeTax() {
        List<Product> productListForTest = new ArrayList<>();

        Product productForTest1 = new Product(1, null, null, 100, 108, null);
        Product productForTest2 = new Product(2, null, null, 200, 216, null);
        Product productForTest3 = new Product(3, null, null, 300, 330, null);
        productListForTest.add(productForTest1);
        productListForTest.add(productForTest2);
        productListForTest.add(productForTest3);

        TotalPriceCalculator totalCalc = new TotalPriceCalculator();
        int result = totalCalc.calculateTotalPriceIncludeTax(productListForTest);

        assertThat(result, is(654));
    }

    /**
     * 空のリストを引数で受け取った場合, 税込み合計金額の算出は実施せず0を返却する.
     *
     * @param emptyListForTest 利用者が選択した商品のリストに見立てて用意した,空のリスト
     */
    @Test
    public void calculateTotalPriceIncludeTaxEmptyList() {
        List<Product> emptyListForTest = Collections.emptyList();

        TotalPriceCalculator totalCalc = new TotalPriceCalculator();
        int result = totalCalc.calculateTotalPriceIncludeTax(emptyListForTest);
        assertThat(result, is(0));
    }

    /**
     * 各商品の税込み価格がすべてマイナスの値であるとき,正常に税込み価格を合算する.
     *
     * @param productListForTest 利用者が選択した商品のリストに見立てて用意した,サンプル商品のリスト
     */
    @Test
    public void calculateTotalPriceIncludeTaxAllMinus() {
        List<Product> productListForTest = new ArrayList<>();

        Product productForTest1 = new Product(1, null, null, -100, -108, null);
        Product productForTest2 = new Product(2, null, null, -200, -216, null);
        Product productForTest3 = new Product(3, null, null, -300, -330, null);
        productListForTest.add(productForTest1);
        productListForTest.add(productForTest2);
        productListForTest.add(productForTest3);

        TotalPriceCalculator totalCalc = new TotalPriceCalculator();
        int result = totalCalc.calculateTotalPriceIncludeTax(productListForTest);
        assertThat(result, is(-654));
    }

    /**
     * 各商品の税込み価格の一部がマイナスの値であるとき,正常に税込み価格を合算する.
     *
     * @param productListForTest 利用者が選択した商品のリストに見立てて用意した,サンプル商品のリスト
     */
    @Test
    public void calculateTotalPriceIncludeTaxContainsMinus() {
        List<Product> productListForTest = new ArrayList<>();

        Product productForTest1 = new Product(1, null, null, 100, 108, null);
        Product productForTest2 = new Product(2, null, null, -200, -216, null);
        Product productForTest3 = new Product(3, null, null, 300, 330, null);
        productListForTest.add(productForTest1);
        productListForTest.add(productForTest2);
        productListForTest.add(productForTest3);

        TotalPriceCalculator totalCalc = new TotalPriceCalculator();
        int result = totalCalc.calculateTotalPriceIncludeTax(productListForTest);
        assertThat(result, is(222));
    }

    /**
     * 一部の商品オブジェクトがnullであるとき,TotalPriceCalculator単体ではNullPointerExceptionが発生する.
     * (結合後は基本的に発生しないケース)
     *
     * @param productListForTest 利用者が選択した商品のリストに見立てて用意した,サンプル商品のリスト
     */
    @Test(expected = NullPointerException.class)
    public void containsNullProduct() {
        List<Product> productListForTest = new ArrayList<>();

        Product productForTest1 = new Product(1, null, null, 100, 108, null);
        Product productForTest2 = null;
        Product productForTest3 = new Product(3, null, null, 300, 330, null);
        productListForTest.add(productForTest1);
        productListForTest.add(productForTest2);
        productListForTest.add(productForTest3);

        TotalPriceCalculator totalCalc = new TotalPriceCalculator();
        int result = totalCalc.calculateTotalPriceIncludeTax(productListForTest);
    }

}
