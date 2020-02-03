
// index.htmlのDOMツリーが構築された後にsendgetメソッドを実行
window.addEventListener('DOMContentLoaded', sendget());

/**
 * 商品一覧を表示するスクリプト
 */
function sendget() {
    $.get('http://10.63.8.160:8080/ojt_ecsite/ViewAllProductServlet')
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
                        $('.product' + j + '').append('<img src ="' + separateWithComma(product[j].imagePath) + '">');
                        $('.product' + j + '').append('<h4 class="productName" data-text="' + product[j].name + '">' + omitLongProductName(product[j].name) + '</h4>');
                        $('.product' + j + '').append(separateWithComma(product[j].priceIncludeTax) + '円 ');
                        $('.product' + j + '').append('(' + separateWithComma(product[j].priceExcludeTax) + '円) <br>');
                        $('.product' + j + '').append('<input type="checkbox" class="checkbox" value="' + product[j].name + '">選択' + '<br>');
                        j++
                    }
                }
            }

        }).fail(function (error) {
            // 通信エラーの場合はこちらが実行され、errorに返ってきた詳細が入る
            console.log(error);
        });
}

// "一の位から3桁ずつ,カンマ区切り"で商品価格を表示する
function separateWithComma(price) {
    return String(price).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');
}

// 商品名が30文字を超えている場合, 31文字目から省略する
function omitLongProductName(name) {
    return name.length > 30 ? (name).slice(0, 30) + "…" : name;
}

// 省略されている商品名をマウスオーバーすると,全文を表示する.
$(document).on(
    'mouseover', '.productName', function () {
        var datatext = $(this).attr('data-text');
        $(this).attr('title', datatext);
    }
);

// チェックボックスで選択されている商品がある場合,メッセージ領域に選択した商品を表示
// 何も選択していない場合,エラーメッセージを出力 (画面仕様評価の為,ユースケース02に先行して簡易的に実装.)
$(document).on(
    'click', '.buy', function () {
        var checked = $('.checkbox:checked').map(function () {
            return $(this).val();
        }).get();
        console.log(checked);
        if (checked.length === 0) {
            $('.message').replaceWith('<h4 class="message">' + '購入に失敗しました(商品が選択されていません)');
        } else {
            $('.message').replaceWith('<h4 class="message">' + checked.join(''));
        }
    }
);
