package com.ttdys108.wechat.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class XmlUtils {

    private static final XmlMapper xmlMapper = new XmlMapper(new JacksonXmlModule());

    public static String toXmlString(Object obj) {
        try {
            return xmlMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toXmlString(Object obj, String rootName) {
        String xml = toXmlString(obj).trim();
        System.out.println(xml);
        xml = xml.replaceFirst("(?<=<).*?(?=>)", rootName);
        System.out.println(xml);
        xml = xml.replaceFirst("(?<=</)[^<>]*?(?=>$)", rootName);
        return xml;
    }

    public static <T> T parseXml(String xml, Class<T> clz) {
        try {
            return xmlMapper.readValue(xml, clz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("b", "12");
        map.put("a", "32");
        System.out.println(toXmlString(map, "xml"));
    }

}
