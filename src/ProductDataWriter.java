package ojt_ecsite;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ProductDataWriter {
    private static final String CSVPATH = "c:\\csvTest\\registerTest.csv";
    // OSにより使用しているものが異なる改行コードをgetPropertyメソッドで用意
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public void WriteProductData(Product product) throws IOException {

        // try-with-resource
        try (
                // FileOutputStreamクラスのオブジェクトを生成する // true → 追記モード
                FileOutputStream file = new FileOutputStream(CSVPATH, true);
                // PrintWriterクラスのオブジェクトを生成する
                PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(file, "UTF-8")));) {

            // new Product(登録したい商品情報)を、カンマ区切りの一行にする
            StringBuilder newProductDataRow = new StringBuilder();

            newProductDataRow.append(LINE_SEPARATOR);// 改行

            newProductDataRow.append(product.getProductID());
            newProductDataRow.append(",");
            newProductDataRow.append(product.getName());
            newProductDataRow.append(",");
            newProductDataRow.append(product.getCategory());
            newProductDataRow.append(",");
            newProductDataRow.append(product.getPriceExcludeTax());
            newProductDataRow.append(",");
            newProductDataRow.append(product.getPriceIncludeTax());
            newProductDataRow.append(",");
            newProductDataRow.append(product.getImagePath());

            System.out.println(newProductDataRow.toString());

            byte[] newProductDataBytes = newProductDataRow.toString().getBytes(StandardCharsets.UTF_8);
            file.write(newProductDataBytes);
            file.flush();
        }
    }
}
