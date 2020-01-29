package ojt_ecsite;

import java.util.Comparator;

/**
 * 2つの商品の税込み価格を比較するクラス.
 *
 * @author nakayama
 */
public class PriceComparator implements Comparator<Product> {
    /**
     * @param p1 一つ目の商品
     * @param p2 二つ目の商品
     *
     * @return 一つ目の商品の税込み価格が二つ目の商品と比べて高い場合は1,同じ場合は0,安い場合は-1を返却する.
     */
    @Override
    public int compare(Product p1, Product p2) {
        Integer price1 = p1.getPriceIncludeTax();
        Integer price2 = p2.getPriceIncludeTax();
        return price1.compareTo(price2);
    }

}
