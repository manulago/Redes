package es.udc.redes.tutorial.info;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Info {

    public static void main(String[] args) throws Exception {
        String relativePath = args[0];
        Path absolutePath = Paths.get("p0-files", relativePath).toAbsolutePath();

        File file = absolutePath.toFile();
        BasicFileAttributes attrs = Files.readAttributes(absolutePath, BasicFileAttributes.class);

        String name = file.getName();
        String extension = name.substring(name.lastIndexOf(".") + 1);

        String type = Files.probeContentType(absolutePath);

        long size = attrs.size();
        long milisegs = attrs.lastModifiedTime().toMillis();

        Date date = new Date(milisegs);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        String lastModified = format.format(date);



        String isDirectory = file.isDirectory() ? "directory" : "not directory";

        System.out.println("Name: " + name);
        System.out.println("Extension: " + extension);
        System.out.println("Type: " + type);
        System.out.println("Size: " + size + " bytes");
        System.out.println("Last Modified: " + lastModified);
        System.out.println("Is Directory: " + isDirectory);
        System.out.println("Absolute Path: " + absolutePath.toString());
    }
}
