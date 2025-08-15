package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.annotations.DataProvider;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class DataUtil {
    static String FilePath = "src/test/resources/RegistrationData.json";
    static String File_Path = "src/test/resources/";

    @DataProvider(name = "RegisterDataProvider")
    public static Object[][] readFromJsonFile(Method method) throws FileNotFoundException {
        FileReader reader = new FileReader(FilePath);
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        JsonObject registerData = jsonObject.getAsJsonObject("RegisterData");
        String testCaseName = method.getName();
        JsonObject record = registerData.getAsJsonObject(testCaseName);

        return new Object[][] {
                {record.get("firstname").getAsString(),record.get("lastname").getAsString()
                        ,record.get("password").getAsString(),record.get("confirmedPassword").getAsString()}
        };
    }

    public static String getJsonData(String FileName, String field) throws FileNotFoundException {

        FileReader reader = new FileReader(File_Path + FileName + ".json");
        JsonElement jsonElement = JsonParser.parseReader(reader);
        return jsonElement.getAsJsonObject().get(field).getAsString();
    }

    public static String getPropertyValue(String FileName, String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(File_Path + FileName + ".properties"));
        return properties.getProperty(key);
    }
}
