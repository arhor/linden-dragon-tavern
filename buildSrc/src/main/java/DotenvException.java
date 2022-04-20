public abstract class DotenvException extends RuntimeException {

    protected DotenvException(final String message) {
        super(message);
    }

    protected DotenvException(final Throwable cause) {
        super(cause);
    }
}
