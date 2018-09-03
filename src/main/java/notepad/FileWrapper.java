package notepad;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileWrapper {

    private String text;
    private Map<String, String> attr;

    public FileWrapper() {
        attr = new HashMap<>();
    }

    public void updateFileWrapper(String text, String type, String authorName, String aothorOrg) {
        this.text = text;
        attr.put("user:type", type);
        attr.put("user:authorName", authorName);
        attr.put("user:authorOrganization", aothorOrg);
    }

    public void readFromFile(File file) throws IOException {
        if (file != null) {
            String line;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                text = sb.toString();
                Path path = Paths.get(file.getAbsolutePath());
                readAttributes(path);
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
    }

    public void readAttributes(Path path) {
        List<String> attrName = Arrays.asList("user:type", "user:authorName", "user:authorOrganization", "acl:owner", "size", "lastModifiedTime", "lastAccessTime", "creationTime");

        attrName.forEach(name -> {
            try {
                Object value = Files.getAttribute(path, name, LinkOption.NOFOLLOW_LINKS);
                if (value instanceof byte[]) {
                    attr.put(name, new String((byte[])value));
                } else {
                    attr.put(name, value != null ? value.toString() : "");
                }
            } catch (IOException | IllegalArgumentException e) {
                attr.put(name, "");
            }
        });
    }

    public void saveToFile(File file) throws IOException {
        if (file != null) {
            Writer writer = new BufferedWriter(new FileWriter(file));
            writer.write(this.text);
            writer.flush();
            writer.close();
            Path path = Paths.get(file.getAbsolutePath());
            Files.setAttribute(path, "user:type", attr.get("user:type").getBytes());
            Files.setAttribute(path, "user:authorName", attr.get("user:authorName").getBytes());
            Files.setAttribute(path, "user:authorOrganization", attr.get("user:authorOrganization").getBytes());
        }
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}


