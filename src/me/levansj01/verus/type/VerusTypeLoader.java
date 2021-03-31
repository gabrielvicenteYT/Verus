package me.levansj01.verus.type;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
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
            this.checkClasses = new ArrayList<Class<? extends Check>>();
            Iterator<Class<?>> iterator = this.getClassInfos().iterator();
            if (iterator.hasNext()) {
                Class<? extends Check> clazz = null;
                Class<?> clazz2 = (Class<?>)iterator.next();
                if (Check.class.isAssignableFrom(clazz2) && !Modifier.isAbstract((int)clazz2.getModifiers())) {
                    clazz = clazz2.asSubclass(Check.class);
                    Check check = null;
                    try {
                        check = clazz.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (verusType.getTypes().contains((Object)check.getCheckVersion()) && !check.getUnsupportedServers().contains((Object)NMSManager.getInstance().getServerVersion())) {
                        this.checkClasses.add(clazz);
                    }
                }
                if (VerusTypeLoader.isDev()) {
                    VerusLauncher.getPlugin().getLogger().log(Level.WARNING, "Failed to load " + clazz2.getName() + ": ", clazz);
                }
            }
        }
        return this.checkClasses;
    }

    public List<Check> loadChecks() {
        Class<? extends Check> clazz = null;
        ArrayList<Check> arrayList = new ArrayList<Check>();
        Iterator<Class<? extends Check>> iterator = this.getCheckClasses().iterator();
        if (iterator.hasNext()) {
            clazz = iterator.next();
        }
        try {
            arrayList.add(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
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
        Class<? extends Command> clazz;
        List<Command> arrayList = new ArrayList<Command>();
        Iterator<Class<?>> iterator = this.getClassInfos().iterator();
        if (iterator.hasNext() && Command.class.isAssignableFrom(clazz = (Class<? extends Command>) iterator.next()) && !Modifier.isAbstract((int)clazz.getModifiers())) {
            try {
                arrayList.add(clazz.asSubclass(Command.class).newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return arrayList;
        }
        return arrayList;
    }


    public static boolean isDev() {
        return false;
    }

    private List<Class<?>> getClassInfos() {
        if (this.classes == null) {
            ClassLoader classLoader = this.getClass().getClassLoader();
            Vector vector = (Vector)SafeReflection.getLocalField(ClassLoader.class, classLoader, "classes");
            this.classes = new ArrayList((Collection)vector);
            if (_classLoaded == null) {
                throw new RuntimeException("Backup classloader method has failed, JVM unsupported.");
            }
            this.classes = Arrays.asList(_classLoaded);
            VerusLauncher.getPlugin().getLogger().log(Level.WARNING, "Using backup classloader method, JVM may not be fully supported");
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
        Class<?> clazz;
        ArrayList<BaseCommand> arrayList = new ArrayList<BaseCommand>();
        Iterator<Class<?>> iterator = this.getClassInfos().iterator();
        if (!(!iterator.hasNext() || BaseCommand.class.isAssignableFrom(clazz = (Class<?>) iterator.next()) && Modifier.isAbstract((int)clazz.getModifiers()))) {
            try {
                arrayList.add((BaseCommand) clazz.asSubclass(BaseCommand.class).newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public static void loader() {
        try {
            Class.forName(("de.xbrowniecodez.verusloader.CustomLoader")).asSubclass(Loader.class).newInstance().load();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
