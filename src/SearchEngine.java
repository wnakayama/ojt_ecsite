package ojt_ecsite;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ユーザーが入力した検索条件に該当する商品データを,全商品データから探し出すクラス.
 *
 * @author nakayama
 *
 */
public class SearchEngine {
    static List<Product> allProductList; // 全商品データ

    static void setAllProductList(List<Product> allProductList) {
        SearchEngine.allProductList = allProductList;
    }

    /**
     * ユーザー入力で指定された検索条件に基づいて,商品データの絞り込みを実施する.
     *
     * @param searchParameter 検索条件
     * @return foundProductList 指定された検索条件に該当する商品のリスト
     */
    public static List<Product> searchProductData(SearchParameter searchParameter) {

        List<Product> foundProductList = new ArrayList<>();

        // 絞り込みを実施するProduct型のコレクションに全商品データを格納する.
        foundProductList = allProductList.stream().collect(Collectors.toList());

        // 商品名の指定があるとき,指定された商品名と部分一致する商品のみに絞り込む.
        // アルファベットは全て小文字に揃えて検索する.
        if (searchParameter.getProductName() != null && !searchParameter.getProductName().isEmpty()) {
            foundProductList = foundProductList.stream()
                    .filter(p -> p.getName().toLowerCase().contains(searchParameter.getProductName().toLowerCase()))
                    .collect(Collectors.toList());
        }

        // 下限価格の指定があるとき,指定された下限価格以上の商品のみに絞り込む.
        if (searchParameter.getMinPrice() != 0) {
            foundProductList = foundProductList.stream()
                    .filter(p -> searchParameter.getMinPrice() <= p.getPriceIncludeTax()).collect(Collectors.toList());
        }

        // 上限価格の指定があるとき,指定された上限価格以上の商品のみに絞り込む.
        if (searchParameter.getMaxPrice() != 0) {
            foundProductList = foundProductList.stream()
                    .filter(p -> p.getPriceIncludeTax() <= searchParameter.getMaxPrice()).collect(Collectors.toList());
        }
        return foundProductList;
    }

}
