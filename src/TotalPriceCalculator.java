package ojt_ecsite;

import java.util.List;

/**
 * 利用者が選択した商品の合計金額を算出するクラス.
 *
 * @author nakayama
 *
 */
public class TotalPriceCalculator {

    /**
     * 税抜き合計金額を算出する.
     * 
     * @param purchasedProductList 選択された商品のリスト
     * @return totalPriceExcludeTax 税抜き合計金額
     */
    public int calculateTotalPriceExcludeTax(List<Product> purchasedProductList) {

        int totalPriceExcludeTax = 0;
        for (int k = 0; k < purchasedProductList.size(); k++) {
            totalPriceExcludeTax += purchasedProductList.get(k).getPriceExcludeTax();
        }
        return totalPriceExcludeTax;
    }

    /**
     * 税込み合計金額を算出する
     *
     * @param purchasedProductList 選択された商品のリスト
     * @return totalPriceIncludeTax 税込み合計金額
     */
    public int calculateTotalPriceIncludeTax(List<Product> purchasedProductList) {
        int totalPriceIncludeTax = 0;
        for (int k = 0; k < purchasedProductList.size(); k++) {
            totalPriceIncludeTax += purchasedProductList.get(k).getPriceIncludeTax();
        }
        return totalPriceIncludeTax;
    }

}
