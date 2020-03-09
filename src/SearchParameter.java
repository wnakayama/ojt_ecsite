package ojt_ecsite;

/**
 * ユーザーが入力した検索条件を格納するデータクラス.
 *
 * @author nakayama
 *
 */
public class SearchParameter {
    private final String productName;
    private final int minPrice;
    private final int maxPrice;

    public String getProductName() {
        return productName;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    SearchParameter(String productName, int minPrice, int maxPrice) {
        this.productName = productName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

}
