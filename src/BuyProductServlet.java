package ojt_ecsite;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 商品の購入処理を行うためのサーブレット. postリクエストを受け取り,「購入日時」「購入した商品のデータ」「税抜き合計金額」「税抜き合計金額」を
 * 記載した購入明細をJSON形式で返却する.
 *
 * @author nakayama
 *
 */
@WebServlet("/BuyProductServlet")
public class BuyProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        // セッションオブジェクトから全商品のデータを受け取る
        // リクエストされた商品を全商品のデータから探し出すProductDataCollectorのstaticフィールドに格納する
        HttpSession session = request.getSession();
        ProductDataCollector.setAllProductList((List<Product>) session.getAttribute("ALLPRODUCT"));

        // リクエストのあった商品IDを取得する
        String[] selectedIdArray = request.getParameterValues("requestedProductID[]");

        // 購入明細を作る
        ReceiptMaker receiptMaker = new ReceiptMaker();
        Receipt receipt = receiptMaker.makeReceipt(selectedIdArray);

        try {
            // 購入明細のオブジェクトをJSON形式の文字列に変換
            ObjectMapper mapper = new ObjectMapper();
            String responseJSON = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(receipt);

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "nocache");
            response.setCharacterEncoding("utf-8");

            PrintWriter out = response.getWriter();
            out.print(responseJSON);

        } catch (IOException e) {
            // javaオブジェクトからJSON文字列への変換に失敗したとき,getWriterでボディメッセージ出力に失敗したときに発生.
            // 基本的に発生しないため,スタックトレースの出力のみで簡易的に例外処理を実装
            e.printStackTrace();
        } catch (NullPointerException | NumberFormatException e) {
            // クライアントから購入リクエストのあった商品のIDを格納するString配列にnullが含まれているとき発生.
            // 基本的に発生しないため,スタックトレースの出力のみで簡易的に例外処理を実装
            e.printStackTrace();
        }
    }

}
