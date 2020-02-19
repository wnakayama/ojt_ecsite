package ojt_ecsite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 購入明細を生成するクラス.
 *
 * @author nakayama
 *
 */
public class ReceiptMaker {
    /**
     * 購入日時,選択された商品のデータ,税抜き合計金額,税込み合計金額を取得して, それらをセットした購入明細オブジェクトを返却する.
     *
     * @param selectedIdArray クライアント側で選択された商品のID
     * @return receipt 購入明細
     */
    public Receipt makeReceipt(String[] selectedIdArray) {

        // 購入日時を取得する
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String purchasedDateTime = simpleDateFormat.format(calendar.getTime());

        // 選択された商品のデータを取得する
        List<Product> purchasedProductList = new ArrayList<>();
        purchasedProductList = ProductDataCollector.collectProductData(selectedIdArray);

        // 税抜き合計金額を算出する
        TotalPriceCalculator totalCalc = new TotalPriceCalculator();
        int totalPriceExcludeTax = totalCalc.calculateTotalPriceExcludeTax(purchasedProductList);

        // 税込み合計金額を算出する
        int totalPriceIncludeTax = totalCalc.calculateTotalPriceIncludeTax(purchasedProductList);

        // 購入日時,選択された商品の商品情報,税抜き合計金額,税込み合計金額を購入明細にセットして返却する
        Receipt receipt = new Receipt(purchasedDateTime, purchasedProductList, totalPriceExcludeTax,
                totalPriceIncludeTax);
        return receipt;
    }
}