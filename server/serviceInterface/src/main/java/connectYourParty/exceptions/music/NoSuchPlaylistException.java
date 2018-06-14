package connectYourParty.exceptions.music;

/**
 * Throw when a problem occurs in the retrieval of playlist
 */
public class NoSuchPlaylistException extends Exception {
    public NoSuchPlaylistException(){}
    public NoSuchPlaylistException(String message){super(message);}
}
