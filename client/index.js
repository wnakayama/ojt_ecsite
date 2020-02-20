
// index.htmlのDOMツリーが構築された後にviewAllProductメソッドを実行
window.addEventListener('DOMContentLoaded', viewAllProduct());

/**
 * 商品一覧を表示するスクリプト
 */
function viewAllProduct() {
    $.get('http://' + location.host + '/ojt_ecsite/ViewAllProductServlet')
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
                        $('.product' + (i * cols + j) + '').append('<img src ="' + product[(i * cols + j)].imagePath + '">');
                        $('.product' + (i * cols + j) + '').append('<h4 class="productName" data-text="' + product[(i * cols + j)].name + '">' + omitLongProductName(product[(i * cols + j)].name) + '</h4>');
                        $('.product' + (i * cols + j) + '').append(separateWithComma(product[(i * cols + j)].priceIncludeTax) + '円 ');
                        $('.product' + (i * cols + j) + '').append('(' + separateWithComma(product[(i * cols + j)].priceExcludeTax) + '円) <br>');
                        $('.product' + (i * cols + j) + '').append('<label><input type="checkbox" class="checkbox" name="requestedProductID[]" value="' + product[(i * cols + j)].productID + '">選択' + '</label><br>');
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
    name = name || "";
    return name.length > 30 ? (name).slice(0, 30) + "…" : name;
}

// 省略されている商品名をマウスオーバーすると,全文を表示する.
$(document).on(
    'mouseover', '.productName', function displayFullProductName() {
        var datatext = $(this).attr('data-text');
        $(this).attr('title', datatext);
    }
);

// 「購入へ進む」ボタン押下時の処理を分岐する.
// チェックボックスを押下して選んだ商品がある場合,選んだ商品のIDを購入処理(sendSelectedId)の引数に渡す.
// 何も選択していない場合,エラーメッセージを出力する.
$(document).on(
    'click', '.buy', function requestPurchase() {
        var selectedId = $('.checkbox:checked').map(function () {
            return $(this).val();
        }).get();
        if (selectedId.length === 0) {
            $('.message').text('購入に失敗しました(商品が選択されていません)');
        } else {
            sendSelectedId(selectedId);
        }
    }
);

/**
 * 購入処理
 * 利用者がチェックボックスを押下して選んだ商品のIDをPOST送信する.
 * サーバ側から購入明細の返却を受け取り,購入明細画面に遷移する.
 */
function sendSelectedId(selectedId) {
    $.post('http://' + location.host + '/ojt_ecsite/BuyProductServlet',
        {
            "requestedProductID": selectedId
        }
    )
        .done(function (data) {
            // サーバから返ってきた購入明細はSessionStorageに保存して
            // 購入明細を表示するスクリプト(purchase.js)でも使えるようにしておく
            var receipt = JSON.stringify(data);
            sessionStorage.setItem('receipt', receipt);
            window.location.href = 'purchase.html'; // 購入明細画面に遷移
        }).fail(function (error) {
            // 通信エラーの場合はこちらが実行され、errorに返ってきた詳細が入る
            console.log(error);
        });
}
