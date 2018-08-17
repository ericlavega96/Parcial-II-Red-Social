package servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

import javax.swing.*;


public class JsonTransformer{

    private static Gson gson = new GsonBuilder().serializeNulls().create();


    public static String render(Object model) {
        return gson.toJson(model);
    }

    public static ResponseTransformer json(){
        return JsonTransformer::render;
    }

}
