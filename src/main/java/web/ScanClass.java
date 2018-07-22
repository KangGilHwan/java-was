package web;

import annotation.Controller;
import annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ScanClass {

    private static final Logger log = LoggerFactory.getLogger(ScanClass.class);
    private static String ROOT;
    private static Map<String, Object> controller = new HashMap<>();

    static {
        URL packageDirURL = Thread.currentThread().getContextClassLoader().getResource("./");
        ROOT = packageDirURL.getFile();
        log.debug("root path : {}", ROOT);

        File directory = new File(ROOT);
        List<Class<?>> classes = new ArrayList<>();

        if (directory.exists()) {
            File[] files = directory.listFiles();
            classes = scan(files, ROOT, classes);
        }

        for (Class<?> scanedClass : classes) {
            log.debug("Scaned Class : {}", scanedClass.getName());
            if (scanedClass.isAnnotationPresent(Controller.class)) {
                addController(scanedClass);
            }
        }
    }

    public static List<Class<?>> scan(File[] files, String directoryName, List<Class<?>> classes) {
        String anotherPath = "";
        for (File file : files) {
            if (file.isDirectory()) {
                anotherPath = directoryName + file.getName();
                File fileChild = new File(anotherPath);
                scan(fileChild.listFiles(), anotherPath, classes);
            } else {
                addClassFileToList(file.getName(), directoryName, classes);
            }
        }
        return classes;
    }

    public static void checkDirectoryOrFile(File file, String directoryName, List<Class<?>> classes) {
        String childPath = "";
        if (file.isDirectory()) {
            childPath = directoryName + file.getName();
            File fileChild = new File(childPath);
            scan(fileChild.listFiles(), childPath, classes);
            return;
        }
        addClassFileToList(file.getName(), directoryName, classes);
    }

    private static void addClassFileToList(String fileName, String directoryName, List<Class<?>> classes) {
        if (fileName.endsWith(".class")) {
            fileName = fileName.substring(0, fileName.length() - 6);
            try {
                Class<?> clazz = Class.forName(directoryName.substring(ROOT.length()) + "." + fileName);
                classes.add(clazz);
                log.debug("Path ! : {}", directoryName);
                log.debug("File Name! : {}", fileName);
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }
    }

    private static void addController(Class<?> clazz) {
        try {
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                log.debug("RequestMapping value : {}", requestMapping.value());
                controller.put(requestMapping.value(), clazz.newInstance());
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    public static Object getController(String requestUrl){
        Set<String> keys = controller.keySet();
        for (String key : keys){
            if (requestUrl.startsWith(key) && !requestUrl.contains(".")){
                return controller.get(requestUrl);
            }
        }
        log.debug("Null!!");
        return null;
    }
}
