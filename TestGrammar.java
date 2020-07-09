import com.google.gson.*;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class TestGrammar {




    public static void main(String[] args) {
        // 除非kotlin 全部设置默认参数，否则默认参数不起作用
        String string2 = "{\"name\":\"dhg\",\"desc\":\"shuaige\",\"location\":\"beijing\",\"company\":{\"name\":\"hdf\",\"type\":\"medcine\"}}";
        Gson gson = getNonNullStrGson();
        System.out.println(gson.fromJson(string2,Person.class));
    }

    public static Gson getNonNullStrGson(){
        Gson gson = new Gson();
        Class<?> cl = gson.getClass();
        try {
            Field factoriesField = cl.getDeclaredField("factories");
            factoriesField.setAccessible(true);
            List<TypeAdapterFactory> factoriesUnmodify = (List<TypeAdapterFactory>) factoriesField.get(gson);
            List<TypeAdapterFactory> factories = new ArrayList<>(factoriesUnmodify);
            Field constructorField = cl.getDeclaredField("constructorConstructor");
            constructorField.setAccessible(true);
            ConstructorConstructor constructorConstructor = (ConstructorConstructor) constructorField.get(gson);
            Field jsonAdapterField = cl.getDeclaredField("jsonAdapterFactory");
            jsonAdapterField.setAccessible(true);
            factories.remove(factories.size() - 1);
            factories.add(new NonNullStrTypeAdapterFactory(constructorConstructor, FieldNamingPolicy.IDENTITY,Excluder.DEFAULT,new JsonAdapterAnnotationTypeAdapterFactory(constructorConstructor)));
            factoriesField.set(gson, Collections.unmodifiableList(factories));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return new Gson();
        }
        return gson;
    }
}
