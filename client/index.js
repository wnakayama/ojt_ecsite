
// index.htmlのDOMツリーが構築された後にsendgetメソッドを実行
window.addEventListener('DOMContentLoaded', sendget());

/**
 * 商品一覧を表示するスクリプト
 */
function sendget() {
    $.get('http://10.63.8.160:8080/ojt_ecsite/ViewAllProductServlet')
        .done(function (data) {
            // 通信成功時
            const product = data || {};
            if (product.length == 0) {
                $('.productInfoArea').append('商品データがありません<br>');
            } else {
                $('.productInfoArea').append('<table class="productInfoTable"></table>');
                const cols = 4; // 列数を設定
                const rows = (product.length) / cols;　// 行数 = 取得した商品データの件数 ÷ 列数
                for (var i = 0; i < rows; i++) {
                    $('.productInfoTable').append('<tr class ="productInfoRow' + i + '"></tr>');
                    for (var j = 0; j < cols; j++) {
                        $('.productInfoRow' + i + '').append('<td class="product' + (i * cols + j) + '"></td>');
                        $('.product' + (i * cols + j) + '').append('<img src ="' + separateWithComma(product[(i * cols + j)].imagePath) + '">');
                        $('.product' + (i * cols + j) + '').append('<h4 class="productName" data-text="' + product[(i * cols + j)].name + '">' + omitLongProductName(product[(i * cols + j)].name) + '</h4>');
                        $('.product' + (i * cols + j) + '').append(separateWithComma(product[(i * cols + j)].priceIncludeTax) + '円 ');
                        $('.product' + (i * cols + j) + '').append('(' + separateWithComma(product[(i * cols + j)].priceExcludeTax) + '円) <br>');
                        $('.product' + (i * cols + j) + '').append('<input type="checkbox" class="checkbox" value="' + product[(i * cols + j)].name + '">選択' + '<br>');
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
    price = price || {};
    return String(price).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');
}

// 商品名が30文字を超えている場合, 31文字目から省略する
function omitLongProductName(name) {
    name = name || {};
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
        if (checked.length === 0) {
            $('.message').replaceWith('<h4>' + '購入に失敗しました(商品が選択されていません)</h4>');
        } else {
            $('.message').replaceWith('<h4>' + checked.join('') + '</h4>');
        }
    }
);
