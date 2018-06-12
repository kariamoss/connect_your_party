package connectYourParty.exception;

public class NoSuchServiceException extends Exception{
    public NoSuchServiceException(){}

    public NoSuchServiceException(String message)
    {
        super(message);
    }
}
