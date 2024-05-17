package pl.put.poznan.qualitychecker.logic;

import com.google.gson.*;
import java.lang.reflect.Type;

public class ScenarioStepComponentDeserializer implements JsonDeserializer<ScenarioStepComponent> {

    @Override
    public ScenarioStepComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement substepsElement = jsonObject.get("substeps");

        if (substepsElement != null && substepsElement.isJsonArray()) {
            return context.deserialize(json, ScenarioStepComposite.class);
        } else {
            return context.deserialize(json, ScenarioStepLeaf.class);
        }
    }
}