package pl.put.poznan.qualitychecker.logic;

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Custom Gson adapter for serializing and deserializing {@link ScenarioStepComponent} objects.
 * This class implements both {@link JsonDeserializer} and {@link JsonSerializer} to handle
 * the conversion between JSON and {@link ScenarioStepComponent} subclasses.
 */
public class ScenarioStepComponentAdapter implements JsonDeserializer<ScenarioStepComponent>, JsonSerializer<ScenarioStepComponent> {

    /**
     * Deserializes JSON into a {@link ScenarioStepComponent} object.
     * This method checks for the presence of the "substeps" field in the JSON object to determine
     * whether to instantiate a {@link ScenarioStepComposite} or {@link ScenarioStepLeaf}.
     *
     * @param json the JSON data being deserialized
     * @param typeOfT the type of the Object to deserialize to
     * @param context the deserialization context
     * @return the deserialized {@link ScenarioStepComponent} object
     * @throws JsonParseException if JSON is not in the expected format
     */
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

    /**
     * Serializes a {@link ScenarioStepComponent} object into its JSON representation.
     * This method checks the type of the {@link ScenarioStepComponent} instance and
     * serializes it as either a {@link ScenarioStepComposite} or {@link ScenarioStepLeaf}.
     *
     * @param src the source object to serialize
     * @param typeOfSrc the actual type of the source object
     * @param context the serialization context
     * @return the serialized JSON element
     */
    @Override
    public JsonElement serialize(ScenarioStepComponent src, Type typeOfSrc, JsonSerializationContext context) {
        if (src instanceof ScenarioStepComposite) {
            return context.serialize(src, ScenarioStepComposite.class);
        } else {
            return context.serialize(src, ScenarioStepLeaf.class);
        }
    }
}
