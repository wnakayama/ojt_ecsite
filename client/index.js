/**
 *
 */

window.addEventListener('DOMContentLoaded', sendget());

function sendget() {
    $.get('http://localhost:8080/ojt_ecsite/ViewAllProductServlet')
        .done(function (data) {
            // 通信成功時

            //レスポンスのJSON文字列をjavaScriptオブジェクトに変換
            const jsonstr = JSON.stringify(data);
            const product = JSON.parse(jsonstr);

            if (product.length == 0) {
                $('.productInfoArea').append('商品データがありません<br>');
            } else {
                $('.productInfoArea').append('<table class="productInfoTable"></table>');
                var j = 0;
                const cols = 4;
                const rows = (product.length) / cols;

                for (var i = 0; i < rows; i++) {

                    $('.productInfoTable').append('<tr class ="test' + i + '">');

                    for (var k = 0; k < cols; k++) {
                        $('.test' + i + '').append('<td class="product' + j + '"></td>');
                        $('.product' + j + '').append('<li data-text="' + product[j].name + '"><h4>' + omitLongProductName(product[j].name) + '</h4>');
                        $('.product' + j + '').append(separateWithComma(product[j].priceIncludeTax) + '円 ');
                        $('.product' + j + '').append('(' + separateWithComma(product[j].priceExcludeTax) + '円) <br>');
                        $('.product' + j + '').append('<input type="checkbox" name="choose" value="1">選択' + '<br></li>');
                        j++
                    }
                }

            }

        }).fail(function (error) {
            // 通信エラーの場合はこちらが実行され、errorに返ってきた詳細が入る
            // 不明なエラーは、エラーコードを参照　検索時のヒントに
            console.log(error);
        });
}

function separateWithComma(price) {
    return String(price).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');
}

function omitLongProductName(name) {
    return name.length > 30 ? (name).slice(0, 30) + "…" : name;
}


$(document).on(
    'mouseover', 'li', function () {

        var datatext = $(this).attr('data-text');
        $(this).attr('title', datatext);
        // $(this).append('<div class=#product-tooltips">' + text + '</div>');
    }
);