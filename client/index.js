
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
                displayProductData(product);
            }

        }).fail(function (error) {
            // 通信エラーの場合はこちらが実行され、errorに返ってきた詳細が入る
            console.log(error);
        });
}


/**
 * サーバから取得した商品データを一覧表示する.
 */
function displayProductData(product) {
    $('.productInfoArea').append('<table class="productInfoTable"></table>');
    const cols = 4; // 列数を設定
    // 行数を設定. (取得した商品データの件数 ÷ 列数)の小数点以下を切り上げた値
    const rows = Math.ceil(product.length / cols);

    for (var tmpRow = 1; tmpRow <= rows; tmpRow++) {
        $('.productInfoTable').append('<tr class ="productInfoRow' + tmpRow + '"></tr>');
        for (var tmpcolumn = 1; tmpcolumn <= cols; tmpcolumn++) {
            // 出力する商品データのインデックスを設定. ※列数,行数と異なり0番目からカウントする.
            var tmpProductIndex = (tmpRow - 1) * cols + (tmpcolumn - 1);
            if (tmpProductIndex < product.length) {
                $('.productInfoRow' + tmpRow + '').append('<td class="product' + tmpProductIndex + '"></td>');
                $('.product' + tmpProductIndex + '').append('<img src ="' + product[tmpProductIndex].imagePath + '">');
                $('.product' + tmpProductIndex + '').append('<h4 class="productName" data-text="' + product[tmpProductIndex].name + '">' + omitLongProductName(product[tmpProductIndex].name) + '</h4>');
                $('.product' + tmpProductIndex + '').append(separateWithComma(product[tmpProductIndex].priceIncludeTax) + '円 ');
                $('.product' + tmpProductIndex + '').append('(' + separateWithComma(product[tmpProductIndex].priceExcludeTax) + '円) <br>');
                $('.product' + tmpProductIndex + '').append('<label><input type="checkbox" class="checkbox" name="requestedProductID[]" value="' + product[tmpProductIndex].productID + '">選択' + '</label><br>');
            }
        }
    }
}


// "一の位から3桁ずつ,カンマ区切り"で商品価格を表示する
function separateWithComma(price) {
    price = price || 0;
    return String(price).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');
}

// 商品名が30文字を超えている場合, 31文字目から省略する
function omitLongProductName(name) {
    name = name || "";
    return name.length > 30 ? (name).slice(0, 30) + "…" : name;
}

// 省略されている商品名をマウスオーバーすると,全文を表示する.
$(document).on(
    'mouseover', '.productName', function () {
        var datatext = $(this).attr('data-text');
        $(this).attr('title', datatext);
    }
);

// 「購入へ進む」ボタン押下時の処理を分岐する.
// チェックボックスを押下して選んだ商品がある場合,選んだ商品のIDを購入処理(sendSelectedId)の引数に渡す.
// 何も選択していない場合,エラーメッセージを出力する.
$(document).on(
    'click', '.buy', function () {
        var selectedIdArray = $('.checkbox:checked').map(function () {
            return $(this).val();
        }).get();
        if (selectedIdArray.length === 0) {
            $('.message').text('購入に失敗しました(商品が選択されていません)');
        } else {
            sendSelectedIdArray(selectedIdArray);
        }
    }
);

/**
 * 購入処理
 * 利用者がチェックボックスを押下して選んだ商品のIDをPOST送信する.
 * サーバ側から購入明細の返却を受け取り,購入明細画面に遷移する.
 */
function sendSelectedIdArray(selectedIdArray) {
    $.post('http://' + location.host + '/ojt_ecsite/BuyProductServlet',
        {
            "requestedProductID[]": selectedIdArray
        }
    ).done(function (data) {
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


// 「検索」ボタン押下時,フォーム入力値の取得,入力チェック,検索機能の呼び出しをする.
$(document).on(
    'click', '.search', function () {
        const productName = $('input[name="productName"]').val();
        const minPrice = convertToHalfWidthNumber($('input[name="minPrice"]').val());
        const maxPrice = convertToHalfWidthNumber($('input[name="maxPrice"]').val());
        if (validateSearchParameter(productName, minPrice, maxPrice)) {
            sendSearchParameter(productName, minPrice, maxPrice)
        }
    }
);

// 全角で入力された数字を半角数字に変換する
function convertToHalfWidthNumber(inputPrice) {
    return inputPrice.replace(/[０-９]/g, function (s) {
        return String.fromCharCode(s.charCodeAt(0) - 0xFEE0);
    });
}


// いずれかの検索フォームに入力があるのみ,検索ボタンを押下可能にする.
$(document).on(
    'keyup', 'input', function () {
        if ($('input[name="productName"]').val() != "" ||
            $('input[name="minPrice"]').val() != "" ||
            $('input[name="maxPrice"]').val() != "") {

            $('.search').prop('disabled', false);
            $('.search').css('color', '#ffffff');
            $('.search').css('background-color', 'blue');

        } else {
            // すべての検索フォームに入力が無いとき,検索ボタンを無効化
            $('.search').prop('disabled', true);
            $('.search').css('color', '');
            $('.search').css('background-color', '');
        }
    }
);


/**
 * Enumで入力エラーを宣言する
 */
const VALIDATION_ERROR = ({
    ALL_INPUT_EMPTY: {
        message: '●すべてのフォームに入力がありませんでした 条件を指定してから検索を実行してください'
    },
    NOT_UNSIGNED_INTEGER: {
        message: '●検索フォーム(価格)の入力が正しくありません(整数で価格を指定してください)'
    },
    REVERSED_PRICE_RANGE: {
        message: '●価格の範囲指定が正しくありません(価格フォームの左側に下限値,右側に上限値を入力してください)'
    },
    CONTAINS_QUOTATION: {
        message: '●シングルクォーテーション(\')またはダブルクォーテーション(\")が含まれる文字列では検索できません'
    }
})


/**
 * クライアント側で実施する入力チェック
 */
function validateSearchParameter(productName, minPrice, maxPrice) {
    if (productName == "" && minPrice == "" && maxPrice == "") {
        $('.message').text(VALIDATION_ERROR.ALL_INPUT_EMPTY.message);
        return false;
    }
    if (productName.includes("\'") || productName.includes("\"")) {
        $('.message').text(VALIDATION_ERROR.CONTAINS_QUOTATION.message);
        return false;
    }
    if (minPrice != "") {
        if (!minPrice.match(/^([1-9]\d*|0)$/)) {
            $('.message').text(VALIDATION_ERROR.NOT_UNSIGNED_INTEGER.message);
            return false;
        }
    }
    if (maxPrice != "") {
        if (!maxPrice.match(/^([1-9]\d*|0)$/)) {
            $('.message').text(VALIDATION_ERROR.NOT_UNSIGNED_INTEGER.message);
            return false;
        }
    }
    if (minPrice != "" && maxPrice != "") {
        if (parseInt(minPrice) > parseInt(maxPrice)) {
            $('.message').text(VALIDATION_ERROR.REVERSED_PRICE_RANGE.message);
            return false;
        }
    }
    return true;
}




/**
 * 検索処理
 * 利用者がフォームに入力した検索条件をGETで送信する.
 * サーバ側から検索結果のJSONを受け取り,商品一覧画面の表示を動的に変更する.
 */
function sendSearchParameter(productName, minPrice, maxPrice) {
    $.get('http://' + location.host + '/ojt_ecsite/SearchProductServlet',
        {
            "productName": productName,
            "minPrice": minPrice,
            "maxPrice": maxPrice
        }
    ).done(function (data) {
        // 通信成功時
        try {
            const product = JSON.parse(data);
            if (product.length == 0) {
                //入力が正常で,該当商品がない場合
                $('.productInfoArea').empty();
                $('.message').empty();
                $('.productInfoArea').append('該当する商品は見つかりませんでした<br>');
            } else {
                if (!product.hasOwnProperty('errorMessage')) {
                    // 入力が正常で,該当商品がある場合 商品情報領域を一度クリアして,新しく検索結果を表示する.
                    $('.productInfoArea').empty();
                    $('.message').empty();
                    displayProductData(product);
                } else {
                    // 不正入力があったとき
                    $('.message').text(product.errorMessage);
                }
            }
        } catch (SyntaxError) {
            $('.message').text('商品データの取得に失敗しました お手数ですが開発者までご連絡ください');
        }
    }).fail(function (error) {
        // 通信エラーの場合はこちらが実行され、errorに返ってきた詳細が入る
        console.log(error);
    });
}

// 全商品一覧へ戻るボタンを押下すると,商品一覧画面に戻る
$(document).on(
    'click', '.goBack', function () {
        window.location.href = 'index.html';
    }
);
