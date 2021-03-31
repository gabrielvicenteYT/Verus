package me.levansj01.verus.util.pastebin.api;

import me.levansj01.verus.util.pastebin.Paste;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(value=XmlAccessType.FIELD)
class Pastes {
    @XmlElement(name="paste")
    private List<Paste> pastes;

    Pastes() {
    }

    public List<Paste> getPastes() {
        return this.pastes;
    }
}

