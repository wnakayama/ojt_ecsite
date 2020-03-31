package ojt_ecsite;

public class SystemMessage {
    private final String systemMessage;

    public String getSystemMessage() {
        return systemMessage;
    }

    SystemMessage(SystemMessageId systemMessageId) {
        switch (systemMessageId) {
            case REGISTER_SUCCESS:
                this.systemMessage = "登録に成功しました";
                break;
            default:
                this.systemMessage = "予期せぬエラーが発生しました お手数ですが開発者までご連絡ください";
                break;
        }
    }
}
