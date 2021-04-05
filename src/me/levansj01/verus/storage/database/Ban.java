package me.levansj01.verus.storage.database;

import java.util.UUID;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.util.bson.Document;

public class Ban {
    private final String subType;
    private final String name;
    private final String type;
    private final long timestamp;
    private final UUID uuid;

    public String getName() {
        return this.name;
    }

    public static Ban create(PlayerData playerData, Check check) {
        return new Ban(playerData.getUuid(), playerData.getName(), System.currentTimeMillis(),
                check.getType().getName(), check.getSubType());
    }

    public Document toBson() {
        Document document = new Document();
        document.put("uuid", (Object) this.uuid);
        document.put("name", (Object) this.name);
        document.put("timestamp", (Object) this.timestamp);
        document.put("type", (Object) this.type);
        document.put("subType", (Object) this.subType);
        return document;
    }

    public String getType() {
        return this.type;
    }

    public Ban(UUID uUID, String string, long l, String string2, String string3) {
        this.uuid = uUID;
        this.name = string;
        this.timestamp = l;
        this.type = string2;
        this.subType = string3;
    }

    public String getSubType() {
        return this.subType;
    }

    public static Ban fromBson(Document document) {
        return new Ban((UUID) document.get("uuid", UUID.class), document.getString("name"),
                document.getLong("timestamp"), document.getString("type"), document.getString("subType"));
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
