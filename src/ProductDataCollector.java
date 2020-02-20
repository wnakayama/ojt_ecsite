package ojt_ecsite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * リクエストのあった商品IDを手掛かりに,利用者が購入したがっている商品は何か,全商品データから探し出すクラス.
 *
 * @author nakayama
 *
 */

public class ProductDataCollector {
    static List<Product> allProductList;

    static void setAllProductList(List<Product> allProductList) {
        ProductDataCollector.allProductList = allProductList;
    }

    /**
     * String配列に入っている商品IDを手掛かりに,利用者が購入したがっている商品は何か,全商品データから探し出す.
     *
     * @param selectedIdArray クライアント側で選択された商品のID
     * @return purchasedProductList 全商品データと照合が取れた選択済み商品のリスト
     */
    public static List<Product> collectProductData(String[] selectedIdArray) {

        // リクエストに対応する商品を探す処理を効率化するため,staticフィールドにセットされているListをMapに変換
        Map<Integer, Product> allProductMap = new HashMap<>();
        allProductList.forEach(product -> allProductMap.put(product.getProductID(), product));

        // リクエストに対応する商品を探し出し,リストに格納してから返却する.
        List<Product> purchasedProductList = new ArrayList<>();
        for (String selectedId : selectedIdArray) {
            Product getResult = allProductMap.get(Integer.parseInt(selectedId));
            if (getResult != null) {
                purchasedProductList.add(getResult);
            }
        }
        return purchasedProductList;
    }
}
