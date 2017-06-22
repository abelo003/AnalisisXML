/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.business;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author acruzb
 */
public class LoadProperties {
    
    private static final Logger LOGGER = Logger.getLogger(LoadProperties.class);    
    public static final HashMap<String, String> PROPS = new HashMap<String, String>();
    
    static{
        LOGGER.info("Iniciando la carga de properties.");
        Properties properties = new Properties();
        String filename = "properties/file.properties";
        try {
            properties.load(LoadProperties.class.getClassLoader().getResourceAsStream(filename));
            for (String key : properties.stringPropertyNames()) {
                PROPS.put(key, properties.getProperty(key));
            }
            LOGGER.info("Se terminan de cargar los properties correctamente.");
        }
        catch (IOException e) {
            LOGGER.info("No se pudieron cargar los properties.");
        }
    }
}
