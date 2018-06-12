package connectYourParty.exceptions.music;

/**
 * Throw when a problem occurs in a song research
 */
public class SearchMusicErrorException extends Exception {

    public SearchMusicErrorException(){}

    public SearchMusicErrorException(String message){super(message);}
}
