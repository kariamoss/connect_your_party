package ConnectYourParty.modulesLogic.service;

public class ByteClassLoader extends ClassLoader {

    public ByteClassLoader(ClassLoader parent){
        super(parent);
    }


    public Class getClassFromByte(byte[] bin) throws ClassFormatError{
        return defineClass(null,bin,0,bin.length);
    }
}
