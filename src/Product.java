package ojt_ecsite;

/**
 * [OJT-最終課題] 商品データクラス
 *
 * @author nakayama
 *
 */
public class Product {

    private final int productID;
    private final String name;
    private final String category;
    private final int priceExcludeTax;
    private final int priceIncludeTax;
    private final String imagePath;

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPriceExcludeTax() {
        return priceExcludeTax;
    }

    public int getPriceIncludeTax() {
        return priceIncludeTax;
    }

    public String getImagePath() {
        return imagePath;
    }

    Product(int productID, String name, String category, int priceExcludeTax, int priceIncludeTax, String imagePath) {
        this.productID = productID;
        this.name = name;
        this.category = category;
        this.priceExcludeTax = priceExcludeTax;
        this.priceIncludeTax = priceIncludeTax;
        this.imagePath = imagePath;
    }
}
