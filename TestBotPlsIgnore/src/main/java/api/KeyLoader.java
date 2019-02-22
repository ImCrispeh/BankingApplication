package api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class KeyLoader {
    public String loadKey(String keyName) {
        Properties props = new Properties();
        String fileName = "ApiKeys.config";
        InputStream is = null;

        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return props.getProperty(keyName);
    }
}
