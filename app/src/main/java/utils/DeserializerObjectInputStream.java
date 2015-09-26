package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class DeserializerObjectInputStream extends ObjectInputStream {
    public DeserializerObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();

        Class localClass;
        try {
            localClass = Class.forName(resultClassDescriptor.getName());
        } catch (ClassNotFoundException var10) {
            return resultClassDescriptor;
        }

        ObjectStreamClass localClassDescriptor = ObjectStreamClass.lookup(localClass);
        if (localClassDescriptor != null) {
            long localSUID = localClassDescriptor.getSerialVersionUID();
            long streamSUID = resultClassDescriptor.getSerialVersionUID();
            if (streamSUID != localSUID) {
                StringBuffer s = new StringBuffer("Overriding serialized class version mismatch: ");
                s.append("local serialVersionUID = ").append(localSUID);
                s.append(" stream serialVersionUID = ").append(streamSUID);
                new InvalidClassException(s.toString());
                resultClassDescriptor = localClassDescriptor;
            }
        }

        return resultClassDescriptor;
    }
}

