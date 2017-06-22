/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx;

import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.deser.StdDeserializer;
import org.codehaus.jackson.node.IntNode;

/**
 *
 * @author acruzb
 */
public class TestJson {
    public static void main(String[] args) throws IOException {
        String json = "{\"c\": false, \"a\": 1, \"b\": \"Jimbo\"}";
        ObjectMapper mapper = new ObjectMapper();
        Hola hola = mapper.readValue(json, Hola.class);
        System.out.println(hola.getA());
    }
}


@JsonDeserialize(using = Hola.HolaDeserializer.class)
class Hola{
    
    private int a;
    private String b;
    private boolean c;

    public Hola() {
        System.out.println("Iniciando");
    }
    public int getA() {
        return a;
    }
    public void setA(int a) {
        this.a = a;
        System.out.println("Setenado a");
    }
    public String getB() {
        return b;
    }
    public void setB(String b) {
        this.b = b;
        System.out.println("Setenado b");
    }
    public boolean isC() {
        return c;
    }
    public void setC(boolean c) {
        this.c = c;
        System.out.println("Setenado c");
    }  
    
    public static class HolaDeserializer extends StdDeserializer<Hola> { 
 
    public HolaDeserializer() { 
        this(null); 
    } 
 
    public HolaDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public Hola deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        int id = node.get("a").asInt();
        String itemName = node.get("b").asText();
        boolean userId = node.get("c").asBoolean();
        Hola hola = new Hola();
        hola.setA(id);
        hola.setB(itemName);
        hola.setC(userId);
        return hola;
    }
}
}

