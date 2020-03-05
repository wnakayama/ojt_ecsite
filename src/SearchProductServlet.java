package ojt_ecsite;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 商品の検索を行うためのサーブレット. 検索条件である「商品名」「下限価格」「上限価格」の入力を含んだgetリクエストを受け取り,
 * 条件に該当する商品のデータをJSON形式で返却する.
 *
 * @author nakayama
 *
 */
@WebServlet("/SearchProductServlet")
public class SearchProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // セッションオブジェクトから全商品のデータを受け取る
        // 検索を実施するSearchEngineクラスのstaticフィールドに格納する
        HttpSession session = request.getSession();
        SearchEngine.setAllProductList((List<Product>) session.getAttribute("ALLPRODUCT"));

        // クライアントからの入力値を取得する
        Map<String, String[]> inputParameterMap = request.getParameterMap();

        try {
            SearchResultMaker searchResultMaker = new SearchResultMaker();
            String searchResultJson = searchResultMaker.makeSearchResult(inputParameterMap);

            // 検索結果をJSON形式の文字列に変換
            ObjectMapper mapper = new ObjectMapper();
            String responseJSON = mapper.writeValueAsString(searchResultJson);

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "nocache");
            response.setCharacterEncoding("utf-8");

            PrintWriter out = response.getWriter();
            out.print(responseJSON);

        } catch (IOException e) {
            // javaオブジェクトからJSON文字列への変換に失敗したとき,getWriterでボディメッセージ出力に失敗したときに発生.
            // 基本的に発生しないため,スタックトレースの出力のみで簡易的に例外処理を実装
            e.printStackTrace();
        } catch (NullPointerException e) {
            // 検索条件の指定にnullが含まれていたときに発生.
            // システム利用者の入力が無いとき,getParameterMapの返り値は空文字列の入ったMapとなるため,
            // 基本的に発生しない例外と判断し,スタックトレースの出力のみで簡易的に例外処理を実装
            e.printStackTrace();
        }
    }

}
