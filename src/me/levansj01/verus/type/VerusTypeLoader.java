package me.levansj01.verus.type;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.type.Loader;
import me.levansj01.verus.type.VerusType;
import me.levansj01.verus.util.java.SafeReflection;
import org.bukkit.command.Command;

public class VerusTypeLoader {
    private List<Class<? extends Check>> checkClasses = null;
    public static final List<String> BETA_USERNAMES;
    private List<Class<?>> classes = null;
    private static final VerusType verusType;
    private static Class<?>[] _classLoaded;

    public void setCheckClasses(List<Class<? extends Check>> list) {
        this.checkClasses = list;
    }

    private synchronized List<Class<? extends Check>> getCheckClasses() {
        if (this.checkClasses == null) {
            this.checkClasses = new ArrayList();
            for (Class clazz : this.getClassInfos()) {
                Class clazz2 = null;
                if (Check.class.isAssignableFrom(clazz) && !Modifier.isAbstract((int)clazz.getModifiers())) {
                    clazz2 = clazz.asSubclass(Check.class);
                    Check check = null;
                    try {
                        check = (Check)clazz2.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (verusType.getTypes().contains((Object)check.getCheckVersion()) && !check.getUnsupportedServers().contains((Object)NMSManager.getInstance().getServerVersion())) {
                        this.checkClasses.add((Class<? extends Check>)clazz2);
                    }
                }
                if (VerusTypeLoader.isDev()) {
                    VerusLauncher.getPlugin().getLogger().log(Level.WARNING, "Failed to load " + clazz.getName() + ": ", clazz2);
                }
            }
        }
        return this.checkClasses;
    }

    public List<Check> loadChecks() {
        ArrayList arrayList = new ArrayList();
        for (Class clazz : this.getCheckClasses()) {
            try {
                arrayList.add(clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public void setClasses(List<Class<?>> list) {
        this.classes = list;
    }

    public static VerusType getVerusType() {
        return verusType;
    }

    public List<Command> getCommands() {
        ArrayList arrayList = new ArrayList();
        for (Class clazz : this.getClassInfos()) {
            if (Command.class.isAssignableFrom(clazz) && !Modifier.isAbstract((int)clazz.getModifiers())) {
                try {
                    arrayList.add(clazz.asSubclass(Command.class).newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }

    public static boolean isDev() {
        boolean bl;
        if (verusType == VerusType.DEV) {
            bl = true;

        } else {
            bl = false;
        }
        return bl;
    }

    private List<Class<?>> getClassInfos() {
        if (this.classes == null) {
            ClassLoader classLoader = this.getClass().getClassLoader();
            Vector vector = (Vector)SafeReflection.getLocalField(ClassLoader.class, (Object)classLoader, (String)"classes");
            this.classes = new ArrayList((Collection)vector);

            _classLoaded = null;
        }
        return this.classes;
    }

    static {
        _classLoaded = null;
        verusType = VerusType.values()[VerusPlugin.getType()];
        BETA_USERNAMES = Arrays.asList(new String[]{"UniversoCraft", "PGxPO", "Axon", "Test"});
    }

    public List<BaseCommand> getBaseCommands() {
        ArrayList arrayList = new ArrayList();
        for (Class clazz : this.getClassInfos()) {
            if (BaseCommand.class.isAssignableFrom(clazz) && !Modifier.isAbstract((int)clazz.getModifiers())) {
                try {
                    arrayList.add(clazz.asSubclass(BaseCommand.class).newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        return arrayList;
    }

    public static void loader() {
        try {
            ((Loader)Class.forName((String)("de.xbrowniecodez.verus.customloader.VerusLoader")).asSubclass(Loader.class).newInstance()).load();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
