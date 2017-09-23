package johnny.nguyen.nbaagent;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by johnnywin24 on 9/20/17.
 */

public class GameDeserializer implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        String time = json.getAsString();
        time = time.substring(time.indexOf("T") + 1);
        int hours = Integer.parseInt(time.substring(0, 2));
        if (hours > 12) {
            hours = hours - 12;
            time = time + " PM";
        } else time = time + " AM";

        String newTime = String.valueOf(hours) + time.substring(2);

        return newTime;
    }
}
