package ojt_ecsite;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * ユーザーが入力した検索条件に該当する商品データを,全商品データから探し出すクラス.
 *
 * @author nakayama
 *
 */
public class SearchEngine {
    static List<Product> allProductList;

    static void setAllProductList(List<Product> allProductList) {
        SearchEngine.allProductList = allProductList;
    }

    /**
     * String配列に入っている商品IDを手掛かりに,利用者が購入したがっている商品は何か,全商品データから探し出す.
     *
     * @param searchParameter 検索条件
     * @return foundProductList 全商品データと照合が取れた該当商品のリスト
     */
    public static List<Product> searchProductData(SearchParameter searchParameter) {

        List<Product> foundProductList = new ArrayList<>();

        foundProductList = allProductList.stream().collect(Collectors.toList());

        if (!StringUtils.isEmpty(searchParameter.getKeyword())) {
            foundProductList = foundProductList.stream()
                    .filter(p -> p.getName().toLowerCase().contains(searchParameter.getKeyword().toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (searchParameter.getMinPrice() != 0) {
            foundProductList = foundProductList.stream()
                    .filter(p -> searchParameter.getMinPrice() <= p.getPriceIncludeTax()).collect(Collectors.toList());
        }

        if (searchParameter.getMaxPrice() != 0) {
            foundProductList = foundProductList.stream()
                    .filter(p -> p.getPriceIncludeTax() <= searchParameter.getMaxPrice()).collect(Collectors.toList());
        }
        return foundProductList;
    }

}
