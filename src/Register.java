package ojt_ecsite;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Register {

    static List<Product> allProductList;

    static void setAllProductList(List<Product> allProductList) {
        Register.allProductList = allProductList;
    }
    // シーケンス図に書いてきた処理を、こっちでもコメントで並べていく そこに肉付け

    public String registerProductData(Map<String, String[]> inputParameterMap) throws IOException {

        // 入力内容をチェックする
        InputValidator inputValidator = new InputValidator();
        ojt_ecsite.InputValidator.ValidationResult validationResult = inputValidator
                .validateRegisterParameters(inputParameterMap);

        ObjectMapper mapper = new ObjectMapper();
        String registerResultJson;

        System.out.println(inputParameterMap);

        switch (validationResult) {
            case VALID:
                System.out.println("VALID");
                // 商品IDを用意するために、最大値を探る streamAPI
                List<Integer> allIdList = allProductList.stream().map(p -> p.getProductID())
                        .collect(Collectors.toList());
                Optional<Integer> s = allIdList.stream().max(Comparator.naturalOrder());
                Integer maxId = s.get();
                Integer newProductId = maxId + 1;

                String productName = inputParameterMap.get(InputConstant.KEY_PRODUCTNAME)[InputConstant.FIRST_VALUE];
                String category = inputParameterMap.get(InputConstant.KEY_CATEGORY)[InputConstant.FIRST_VALUE];
                int priceExcludeTax = Integer.parseInt(
                        inputParameterMap.get(InputConstant.KEY_PRICE_EXCLUDE_TAX)[InputConstant.FIRST_VALUE]);

                // 税込み価格を算出する
                TaxCalculator taxCalculator = new TaxCalculator();
                int priceIncludeTax = taxCalculator.calculatePriceIncludeTax(category, priceExcludeTax);

                // 【未】画像データのパスを用意
                String imagePath = "nowPrinting.png"; // 【暫定】画像データのパスを用意

                // new Product
                Product product = new Product(newProductId, productName, category, priceExcludeTax, priceIncludeTax,
                        imagePath);

                // 商品情報をCSVファイルに書く
                ProductDataWriter productDataWriter = new ProductDataWriter();
                productDataWriter.WriteProductData(product);

                // 【未】画像ファイルをimagesフォルダに保存

                // 登録完了メッセージ
                SystemMessage messageRegisterSuccess = new SystemMessage(SystemMessageId.REGISTER_SUCCESS);
                registerResultJson = mapper.writeValueAsString(messageRegisterSuccess);
                break;

            case INVALID_NECESSARY_INPUT_EMPTY:
                ErrorMessage messageNecessaryInputEmpty = new ErrorMessage(ErrorId.NECESSARY_INPUT_EMPTY);
                registerResultJson = mapper.writeValueAsString(messageNecessaryInputEmpty);
                break;

            case INVALID_CONTAINS_QUOTATION:
                ErrorMessage messageContainsQuotation = new ErrorMessage(ErrorId.CONTAINS_QUOTATION);
                registerResultJson = mapper.writeValueAsString(messageContainsQuotation);
                break;

            case INVALID_EXCEEDS_CHARACTERS:
                ErrorMessage messageExceedsCharacters = new ErrorMessage(ErrorId.EXCEEDS_CHARACTERS);
                registerResultJson = mapper.writeValueAsString(messageExceedsCharacters);
                break;

            case INVALID_NOT_UNSIGNED_INTEGER:
                ErrorMessage messageNotUnsignedInteger = new ErrorMessage(ErrorId.NOT_UNSIGNED_INTEGER);
                registerResultJson = mapper.writeValueAsString(messageNotUnsignedInteger);
                break;

            case INVALID_NOT_IMAGE_FILE:
                ErrorMessage messageNotImageFile = new ErrorMessage(ErrorId.NOT_IMAGE_FILE);
                registerResultJson = mapper.writeValueAsString(messageNotImageFile);
                break;

            case INVALID_EXCEEDS_FILE_SIZE:
                ErrorMessage messageExceedsFileSize = new ErrorMessage(ErrorId.EXCEEDS_FILE_SIZE);
                registerResultJson = mapper.writeValueAsString(messageExceedsFileSize);
                break;

            default:
                ErrorMessage messageUnexpectedState = new ErrorMessage(ErrorId.UNEXPECTED_STATE);
                registerResultJson = mapper.writeValueAsString(messageUnexpectedState);
                break;
        }
        return registerResultJson;

    }
}
