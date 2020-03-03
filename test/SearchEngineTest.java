package ojt_ecsite;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SearchEngineTest {

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

    @Test
    public void name() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter("FE", 0, 0);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(contains(product3)));
    }

    @Test
    public void MinPrice() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter(null, 216, 0);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(contains(product2, product3)));
    }

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

    @Test
    public void MaxPrice() {
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

    @Test
    public void checkMaxPriceBorder() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter(null, 0, 215);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(contains(product1)));
    }

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

    @Test
    public void priceRangeReversed() {
        Product product1 = new Product(1, "AP過去問", "テスト用", 100, 108, null);
        Product product2 = new Product(2, "ap過去問", "テスト用", 200, 216, null);
        Product product3 = new Product(3, "FE過去問", "テスト用", 300, 330, null);

        List<Product> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        SearchParameter testparam = new SearchParameter(null, 216, 108);
        SearchEngine.setAllProductList(allProductList);
        List<Product> result = SearchEngine.searchProductData(testparam);

        assertThat(result, is(empty()));
    }

}
