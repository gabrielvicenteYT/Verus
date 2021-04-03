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
    public static final List<String> BETA_USERNAMES = Arrays.asList(new String[] { "UniversoCraft", "PGxPO", "Axon", "Test" });
    private List<Class<?>> classes = null;
    private static final VerusType verusType = VerusType.values()[VerusPlugin.getType()];

    public void setCheckClasses(List<Class<? extends Check>> list) {
        this.checkClasses = list;
    }

    private synchronized List<Class<? extends Check>> getCheckClasses() {
        if (this.checkClasses == null) {
            this.checkClasses = new ArrayList<Class<? extends Check>>();
            for (final Class<?> clazz : this.getClassInfos()) {
                try {
                    if (!Check.class.isAssignableFrom(clazz) || Modifier.isAbstract(clazz.getModifiers())) {
                        continue;
                    }
                    final Class<? extends Check> subclass = clazz.asSubclass(Check.class);
                    final Check check = (Check) subclass.newInstance();
                    if ((!VerusTypeLoader.verusType.getTypes().contains(check.getCheckVersion())
                            || check.getUnsupportedServers().contains(NMSManager.getInstance().getServerVersion()))) {
                        continue;
                    }
                    this.checkClasses.add(subclass);
                } catch (Throwable t) {
                    if (!isDev()) {
                        continue;
                    }
                    VerusLauncher.getPlugin().getLogger().log(Level.WARNING, "Failed to load " + clazz.getName() + ": ",
                            t);
                }
            }
        }
        return this.checkClasses;
    }

    public List<Check> loadChecks() {
        final ArrayList<Check> list = new ArrayList<Check>();
        for (final Class<? extends Check> clazz : this.getCheckClasses()) {
            try {
                list.add((Check) clazz.newInstance());
            } catch (Throwable t) {
            }
        }
        return list;
    }

    public void setClasses(List<Class<?>> list) {
        this.classes = list;
    }

    public static VerusType getVerusType() {
        return verusType;
    }

    public List<Command> getCommands() {
        final ArrayList<Command> list = new ArrayList<Command>();
        for (final Class<?> clazz : this.getClassInfos()) {
            if (Command.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                try {
                    list.add((Command) clazz.asSubclass(Command.class).newInstance());
                } catch (Throwable t) {
                }
            }
        }
        return list;
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
            final ClassLoader classLoader = this.getClass().getClassLoader();
            this.classes = new ArrayList<Class<?>>(
                    SafeReflection.getLocalField(ClassLoader.class, classLoader, "classes"));
        }
        return this.classes;
    }

    public List<BaseCommand> getBaseCommands() {
        final ArrayList<BaseCommand> list = new ArrayList<BaseCommand>();
        for (final Class<?> clazz : this.getClassInfos()) {
            if (BaseCommand.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                try {
                    list.add((BaseCommand) clazz.asSubclass(BaseCommand.class).newInstance());
                } catch (Throwable t) {
                    if (!isDev()) {
                        continue;
                    }
                    t.printStackTrace();
                }
            }
        }
        return list;
    }

    public static void loader() {
        try {
            ((Loader) Class.forName((String) ("de.xbrowniecodez.verus.customloader.VerusLoader"))
                    .asSubclass(Loader.class).newInstance()).load();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
