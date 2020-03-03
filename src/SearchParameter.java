package ojt_ecsite;

public class SearchParameter {
    private final String keyword;
    private final int minPrice;
    private final int maxPrice;

    public String getKeyword() {
        return keyword;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    SearchParameter(String keyword, int minPrice, int maxPrice) {
        this.keyword = keyword;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

}
