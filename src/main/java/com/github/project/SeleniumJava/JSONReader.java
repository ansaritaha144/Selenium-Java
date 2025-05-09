package com.github.project.SeleniumJava;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONReader {

	public static JsonNode readJsonFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;

        try {
            jsonNode = mapper.readTree(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonNode;
    }

	
	public static Map<String, Object> readJsonFileAsMap(String filePath) {
	    ObjectMapper mapper = new ObjectMapper();
	    try {
	        return mapper.readValue(new File(filePath), Map.class);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
}
