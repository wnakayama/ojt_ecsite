
// purchase.htmlのDOMツリーが構築された後にshowReceiptメソッドを実行
window.addEventListener('DOMContentLoaded', showReceipt());

/**
 * 購入明細を表示するスクリプト
 */
function showReceipt() {
    // sessionStorageにString文字列で保存していた購入明細を取得し,パースする
    const receipt = JSON.parse(sessionStorage.getItem('receipt')) || {};
    const purchasedProduct = receipt['purchasedProductList'] || {};

    // 購入明細表示領域に,HTML要素を追加して購入明細を表示する.
    $('.receiptArea').append('<h4 class="purchasedDateTime"><u>購入日時 :' + receipt['purchasedDateTime'] + '</u></h4><br>');
    $('.receiptArea').append('<table class="receiptTable"></table>');

    for (var i = 0; i < purchasedProduct.length; i++) {
        $('.receiptTable').append('<tr class ="receiptRow' + i + '"></tr>');

        $('.receiptRow' + i + '').append('<td class="purchasedProductImage' + i + '"></td>');
        $('.purchasedProductImage' + i + '').append('<img src ="' + purchasedProduct[i].imagePath + '">');

        $('.receiptRow' + i + '').append('<td class="purchasedProductInfo' + i + '"></td>');
        $('.purchasedProductInfo' + i + '').append('<h4 class="productName" data-text="' + purchasedProduct[i].name + '">' + purchasedProduct[i].name + '</h4>');
        $('.purchasedProductInfo' + i + '').append('<span class="priceIncludeTax">' + separateWithComma(purchasedProduct[i].priceIncludeTax) + '円 <span>');
        $('.purchasedProductInfo' + i + '').append('<span class="priceExcludeTax">(' + separateWithComma(purchasedProduct[i].priceExcludeTax) + '円)<span>');
    }

    $('.receiptArea').append('<h4 class="totalPrice"><u>合計金額 : ' + separateWithComma(receipt['totalPriceIncludeTax'])
        + '円 (' + separateWithComma(receipt['totalPriceExcludeTax']) + '円 + 税)</u></h4>');
}

// "一の位から3桁ずつ,カンマ区切り"で商品価格を表示する
function separateWithComma(price) {
    price = price || {};
    return String(price).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');
}

// 商品名をマウスオーバーすると,全文を表示する.
$(document).on(
    'mouseover', '.productName', function () {
        var datatext = $(this).attr('data-text');
        $(this).attr('title', datatext);
    }
);

// 全商品一覧へ戻るボタンを押下すると,商品一覧画面に戻る
$(document).on(
    'click', '.goBack', function () {
        window.location.href = 'index.html';
    }
);
