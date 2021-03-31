package me.levansj01.verus.util.pastebin.api;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import me.levansj01.verus.util.pastebin.Paste;
import me.levansj01.verus.util.pastebin.PasteExpiration;

class PasteInfoUpdater {

    static BasePaste updateDate(BasePaste basePaste) {
        Object object;
        if (basePaste == null) {
            return null;
        }
        if (basePaste instanceof Paste) {
            object = (Paste)basePaste;
            if (basePaste.getExpireDateTimestamp() > 0L) {
                ((Paste)object).setExpiration(PasteExpiration.getBySeconds(basePaste.getExpireDateTimestamp() - basePaste.getDateTimestamp()));
            } else {
                ((Paste)object).setExpiration(PasteExpiration.NEVER);
            }
        }
        if (basePaste.getDateTimestamp() > 0L) {
            object = new Date(basePaste.getDateTimestamp() * 1000L).toInstant().atZone(ZoneId.systemDefault());
            basePaste.setLocalPasteDate((ZonedDateTime)object);
            basePaste.setPasteDate(((ZonedDateTime)object).toLocalDateTime());
            if (basePaste.getExpireDateTimestamp() > 0L) {
                ZonedDateTime zonedDateTime = new Date(basePaste.getExpireDateTimestamp() * 1000L).toInstant().atZone(ZoneId.systemDefault());
                basePaste.setLocalExpirationDate(zonedDateTime);
                basePaste.setExpirationDate(zonedDateTime.toLocalDateTime());
            }
        }
        return basePaste;
    }

    static List<BasePaste> updateDate(List<BasePaste> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        list.forEach(PasteInfoUpdater::updateDate);
        return list;
    }

    private PasteInfoUpdater() {
    }
}

