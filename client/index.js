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
                // (途中)<tr></tr>で新しい列を作成　4件ごとに改行するイメージで商品データを並べたい
                for (var i = 0, len = product.length; i < len; i++) {
                    if (i % 4 == 0) {
                        $('.productInfoTable').append('<tr>');
                    }
                    $('.productInfoTable').append('<td class="product' + i + '"></td>');
                    $('.product' + i + '').append('<li data-text="' + product[i].name + '"><h4>' + omitLongProductName(product[i].name) + '</h4>');
                    $('.product' + i + '').append(separateWithComma(product[i].priceIncludeTax) + '円 ');
                    $('.product' + i + '').append('(' + separateWithComma(product[i].priceExcludeTax) + '円) <br>');
                    $('.product' + i + '').append('<input type="checkbox" name="choose" value="1">選択' + '<br></li>');

                    if (i % 4 == 0) {
                        $('.productInfoTable').append('<tr>');
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

// マウスオーバー商品名の吹き出し
// $(".product li").on({
// 	'mouseenter': function () {
// 		var text = $(this).attr('data-text');
// 		$(this).append('<div class=#product-tooltips">' + text + '</div>');
// 	},
// 	'mouseleave': function () {
// 		$(this).find("#product-tooltips").remove();
// 	}
// });