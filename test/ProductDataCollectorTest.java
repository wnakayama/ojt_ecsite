package ojt_ecsite;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * 購入リクエストのあった商品IDを手掛かりに,利用者が購入したがっている商品は何か,全商品データから探し出すクラスのテスト.
 *
 * @author nakayama
 *
 */
public class ProductDataCollectorTest {
    /**
     * 購入したい商品のIDとして3,1,2を指定したとき,対応する商品が指定した順番で入ったList<Product>が返却される.
     *
     * @param allProductList     全商品データのリスト
     * @param requestedProductID 利用者が選択した商品のID
     */
    @Test
    public void testCollectProductData() {
        Product productForTest1 = new Product(1, "テスト1", "飲食料品", 100, 108, null);
        Product productForTest2 = new Product(2, "テスト2", "飲食料品", 200, 216, null);
        Product productForTest3 = new Product(3, "テスト3", "酒類", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(productForTest1);
        allProductList.add(productForTest2);
        allProductList.add(productForTest3);

        String[] requestProductID = { "3", "1", "2" };

        ProductDataCollector.setAllProductList(allProductList);
        List<Product> result = ProductDataCollector.collectProductData(requestProductID);

        assertThat(result, is(contains(productForTest3, productForTest1, productForTest2)));
    }

    /**
     * 指定した商品IDの一部が全商品データに存在しないとき,対応する商品だけが入ったList<Product>が返却される.
     * (結合後は基本的に発生しないケース)
     *
     * @param allProductList     全商品データのリスト
     * @param requestedProductID 利用者が選択した商品のID
     */
    @Test
    public void requestWrongProductId() {
        Product productForTest1 = new Product(1, "テスト1", "飲食料品", 100, 108, null);
        Product productForTest2 = new Product(2, "テスト2", "飲食料品", 200, 216, null);
        Product productForTest3 = new Product(3, "テスト3", "酒類", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(productForTest1);
        allProductList.add(productForTest2);
        allProductList.add(productForTest3);

        String[] requestProductID = { "3", "999", "2" };

        ProductDataCollector.setAllProductList(allProductList);
        List<Product> result = ProductDataCollector.collectProductData(requestProductID);

        assertThat(result, is(contains(productForTest3, productForTest2)));
        assertThat(result.size(), is(2));
    }

    /**
     * 存在しない商品IDだけが引数に含まれているとき,空のリストを返却する. (結合後は基本的に発生しないケース)
     *
     * @param allProductList     全商品データのリスト
     * @param requestedProductID 利用者が選択した商品のID
     */
    @Test
    public void allRequestProductIdAreWrong() {
        Product productForTest1 = new Product(1, "テスト1", "飲食料品", 100, 108, null);
        Product productForTest2 = new Product(2, "テスト2", "飲食料品", 200, 216, null);
        Product productForTest3 = new Product(3, "テスト3", "酒類", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(productForTest1);
        allProductList.add(productForTest2);
        allProductList.add(productForTest3);

        String[] requestProductID = { "-1", "0", "999" };

        ProductDataCollector.setAllProductList(allProductList);
        List<Product> result = ProductDataCollector.collectProductData(requestProductID);

        assertThat(result, is(empty()));
    }

    /**
     * リクエストしている商品のIDにnullが含まれているとき,
     * ProductDataCollector単体ではNumberFormatExceptionが発生する. (結合後は基本的に発生しないケース)
     *
     * @param allProductList     全商品データのリスト
     * @param requestedProductID 利用者が選択した商品のID
     */
    @Test(expected = NumberFormatException.class)
    public void requestProductIdContainsNull() {
        Product productForTest1 = new Product(1, "テスト1", "飲食料品", 100, 108, null);
        Product productForTest2 = new Product(2, "テスト2", "飲食料品", 200, 216, null);
        Product productForTest3 = new Product(3, "テスト3", "酒類", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(productForTest1);
        allProductList.add(productForTest2);
        allProductList.add(productForTest3);

        String[] requestProductID = { "1", null, "3" };

        ProductDataCollector.setAllProductList(allProductList);
        List<Product> result = ProductDataCollector.collectProductData(requestProductID);
    }

    /**
     * リクエストしている商品のIDを格納するString配列がnullのとき,
     * ProductDataCollector単体ではNullPointerExceptionが発生する. (結合後は基本的に発生しないケース)
     *
     * @param allProductList     全商品データのリスト
     * @param requestedProductID 利用者が選択した商品のID
     */
    @Test(expected = NullPointerException.class)
    public void requestProductIdArrayIsNull() {
        Product productForTest1 = new Product(1, "テスト1", "飲食料品", 100, 108, null);
        Product productForTest2 = new Product(2, "テスト2", "飲食料品", 200, 216, null);
        Product productForTest3 = new Product(3, "テスト3", "酒類", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(productForTest1);
        allProductList.add(productForTest2);
        allProductList.add(productForTest3);

        String[] requestProductID = null;

        ProductDataCollector.setAllProductList(allProductList);
        List<Product> result = ProductDataCollector.collectProductData(requestProductID);
    }

    /**
     * リクエストしている商品のIDが重複している場合, 対応する商品が指定した順番で入ったList<Product>が返却される.
     * (重複している分も,そのままListに格納して返却される.)
     *
     * @param allProductList     全商品データのリスト
     * @param requestedProductID 利用者が選択した商品のID
     */
    @Test
    public void requestDuplicatedProductId() {
        Product productForTest1 = new Product(1, "テスト1", "飲食料品", 100, 108, null);
        Product productForTest2 = new Product(2, "テスト2", "飲食料品", 200, 216, null);
        Product productForTest3 = new Product(3, "テスト3", "酒類", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(productForTest1);
        allProductList.add(productForTest2);
        allProductList.add(productForTest3);

        String[] requestProductID = { "1", "3", "3" };

        ProductDataCollector.setAllProductList(allProductList);
        List<Product> result = ProductDataCollector.collectProductData(requestProductID);
        assertThat(result, is(contains(productForTest1, productForTest3, productForTest3)));
        assertThat(result.size(), is(3));
    }

    /**
     * 全商品データのリストが空のとき,商品IDを指定していても照合は行われず空のリストを返却する.
     *
     * @param emptyListForTest   全商品データのリスト(このテストケースでは空のリストを用意する)
     * @param requestedProductID 利用者が選択した商品のID
     */
    @Test
    public void productListIsEmpty() {
        Product productForTest1 = new Product(1, "テスト1", "飲食料品", 100, 108, null);
        Product productForTest2 = new Product(2, "テスト2", "飲食料品", 200, 216, null);
        Product productForTest3 = new Product(3, "テスト3", "酒類", 300, 330, null);

        List<Product> emptyListForTest = Collections.emptyList();

        String[] requestProductID = { "3", "1", "2" };

        ProductDataCollector.setAllProductList(emptyListForTest);
        List<Product> result = ProductDataCollector.collectProductData(requestProductID);

        assertThat(result, is(not(contains(productForTest3, productForTest1, productForTest2))));
        assertThat(result, is(empty()));
    }

}
