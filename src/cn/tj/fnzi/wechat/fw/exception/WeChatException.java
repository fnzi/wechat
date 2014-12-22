package cn.tj.fnzi.wechat.fw.exception;

public class WeChatException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WeChatException() {
        super();
    }

    public WeChatException(String message) {
        super(message);
    }

    public WeChatException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeChatException(Throwable cause) {
        super(cause);
    }
}
