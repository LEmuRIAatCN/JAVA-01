package discoverer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Discoverer {
//    private static final String FILE_NAME;
//    private static final String FILE_DIR;
//    static {
//        FILE_NAME = System.getProperty("gateway.registry.file.name");
//        FILE_DIR = System.getProperty("gateway.registry.file.dir");
//    }
//    private static String CONTENT = "";
//    private static void load(){
//
//    }
//    private static void update(){
//
//    }
    public static List<String> get(){
        List<String> strings = new ArrayList<>();
        strings.add("localhost:8801");
        strings.add("localhost:8802");
        strings.add("localhost:8803");
        strings.add("localhost:8808");
        return strings;
    }
}
