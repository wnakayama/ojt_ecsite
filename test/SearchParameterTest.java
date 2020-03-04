package ojt_ecsite;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * ユーザーが入力した検索条件を格納するデータクラスのテスト.
 *
 * @author nakayama
 *
 */
public class SearchParameterTest {

    @Test
    public void testGetProductName() {
        SearchParameter param = new SearchParameter("test", 0, 0);
        String result = param.getProductName();
        assertThat(result, is("test"));
    }

    @Test
    public void testGetMinPrice() {
        SearchParameter param = new SearchParameter(null, 123, 0);
        int result = param.getMinPrice();
        assertThat(result, is(123));
    }

    @Test
    public void testGetMaxPrice() {
        SearchParameter param = new SearchParameter(null, 0, 765);
        int result = param.getMaxPrice();
        assertThat(result, is(765));
    }

}
