package com.smapley.anthelp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smapley.base.http.BaseResponse;
import com.smapley.base.http.LoginResponse;

import org.junit.Test;

import java.lang.reflect.Type;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void gsonTest(){
        String json = "{\"status\":\"success\",\"data\":{\"ukey\":\"fcb0a8b6c199daaf713c720b2eb50a4c\"}}";
        String json2 = "{\"status\":\"success\"}";

        GsonResponsePasare<LoginResponse> pasare =new GsonResponsePasare<LoginResponse>(){};
        LoginResponse response = pasare.deal(json2);
        System.out.println("");

        new format<LoginResponse>().form(json);
    }

    public class format<T>{
        public void form(String json){
            GsonResponsePasare<T> pasare =new GsonResponsePasare<T>(){};
            T  response = pasare.deal(json);
            System.out.println("");
        }
    }
}