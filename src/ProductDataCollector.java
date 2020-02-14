package ojt_ecsite;

import java.util.ArrayList;
import java.util.List;

/**
 * リクエストのあった商品IDを手掛かりに,利用者が購入したがっている商品は何か,全商品データから探し出すクラス.
 *
 * @author nakayama
 *
 */

public class ProductDataCollector {
    private static List<Product> allProductList;

    ProductDataCollector(List<Product> allProductList) {
        ProductDataCollector.allProductList = allProductList;
    }

    /**
     * String配列に入っている商品IDを手掛かりに,利用者が購入したがっている商品は何か,全商品データから探し出す.
     *
     * @param checkValues クライアント側で選択された商品のID
     * @return purchasedProductList 全商品データと照合が取れた選択済み商品のリスト
     */
    public static List<Product> collectProductData(String[] checkValues) {
        List<Product> purchasedProductList = new ArrayList<>();
        for (int i = 0; i < checkValues.length; i++) {
            int requestedID = Integer.parseInt(checkValues[i]);

            for (int j = 0; j < allProductList.size(); j++) {
                int collationID = allProductList.get(j).getProductID();

                if (requestedID == collationID) {
                    purchasedProductList.add(allProductList.get(j));
                }
            }
        }
        return purchasedProductList;
    }
}
