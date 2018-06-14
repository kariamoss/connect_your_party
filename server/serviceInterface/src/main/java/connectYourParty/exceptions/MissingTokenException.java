package connectYourParty.exceptions;

/**
 * Throw when a token is missing while authenticating
 */
public class MissingTokenException extends Exception{
    public MissingTokenException(){}

    public MissingTokenException(String message){super(message);}
}
