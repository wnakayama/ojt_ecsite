
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
                var k = 0;
                for (var i = 0; i < rows; i++) {
                    $('.productInfoTable').append('<tr class ="productInfoRow' + i + '"></tr>');
                    for (; k < (product.length); k++) {
                        $('.productInfoRow' + i + '').append('<td class="product' + k + '"></td>');
                        $('.product' + k + '').append('<img src ="' + separateWithComma(product[k].imagePath) + '">');
                        $('.product' + k + '').append('<h4 class="productName" data-text="' + product[k].name + '">' + omitLongProductName(product[k].name) + '</h4>');
                        $('.product' + k + '').append(separateWithComma(product[k].priceIncludeTax) + '円 ');
                        $('.product' + k + '').append('(' + separateWithComma(product[k].priceExcludeTax) + '円) <br>');
                        $('.product' + k + '').append('<input type="checkbox" class="checkbox" value="' + product[k].name + '">選択' + '<br>');
                        if ((k + 1) % cols === 0) {
                            // 設定した列数まで商品データを追加したら,子のfor文を一旦抜けて次の行(tr)を追加する
                            k++
                            break;
                        }
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
            $('.message').replaceWith('<h4 class="message">' + '購入に失敗しました(商品が選択されていません)');
        } else {
            $('.message').replaceWith('<h4 class="message">' + checked.join(''));
        }
    }
);
