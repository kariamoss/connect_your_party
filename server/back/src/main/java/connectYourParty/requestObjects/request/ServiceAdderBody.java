package connectYourParty.requestObjects.request;

import java.io.InputStream;

public class ServiceAdderBody {
    private InputStream file;
    private String name;
    private String module;

    public InputStream getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public String getModule() {
        return module;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public boolean check(){
        return name != null &&
                file != null &&
                module != null;
    }
}
