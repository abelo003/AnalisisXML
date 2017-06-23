/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx;

import com.cruz.mx.views.Principal;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.selectors.FileSelector;
import org.apache.tools.ant.types.selectors.SelectorScanner;

/**
 *
 * @author acruzb
 */
public class TestJson {

    public static void main(String[] args){
        Path p1 = Paths.get("E:\\Users\\acruzb\\Desktop\\xmls");
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{"**/*" + Principal.XML_FORMAT});
        scanner.setBasedir(p1.toString());
        scanner.setCaseSensitive(false);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();
        for (String file : files) {
            System.out.println(p1.resolve(file));
        }
//        System.out.println(p1);
//        System.out.println("Obteniendo lista de archvios");
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(p1, "*")) { // "v" or "w" followed by anything
//            for (Path path : stream){
//                System.out.println(path.getFileName().toAbsolutePath());
//                System.out.print("es file: " + Files.isDirectory(path)+"      ");
//                if(!Files.isDirectory(path)){
//                    if(path.getFileName().toString().endsWith(".xml")){
//                        System.out.println(" Es un xml");
//                    }
//                    else{
//                        System.out.println(" No es xmls");
//                    }
//                }
//            }
//        } catch (IOException ex) {
//
//        }
    }
}
