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
import javax.servlet.http.HttpSession;

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

    private static final String CSVPATH = "c:\\csvTest\\ecData.csv";
    private static final int COLUMN_PRODUCT_ID = 0;
    private static final int COLUMN_NAME = 1;
    private static final int COLUMN_CATEGORY = 2;
    private static final int COLUMN_PRICE_EXCLUDE_TAX = 3;
    private static final int COLUMN_IMAGE_PATH = 4;

    // 1ページに表示する商品データの範囲はsubListメソッドで絞り込む.
    // ここでは機能仕様書に沿って12件目までを表示させるよう設定する.
    private static final int DISPLAY_PRODUCTS_LOWERLIMIT = 0; // subListの下端点(これを含む)
    private static final int DISPLAY_PRODUCTS_UPPERLIMIT = 12; // subListの上端点(これを含まない)

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        List<Product> productList = new ArrayList<>();
        try {
            CsvParser csvParser = new CsvParser();
            String[][] sample2dArray = null;
            sample2dArray = csvParser.convertTo2dArray(CSVPATH);

            // 商品データを1レコードずつProductオブジェクトとして初期化する
            TaxCalculator taxCalculator = new TaxCalculator();
            for (int i = 0; i < sample2dArray.length; i++) {
                int productID = Integer.parseInt(sample2dArray[i][COLUMN_PRODUCT_ID]);
                String name = sample2dArray[i][COLUMN_NAME];
                String category = sample2dArray[i][COLUMN_CATEGORY];
                int priceExcludeTax = Integer.parseInt(sample2dArray[i][COLUMN_PRICE_EXCLUDE_TAX]);
                int priceIncludeTax = taxCalculator.calculatePriceIncludeTax(category, priceExcludeTax);
                String imagePath = "images/" + sample2dArray[i][COLUMN_IMAGE_PATH] + "";

                Product product = new Product(productID, name, category, priceExcludeTax, priceIncludeTax, imagePath);
                productList.add(product);
            }

            // セッションを生成して,そこへ全商品データを格納したproductListを保存する
            // 保存した全商品データは,別のユースケース(購入,検索)で値を変えないまま使用する
            HttpSession session = request.getSession();
            session.setAttribute("ALLPRODUCT", productList);

            // Productオブジェクトのコレクションを税込み価格が安い順にソートする
            Collections.sort(productList, new PriceComparator());

        } catch (IOException e) {
            // CSVファイルの読み込みに失敗した場合と指定したファイルが見つからなかった場合にスローされる例外.
            // 開発者向けに,コンソールで原因を伝えるメッセージが出力される.
            e.printStackTrace();
        } catch (IrregularColumnsException e) {
            // CSVファイルの列数が揃っていないときに自作ライブラリCsvParserがスローする例外.
            // 開発者向けに,コンソールで原因を伝えるメッセージが出力される.
            e.printStackTrace();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("CSVファイルの列が商品データの仕様と合致していません " + "ヘッダー無しで[商品ID(整数),商品名,商品カテゴリ,税抜き価格(整数)]を設定してください");
        }

        try {
            int fromIndex = DISPLAY_PRODUCTS_LOWERLIMIT;
            int toIndex = DISPLAY_PRODUCTS_UPPERLIMIT;

            if (DISPLAY_PRODUCTS_UPPERLIMIT > productList.size()) {
                // 設定した上限が元のListのsizeを超えていたら,元のListのsizeに合わせる
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
            // javaオブジェクトからJSON文字列への変換に失敗したとき,getWriterでボディメッセージ出力に失敗したときに発生.
            // 基本的に発生しないため,スタックトレースの出力のみで簡易的に例外処理を実装
            e.printStackTrace();
        }
    }
}
