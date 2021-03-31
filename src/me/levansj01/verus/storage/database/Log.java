package me.levansj01.verus.storage.database;

import java.util.UUID;
import java.util.function.Supplier;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.util.bson.Document;

public class Log {
    private final String subType;
    private final int violations;
    private final int lag;
    private final long timestamp;
    private final String name;
    private final int ping;
    private final String type;
    private final String data;
    private final UUID uuid;

    public static Log create(PlayerData playerData, Check check, Supplier supplier, int n) {
        String string = null;
        if (check.isLogData() && supplier != null && (string = (String)supplier.get()).isEmpty()) {
            string = null;
        }
        return Log.create(playerData, check, string, n);
    }

    public int getPing() {
        return this.ping;
    }

    public Document toBson() {
        Document document = new Document();
        document.put("uuid", (Object)this.uuid);
        document.put("name", (Object)this.name);
        document.put("timestamp", (Object)this.timestamp);
        document.put("type", (Object)this.type);
        document.put("subType", (Object)this.subType);
        document.put("violations", (Object)this.violations);
        document.put("ping", (Object)this.ping);
        document.put("lag", (Object)this.lag);
        if (this.data != null) {
            document.put("data", (Object)this.data);
        }
        return document;
    }

    public Log(UUID uUID, String string, long l, String string2, String string3, int n, int n2, int n3, String string4) {
        this.uuid = uUID;
        this.name = string;
        this.timestamp = l;
        this.type = string2;
        this.subType = string3;
        this.violations = n;
        this.ping = n2;
        this.lag = n3;
        this.data = string4;
    }

    public String getData() {
        return this.data;
    }

    public String getType() {
        return this.type;
    }

    public int getViolations() {
        return this.violations;
    }

    private static Log create(PlayerData playerData, Check check, String string, int n) {
        return new Log(playerData.getUuid(), playerData.getName(), System.currentTimeMillis(), check.getType().getName(), check.getSubType(), n, playerData.getTransactionPing(), playerData.getLag(), string);
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public static Log fromBson(Document document) {
        return new Log((UUID)document.get((Object)"uuid", UUID.class), document.getString((Object)"name"), document.getLong((Object)"timestamp"), document.getString((Object)"type"), document.getString((Object)"subType"), document.getInteger((Object)"violations"), document.getInteger((Object)"ping"), document.getInteger((Object)"lag"), document.getString((Object)"data"));
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getSubType() {
        return this.subType;
    }

    public int getLag() {
        return this.lag;
    }

    public String getName() {
        return this.name;
    }
}
