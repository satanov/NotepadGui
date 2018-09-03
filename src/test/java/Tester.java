import notepad.FileWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@PowerMockIgnore({"javax.management.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(FileWrapper.class)
public class Tester {

    private static final String AUTHOR_NAME = "user:authorName";
    private static final String TYPE_NAME = "user:type";
    private static final String OWNER = "acl:owner";

    @InjectMocks
    private FileWrapper fileWrapper = new FileWrapper();

    @Before
    public void setUp() throws Exception {
        mockStatic(Files.class);
        when(Files.getAttribute(any(Path.class), eq(TYPE_NAME), any(LinkOption.class))).thenReturn("type");
        when(Files.getAttribute(any(Path.class), eq(OWNER), any(LinkOption.class))).thenReturn("owner");
        when(Files.getAttribute(any(Path.class), eq(AUTHOR_NAME), any(LinkOption.class))).thenReturn("author");

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAttribute() throws Exception {
        Path path = Paths.get("123.txt");

        fileWrapper.readAttributes(path);
        Map<String, String> attr = fileWrapper.getAttr();

        Assert.assertEquals(attr.get(OWNER), "owner");
        Assert.assertEquals(attr.get(TYPE_NAME), "type");
        Assert.assertEquals(attr.get(AUTHOR_NAME), "author");
    }

}
