package e.rosti.listery;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {

    @TypeConverter
    public String fromMatesList(List<Mate> mates){
        if(mates == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Mate>>(){}.getType();
        String json = gson.toJson(mates, type);
        return json;
    }

    @TypeConverter
    public List<Mate> toMatesList(String matesString){
        if(matesString == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Mate>>(){}.getType();
        List<Mate> mates = gson.fromJson(matesString,type);
        return mates;
    }
}
