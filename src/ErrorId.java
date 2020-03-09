package ojt_ecsite;

/**
 * システム全体で共有するエラーID(列挙型)のクラス.
 *
 * @author nakayama
 *
 */
public enum ErrorId {
    ALL_INPUT_EMPTY, CONTAINS_QUOTATION, EXCEEDS_CHARACTERS, NOT_UNSIGNED_INTEGER, REVERSED_PRICE_RANGE,
    UNEXPECTED_STATE
}
