package ojt_ecsite;

/**
 * [OJT - 最終課題] 税込み価格を算出するクラス.
 *
 * @author nakayama
 *
 */
public class TaxCalculator {

    private static final double REDUCED_TAXRATE = 0.08; // 軽減税率
    private static final double STANDARD_TAXRATE = 0.10; // 標準税率

    /**
     * 商品カテゴリに応じて異なる税率を適用し,商品の税込み価格を算出する.
     *
     * @param category        商品カテゴリ
     * @param priceExcludeTax 税抜き価格
     * @return 税込み価格
     */
    public int calculatePriceIncludeTax(String category, int priceExcludeTax) {
        switch (category) {
        case "飲食料品":
            // 商品カテゴリが飲食料品のとき、軽減税率を適用する
            // 税込み価格 = 税抜き価格 * 1.08 小数点以下は切り捨て
            return (int) (priceExcludeTax + priceExcludeTax * REDUCED_TAXRATE);
        default:
            return (int) (priceExcludeTax + priceExcludeTax * STANDARD_TAXRATE);
        }
    }

}
