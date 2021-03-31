package me.levansj01.verus.gui.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.impl.CheckGUI;
import me.levansj01.verus.gui.impl.MainGUI;
import me.levansj01.verus.gui.impl.TypeGUI;

public class GUIManager {

    private Map<CheckType, GUI> typeGuis;
    private static GUIManager instance;
    private MainGUI mainGui;
    private CheckGUI checkGui;

    public GUI getTypeGui(CheckType checkType) {
        return this.typeGuis.get(checkType);
    }

    public static GUIManager getInstance() {
        GUIManager gUIManager;
        if (instance == null) {
            gUIManager = instance = new GUIManager();
        } else {
            gUIManager = instance;
        }
        return gUIManager;
    }

    public void enable(VerusPlugin verusPlugin) {
        this.typeGuis = new ConcurrentHashMap<>();
        List<Check> list = verusPlugin.getTypeLoader().loadChecks();
        for (CheckType checkType : CheckType.values()) {
            this.typeGuis.put(checkType, new TypeGUI(checkType, list.stream()
                    .filter(check -> check.getType() == checkType).collect(Collectors.toList())));
        }
        this.mainGui = new MainGUI();
        this.checkGui = new CheckGUI();
    }

    public void disable() {
        if (this.mainGui != null) {
            this.mainGui.clear();
        }
        if (this.checkGui != null) {
            this.checkGui.clear();
        }
        if (this.typeGuis != null) {
            this.typeGuis.values().forEach(GUI::clear);
        }
        this.typeGuis = null;
    }

    public MainGUI getMainGui() {
        return this.mainGui;
    }

    public Map<CheckType, GUI> getTypeGuis() {
        return this.typeGuis;
    }

    public CheckGUI getCheckGui() {
        return this.checkGui;
    }
}
