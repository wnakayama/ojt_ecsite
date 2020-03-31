
// editData.htmlのDOMツリーが構築された後にgetAllProductメソッドを実行
window.addEventListener('DOMContentLoaded', getAllProduct());

// 商品一覧画面→商品データ管理画面の遷移で、ViewAllProductServletで保存したセッションオブジェクト(全商品データ)が失われる
// 【暫定】再度vieewAllProductServletを使って全商品データを取得
function getAllProduct() {
    $.get('http://' + location.host + '/ojt_ecsite/ViewAllProductServlet')
        .done(function (data) {
            // 通信成功時
            const allProduct = data || {};
            sessionStorage.setItem('allProduct', allProduct);
            console.log('管理画面：全商品データを再取得')
        }).fail(function (error) {
            // 通信エラーの場合はこちらが実行され、errorに返ってきた詳細が入る
            console.log(error);
        });
}


// 「登録」ボタン押下時,フォーム入力値の取得,入力チェック,登録機能の呼び出しをする.
$(document).on(
    'click', '.register', function () {
        const productName = $('input[name="productName"]').val();
        const category = ($('select[name="category"]').val());
        const priceExcludeTax = convertToHalfWidthNumber($('input[name="priceExcludeTax"]').val());
        if (validateRegisterParameter(productName, category, priceExcludeTax)) {
            sendRegisterParameter(productName, category, priceExcludeTax)
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
            $('input[name="category"]').val() != "" ||
            $('input[name="priceExcludeTax"]').val() != "") {

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
        message: '●税込み価格フォームの入力が正しくありません(整数で価格を指定してください)'
    },
    CONTAINS_QUOTATION: {
        message: '●シングルクォーテーション(\')またはダブルクォーテーション(\")が含まれる文字列では検索できません'
    }
})


/**
 * クライアント側で実施する入力チェック
 */
function validateRegisterParameter(productName, category, priceExcludeTax) {
    if (productName == "" && category == "" && priceExcludeTax == "") {
        $('.message').text(VALIDATION_ERROR.ALL_INPUT_EMPTY.message);
        return false;
    }
    if (productName.includes("\'") || productName.includes("\"")) {
        $('.message').text(VALIDATION_ERROR.CONTAINS_QUOTATION.message);
        return false;
    }
    if (priceExcludeTax != "") {
        if (!priceExcludeTax.match(/^([1-9]\d*|0)$/)) {
            $('.message').text(VALIDATION_ERROR.NOT_UNSIGNED_INTEGER.message);
            return false;
        }
    }
    return true;
}


/**
 * 登録処理
 * 利用者がフォームに入力した商品情報をPOSTで送信する.
 * サーバ側から登録結果のJSONを受け取り,メッセージ領域の表示を動的に変更する.
 */
function sendRegisterParameter(productName, category, priceExcludeTax) {
    console.log(productName);
    console.log(category);
    console.log(priceExcludeTax);
    $.post('http://' + location.host + '/ojt_ecsite/RegisterProductServlet',
        {
            "productName": productName,
            "category": category,
            "priceExcludeTax": priceExcludeTax
        }
    ).done(function (data) {
        // 通信成功時
        try {
            const registerResultMessage = JSON.parse(data);
            if (!registerResultMessage.hasOwnProperty('errorMessage')) {
                $('.message').text(registerResultMessage.systemMessage);
            } else {
                $('.message').text(registerResultMessage.errorMessage);
            }
        } catch (SyntaxError) {
            $('.message').text('登録結果の取得に失敗しました お手数ですが開発者までご連絡ください');
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
