package ConnectYourParty.modulesLogic.music.interpreter;

import javax.json.JsonArray;

public interface IMusicInterpreter {
    JsonArray searchMusic(String search);

    JsonArray getListMusic(String event);

    void addMusicToEvent(String music, String event);
}
