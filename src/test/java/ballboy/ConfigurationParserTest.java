package ballboy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ConfigurationParserTest {

    @Test
    void TestConstructor(){
        ConfigurationParser configurationParser = new ConfigurationParser();
        assertNotNull(configurationParser);
    }

    @Test
    void TestParseConfigCorrectPath(){
        ConfigurationParser configurationParser = new ConfigurationParser();
        configurationParser.parseConfig("config.json");
    }

    @Test
    void TestParseConfigEmpty(){
        ConfigurationParser configurationParser = new ConfigurationParser();
        Exception exception = assertThrows(ConfigurationParseException.class, () -> configurationParser.parseConfig(""));
        assertEquals("Configuration file () not found \n", exception.getMessage());
    }

    @Test
    void TestParseConfigFileNotFound(){
        ConfigurationParser configurationParser = new ConfigurationParser();
        Exception exception = assertThrows(NullPointerException.class, () -> configurationParser.parseConfig("NotExist"));
    }

    @Test
    void TestParseConfigURLSyntax(){
        ConfigurationParser configurationParser = new ConfigurationParser();
        Exception exception = assertThrows(NullPointerException.class, () -> configurationParser.parseConfig("///"));
    }
}
