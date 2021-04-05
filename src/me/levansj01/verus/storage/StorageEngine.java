package me.levansj01.verus.storage;

import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.storage.database.DatabaseType;

import java.util.logging.Logger;

public class StorageEngine {
    private static StorageEngine engine;
    private DatabaseType type;
    private VerusConfiguration verusConfig;

    private Database database;

    public void stop() {
        if (this.database == null)
            return;
        this.database.stop();
    }

    public static StorageEngine getInstance() {
        StorageEngine storageEngine;
        if (engine == null) {
            storageEngine = engine = new StorageEngine();
            return storageEngine;

        }
        storageEngine = engine;
        return storageEngine;
    }

    public boolean isConnected() {
        if (this.database == null)
            return false;
        if (!this.database.isConnected())
            return false;
        boolean bl = true;
        return bl;

    }

    public VerusConfiguration getVerusConfig() {
        return this.verusConfig;
    }

    public Database getDatabase() {
        return this.database;
    }

    public DatabaseType getType() {
        return this.type;
    }

    public void stopConfig() {
        if (this.verusConfig == null)
            return;
        this.verusConfig.disable();
    }

    public void start() {
        this.verusConfig = new VerusConfiguration();
        this.verusConfig.enable();
        if (this.verusConfig.isMongoEnabled()) {
            this.type = DatabaseType.MONGO;

        } else if (this.verusConfig.isSqlEnabled()) {
            this.type = DatabaseType.SQL;
        }
        if (this.type == null) {
            Logger logger = VerusLauncher.getPlugin().getLogger();
            logger.warning(
                    "No storage method found, please enable database storage in the configuration to access important features");
            return;

        }
        this.database = this.type.create();
        VerusLauncher.getPlugin().getLogger()
                .info("Using storage method " + this.type.name() + " handled by " + this.database.getClass().getName());
        this.database.start();
        this.database.connect(this.verusConfig);
    }
}
