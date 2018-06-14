package connectYourParty.exceptions.music;

/**
 * Throw when a problem occurs in the user id retrieval
 */
public class CannotGetUserId extends Exception {
    public CannotGetUserId(){}
    public CannotGetUserId(String message){super(message);}
}
