package me.levansj01.verus.compat;

public enum ServerVersion
{
    v1_11_R1, 
    v1_7_R4, 
    v1_8_R3, 
    v1_12_R1;
    
    public boolean after(final ServerVersion serverVersion) {
        return this.ordinal() > serverVersion.ordinal();
    }
    
    public boolean before(final ServerVersion serverVersion) {
        return serverVersion.ordinal() > this.ordinal();
    }
}
