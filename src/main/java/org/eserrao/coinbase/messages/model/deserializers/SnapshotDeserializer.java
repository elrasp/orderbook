package org.eserrao.coinbase.messages.model.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.eserrao.coinbase.messages.model.Snapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SnapshotDeserializer extends StdDeserializer<List<Snapshot>> {
    public SnapshotDeserializer() {
        this(null);
    }

    public SnapshotDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<Snapshot> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        TreeNode treeNode = jsonParser.readValueAsTree();

        List<Snapshot> values = new ArrayList<>();
        for (int i = 0; i < treeNode.size(); i++) {
            TreeNode tuple = treeNode.get(i);
            double price = ((TextNode)tuple.get(0)).asDouble(0);
            double size = ((TextNode)tuple.get(1)).asDouble(0);
            Snapshot snapshot = new Snapshot(price, size);
            values.add(snapshot);
        }
        return values;
    }
}
