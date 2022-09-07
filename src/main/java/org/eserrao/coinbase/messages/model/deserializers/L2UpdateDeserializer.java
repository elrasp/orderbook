package org.eserrao.coinbase.messages.model.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.eserrao.coinbase.messages.model.L2Update;
import org.eserrao.model.SideType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class L2UpdateDeserializer extends StdDeserializer<List<L2Update>> {
    public L2UpdateDeserializer() {
        this(null);
    }

    public L2UpdateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<L2Update> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        TreeNode treeNode = jsonParser.readValueAsTree();

        List<L2Update> values = new ArrayList<>();
        for (int i = 0; i < treeNode.size(); i++) {
            TreeNode tuple = treeNode.get(i);
            SideType sideType = Enum.valueOf(SideType.class, ((TextNode) tuple.get(0)).asText().toUpperCase());
            double price = ((TextNode) tuple.get(1)).asDouble(0);
            double size = ((TextNode) tuple.get(2)).asDouble(0);
            L2Update update = new L2Update(sideType, price, size);
            values.add(update);
        }
        return values;
    }
}
