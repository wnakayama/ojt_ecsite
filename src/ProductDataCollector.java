package ojt_ecsite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * リクエストのあった商品IDを手掛かりに,利用者が購入したがっている商品は何か,全商品データから探し出すクラス.
 *
 * @author nakayama
 *
 */

public class ProductDataCollector {
    static List<Product> allProductList;

    /**
     * String配列に入っている商品IDを手掛かりに,利用者が購入したがっている商品は何か,全商品データから探し出す.
     *
     * @param checkValues クライアント側で選択された商品のID
     * @return purchasedProductList 全商品データと照合が取れた選択済み商品のリスト
     */
    public static List<Product> collectProductData(String[] checkValues) {

        Map<Integer, Product> allProductMap = new HashMap<>();
        allProductMap = allProductList.stream().collect(
                Collectors.toMap(allProductList -> allProductList.getProductID(), allProductList -> allProductList));

        List<Product> purchasedProductList = new ArrayList<>();
        for (int i = 0; i < checkValues.length; i++) {
            int requestedID = Integer.parseInt(checkValues[i]);
            purchasedProductList.add(allProductMap.get(requestedID));
        }
        return purchasedProductList;
    }
}
