package me.levansj01.verus.storage.database;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.util.bson.Document;

public class CheckValues {
    private boolean alert = true;
    private int maxViolations = Integer.MAX_VALUE;
    private final String checkId;
    private boolean punish = true;

    public boolean isPunish() {
        return this.punish;
    }

    public Document toBson() {
        Document document = new Document("_id", this.checkId);
        document.put("punish", this.punish);
        document.put("alert", this.alert);
        document.put("maxViolations", this.maxViolations);
        return document;
    }

    public void setPunish(boolean bl) {
        this.punish = bl;
    }

    public void setMaxViolations(int n) {
        this.maxViolations = n;
    }

    public boolean isAlert() {
        return this.alert;
    }

    public void setAlert(boolean bl) {
        this.alert = bl;
    }

    public static CheckValues fromBson(Document document) {
        CheckValues checkValues = new CheckValues(document.getString("_id"));
        checkValues.setPunish(document.getBoolean("punish"));
        checkValues.setAlert(document.getBoolean("alert"));
        checkValues.setMaxViolations(document.getInteger("maxViolations"));
        return checkValues;
    }

    public CheckValues(String string) {
        this.checkId = string;
    }

    public String getCheckId() {
        return this.checkId;
    }

    public int getMaxViolations() {
        return this.maxViolations;
    }

    public CheckValues(Check check) {
        this.checkId = check.identifier();
        this.maxViolations = check.getMaxViolation();
    }
}
