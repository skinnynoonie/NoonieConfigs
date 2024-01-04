package me.skinnynoonie.noonieconfigs.converter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFormConverterTest {

    JsonFormConverter jsonFormConverter = new JsonFormConverter();

    @Test
    void toRawForm() {
        assertEquals(jsonFormConverter.toRawForm(new Object()), new JsonObject());
        assertEquals(jsonFormConverter.toRawForm(new RandomObject()), RandomObject.jsonForm);
    }

    @Test
    void toObjectForm() {
    }

    static class RandomObject {
        static JsonObject jsonForm = JsonParser.parseString("{\"string\":\"hi\",\"number\":5}").getAsJsonObject();

        String string = "hi";
        int number = 5;
    }

}