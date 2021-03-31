package me.levansj01.verus.storage.database;

import java.util.function.Supplier;
import me.levansj01.verus.storage.database.impl.MongoDB;
import me.levansj01.verus.storage.database.impl.SQLDB;

public enum DatabaseType {
    MONGO(MongoDB::new), SQL(SQLDB::new);
    private final Supplier supplier;

    private DatabaseType(Supplier supplier) {
        this.supplier = supplier;
    }

    public Database create() {
        return (Database) this.supplier.get();
    }
}
