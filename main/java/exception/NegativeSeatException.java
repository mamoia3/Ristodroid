package exception;

public class NegativeSeatException extends Exception {

    private static final long serialVersionUID = 1L;

    public NegativeSeatException(String s) {
        super(s);
    }
}
