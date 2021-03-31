package me.levansj01.verus.check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.version.ClientVersion;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface CheckInfo {
    public boolean butterfly() default false;

    public int maxViolations() default 0x7FFFFFFF;

    public boolean heavy() default false;

    public boolean logData() default false;

    public CheckType type();

    public double minViolations() default 0.0;

    public int priority() default 1;

    public String subType();

    public boolean schematica() default false;

    public ServerVersion[] unsupportedServers() default {};

    public String friendlyName();

    public ClientVersion unsupportedAtleast() default ClientVersion.NONE;

    public ClientVersion[] unsupportedVersions() default {};

    public CheckVersion version();
}
