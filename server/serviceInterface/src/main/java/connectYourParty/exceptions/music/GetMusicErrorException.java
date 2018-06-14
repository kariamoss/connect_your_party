package connectYourParty.exceptions.music;

/**
 * Throw when a problem occurs in the retrieval of musics
 */
public class GetMusicErrorException extends Exception {

    public GetMusicErrorException(){}

    public GetMusicErrorException(String message){super(message);}
}
