
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


// 「検索」ボタン押下時の処理を分岐する.
$(document).on(
    'click', '.search', function () {
        const productName = $('input[name="productName"]').val();
        const minPrice = $('input[name="minPrice"]').val();
        const maxPrice = $('input[name="maxPrice"]').val();
        if (validateSearchParameter(productName, minPrice, maxPrice)) {
            sendSearchParameter(productName, minPrice, maxPrice)
        }
    }
);


// いずれかの検索フォームに入力があるのみ,検索ボタンを押下可能にする.
$(document).on(
    'change', 'input', function () {

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
    EXCEEDS_CHARACTERS: {
        message: '●商品名は250文字,価格は9文字まで入力を受け付けます 入力を減らして再度お試しください'
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
    const NAME_MAX_LENGTH = 250;
    const PRICE_MAX_DIGIT = 9;

    if (productName == "" && minPrice == "" && maxPrice == "") {
        console.log('全空欄');
        $('.message').text(VALIDATION_ERROR.ALL_INPUT_EMPTY.message);
        return false;
    } else if (productName.length > NAME_MAX_LENGTH || minPrice.length > PRICE_MAX_DIGIT || maxPrice.length > PRICE_MAX_DIGIT) {
        console.log('文字数オーバー');
        $('.message').text(VALIDATION_ERROR.EXCEEDS_CHARACTERS.message);
        return false;
    } else if (!minPrice.match(/^([1-9]\d*|0)$/) || !maxPrice.match(/^([1-9]\d*|0)$/)) {
        console.log('数値じゃない');
        $('.message').text(VALIDATION_ERROR.NOT_UNSIGNED_INTEGER.message);
        return false;
    } else if (minPrice > maxPrice) {
        console.log('下限値>上限値');
        $('.message').text(VALIDATION_ERROR.REVERSED_PRICE_RANGE.message);
        return false;
    } else if (productName.includes("\'") || productName.includes("\"")) {
        console.log('クォーテーションを含む');
        $('.message').text(VALIDATION_ERROR.CONTAINS_QUOTATION.message);
        return false;
    } else {
        return true;
    }
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
        const product = JSON.parse(data) || {};
        if (product.length == 0) {
            //入力が正常で,該当商品がない場合
            $('.productInfoArea').empty();
            $('.message').empty();
            $('.productInfoArea').append('該当する商品は見つかりませんでした<br>');
        } else {
            if (!product.hasOwnProperty('errorMessage')) {
                //入力が正常で,該当商品がある場合 商品情報領域を一度クリアして,新しく検索結果を表示する.
                $('.productInfoArea').empty();
                $('.message').empty();
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
            } else {
                // 不正入力があったとき
                $('.message').text(product.errorMessage);
            }
        }
    }).fail(function (error) {
        // 通信エラーの場合はこちらが実行され、errorに返ってきた詳細が入る
        console.log(error);
    });
}