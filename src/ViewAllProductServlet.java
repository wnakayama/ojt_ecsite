package ojt_ecsite;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import readCSV_practice.CsvParser;
import readCSV_practice.IrregularColumnsException;

/**
 * 商品の一覧表示を行うためのサーブレット. getリクエストを受け取り,
 * 指定したCSVファイル内の全商品データを価格が安い順にソートしたJSON形式で返却する.
 *
 * @author nakayama
 *
 */
@WebServlet("/ViewAllProductServlet")
public class ViewAllProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        CsvParser csvParser = new CsvParser();
        String[][] sample2dArray = null;
        List<Product> productList = new ArrayList<>();
        try {
            sample2dArray = csvParser.convertTo2dArray("c:\\csvTest\\ecData.csv");

            // 商品データを1レコードずつProductオブジェクトとして初期化する
            for (int i = 0; i < sample2dArray.length; i++) {
                int productID = Integer.parseInt(sample2dArray[i][0]);
                String name = sample2dArray[i][1];
                String category = sample2dArray[i][2];
                int priceWithoutTax = Integer.parseInt(sample2dArray[i][3]);

                TaxCalculator taxCalculator = new TaxCalculator();
                int priceIncludeTax = taxCalculator.calculatePriceIncludeTax(category, priceWithoutTax);

                Product product = new Product(productID, name, category, priceWithoutTax, priceIncludeTax);
                productList.add(product);
            }
            // Productオブジェクトのコレクションを税込み価格が安い順にソートする
            Collections.sort(productList, new PriceComparator());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IrregularColumnsException e) {
            e.printStackTrace();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("CSVファイルの列が商品データの仕様と合致していません " + "ヘッダー無しで[商品ID(整数),商品名,商品カテゴリ,税抜き価格(整数)]を設定してください");
        }

        try {
            // 1ページに表示する商品データの範囲をsubListメソッドで絞り込む
            // ここでは機能仕様書に沿って1件目から12件目までを表示させる
            int fromIndex = 0; // subListの下端点(これを含む)
            int toIndex = 12; // subListの上端点(これを含まない)

            if (fromIndex < 0 || toIndex > productList.size()) {
                // 指定するインデックス値が商品リストの範囲外である場合, 下端を0, 上端をリストのsizeに指定する
                fromIndex = 0;
                toIndex = productList.size();
            }
            List<Product> subList = productList.subList(fromIndex, toIndex);

            // CSVファイルから読み込んで安い順にソートした商品データをJSON文字列に変換してクライアントに返却する
            ObjectMapper mapper = new ObjectMapper();
            String responseJSON = mapper.writeValueAsString(subList);

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "nocache");
            response.setCharacterEncoding("utf-8");

            PrintWriter out = response.getWriter();
            out.print(responseJSON);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
