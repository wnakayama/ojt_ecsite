package ojt_ecsite;

import java.util.List;

/**
 * 購入明細のデータクラス.
 *
 * @author nakayama
 *
 */
public class Receipt {

    private final String purchasedDateTime;
    private final List<Product> purchasedProductList;
    private final int TotalPriceExcludeTax;
    private final int TotalPriceIncludeTax;

    public String getPurchasedDateTime() {
        return purchasedDateTime;
    }

    public List<Product> getPurchasedProductList() {
        return purchasedProductList;
    }

    public int getTotalPriceExcludeTax() {
        return TotalPriceExcludeTax;
    }

    public int getTotalPriceIncludeTax() {
        return TotalPriceIncludeTax;
    }

    Receipt(String purchasedDateTime, List<Product> purchasedProductList, int TotalPriceExcludeTax,
            int TotalPriceIncludeTax) {
        this.purchasedDateTime = purchasedDateTime;
        this.purchasedProductList = purchasedProductList;
        this.TotalPriceExcludeTax = TotalPriceExcludeTax;
        this.TotalPriceIncludeTax = TotalPriceIncludeTax;
    }
}
