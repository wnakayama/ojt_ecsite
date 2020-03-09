package ojt_ecsite;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * ユーザーが入力した検索条件に該当する商品データを,全商品データから探し出すクラスのテスト.
 *
 * @author nakayama
 *
 */
public class SearchEngineTest {

    /**
     * 商品名,下限価格,上限価格を指定して,正常に商品が検索されることを確認する.
     * 
     * @param testparam 検索条件のオブジェクト.商品名,下限価格,上限価格をフィールドに持つ.
     * @return result 指定された検索条件に該当する商品のリスト
     */
    @Test
    public void nameMinPriceMaxPrice() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter("AP", 215, 216);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(contains(product2)));
    }

    /**
     * 商品名,下限価格を指定して,正常に商品が検索されることを確認する.
     */
    @Test
    public void nameMinPrice() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter("AP", 109, 0);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(contains(product2)));
    }

    /**
     * 商品名,上限価格を指定して,正常に商品が検索されることを確認する.
     */
    @Test
    public void nameMaxPrice() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter("AP", 0, 108);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(contains(product1)));
    }

    /**
     * 下限価格,上限価格を指定して,正常に商品が検索されることを確認する.
     */
    @Test
    public void MinPriceMaxPrice() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter(null, 216, 330);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(contains(product2, product3)));
    }

    /**
     * 下限価格を指定して検索するとき,"税込み価格"が指定した下限価格以上である商品のみ検索結果のリストに格納されることを確認する.
     */
    @Test
    public void checkMinPriceBorder() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter(null, 217, 0);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(contains(product3)));
    }

    /**
     * 上限価格を指定して検索するとき,"税込み価格"が指定した上限価格以下である商品のみ検索結果のリストに格納されることを確認する.
     */
    @Test
    public void checkMaxPriceBorder() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter(null, 0, 216);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(contains(product1, product2)));
    }

    /**
     * 指定した商品名にアルファベットが含まれるとき,大文字と小文字の区別を付けずに検索を実施する.
     * (小文字を指定しているとき,大文字で同じアルファベットを含む商品も検索結果に入れる)
     */
    @Test
    public void lowerCase() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter("ap", 0, 0);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(contains(product1, product2)));
    }

    /**
     * 指定した商品名にアルファベットが含まれるとき,大文字と小文字の区別を付けずに検索を実施する.
     * (大文字を指定しているとき,小文字で同じアルファベットを含む商品も検索結果に入れる)
     */
    @Test
    public void upperCase() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter("AP", 0, 0);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(contains(product1, product2)));
    }

    /**
     * 全商品データに存在しない商品名を指定したとき,検索結果のリストは空である.
     */
    @Test
    public void nameNotFound() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter("存在しない商品名", 0, 0);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(empty()));
    }

    /**
     * 全商品データに該当商品が存在しない下限価格を指定したとき,検索結果のリストは空である.
     */
    @Test
    public void minPriceNotFound() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter(null, 999, 0);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(empty()));
    }

    /**
     * 全商品データに該当商品が存在しない上限価格を指定したとき,検索結果のリストは空である.
     */
    @Test
    public void maxPriceNotFound() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter(null, 0, 1);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(empty()));
    }

    /**
     * 商品名,下限価格,上限価格がいずれも指定されていないとき,検索は実行されず
     * SearchEngineクラス単体では全商品データをそのまま検索結果として返す.
     */
    @Test
    public void allSearchParameterUnspecified() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter(null, 0, 0);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(allProductList));
    }

    /**
     * 指定した下限価格が上限価格を上回っているとき,検索結果のリストは空である.
     */
    @Test
    public void priceRangeReversed() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter(null, 217, 216);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(empty()));
    }

}
