import notepad.FileWrapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileTest {

    private static final String TEST_STRING = "test";
    private static final String SAVE_TEST_STRING = "save_test";

    private static final String TYPE_ATTR = "user:type";
    private static final String AUTHOR_NAME_ATTR = "user:authorName";
    private static final String AUTHOR_ORG_ATTR = "user:authorOrganization";

    @Test
    public void readExistingFile() throws IOException {
        FileWrapper fileWrapper = new FileWrapper();
        File file = new File("src/test/resources/readTest.txt");
        fileWrapper.readFromFile(file);

        Assert.assertEquals(fileWrapper.getText().trim(), TEST_STRING);

        Map<String, String> attr = fileWrapper.getAttr();
        Assert.assertEquals(attr.get(TYPE_ATTR), TEST_STRING);
        Assert.assertEquals(attr.get(AUTHOR_NAME_ATTR), TEST_STRING);
        Assert.assertEquals(attr.get(AUTHOR_ORG_ATTR), TEST_STRING);
    }

    @Test
    public void createNewFile() throws IOException {
        FileWrapper fileWrapper = new FileWrapper();
        File file = new File("src/test/resources/writeTest.txt");

        fileWrapper.setText(SAVE_TEST_STRING);
        Map<String, String> attr = fileWrapper.getAttr();
        attr.put(TYPE_ATTR, SAVE_TEST_STRING);
        attr.put(AUTHOR_NAME_ATTR, SAVE_TEST_STRING);
        attr.put(AUTHOR_ORG_ATTR, SAVE_TEST_STRING);
        fileWrapper.saveToFile(file);

        fileWrapper = new FileWrapper();
        fileWrapper.readFromFile(file);
        Assert.assertEquals(fileWrapper.getText().trim(), SAVE_TEST_STRING);
        attr = fileWrapper.getAttr();
        Assert.assertEquals(attr.get(TYPE_ATTR), SAVE_TEST_STRING);
        Assert.assertEquals(attr.get(AUTHOR_NAME_ATTR), SAVE_TEST_STRING);
        Assert.assertEquals(attr.get(AUTHOR_ORG_ATTR), SAVE_TEST_STRING);

        file.delete();
    }
}
