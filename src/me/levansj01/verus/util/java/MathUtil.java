package me.levansj01.verus.util.java;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import me.levansj01.verus.compat.BlockPosition;
import me.levansj01.verus.util.PacketLocation;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.java.JavaV;
import me.levansj01.verus.util.java.Vector3d;
import net.minecraft.server.v1_8_R3.MathHelper;

public class MathUtil {
    private static final Map<Thread, Map<Integer, DecimalFormat>> formatMap = new ConcurrentHashMap<Thread, Map<Integer, DecimalFormat>>();

    public static int lowestInt(int ... arrn) {
        Integer n = null;
        for (int n2 : arrn) {
            if (n != null && n2 >= n) continue;
            n = n2;
        }
        return JavaV.firstNonNull(n, 0);
    }

    public static double common(Queue<Float> queue) {
        HashSet<Float> hashSet = new HashSet<Float>();
        double d = 0.0;
        Iterator iterator = queue.iterator();
        while (iterator.hasNext()) {
            float f = ((Float)iterator.next()).floatValue();
            if (hashSet.add(Float.valueOf(f))) continue;
            d += 1.0;
        }
        return d / (double)(queue.size() + 1);
    }

    public static double deviation(Iterable<? extends Number> iterable) {
        return Math.sqrt(MathUtil.deviationSquared(iterable));
    }

    public static double getDistanceBetweenAngles(float f, float f2) {
        float f3 = Math.abs(f - f2) % 360.0f;
        if (f3 > 180.0f) {
            f3 = 360.0f - f3;
        }
        return f3;
    }

    public static double varianceSquared(Number number, Iterable<? extends Number> iterable) {
        double d = 0.0;
        int n = 0;
        for (Number number2 : iterable) {
            d += Math.pow(number2.doubleValue() - number.doubleValue(), 2.0);
            ++n;
        }
        return d == 0.0 ? 0.0 : d / (double)(n - 1);
    }

    public static double getLuckyAura(PlayerLocation playerLocation, PacketLocation packetLocation) {
        float f = playerLocation.getPitch();
        double d = playerLocation.distanceXZ(packetLocation);
        return Math.tan(Math.toRadians(f)) * d - playerLocation.getY() + packetLocation.getY();
    }

    public static float[][] getRotationFromPositions(Double[] arrdouble, Vector3d[] arrvector3d, Vector3d[] arrvector3d2) {
        float[][] arrarrf = new float[arrdouble.length * arrvector3d.length * arrvector3d2.length][];
        int n = 0;
        for (Vector3d vector3d : arrvector3d2) {
            for (Vector3d vector3d2 : arrvector3d) {
                Double[] arrdouble2 = arrdouble;
                int n2 = arrdouble2.length;
                for (int i = 0; i < n2; ++i) {
                    double d = arrdouble2[i];
                    double d2 = vector3d.getX() - vector3d2.getX();
                    double d3 = vector3d.getZ() - vector3d2.getZ();
                    double d4 = vector3d.getY() - (vector3d2.getY() + d);
                    float f = (float)Math.sqrt(d2 * d2 + d3 * d3);
                    float f2 = (float)(Math.atan2(d3, d2) * 180.0 / Math.PI) - 90.0f;
                    float f3 = (float)(-(Math.atan2(d4, f) * 180.0 / Math.PI));
                    arrarrf[n++] = new float[]{f2, f3};
                }
            }
        }
        return arrarrf;
    }

    public static double total(Iterable<? extends Number> iterable) {
        double d = 0.0;
        for (Number number : iterable) {
            d += number.doubleValue();
        }
        return d;
    }

    public static DecimalFormat getFormat(int n2) {
        return formatMap.computeIfAbsent(Thread.currentThread(), thread -> new HashMap<Integer, DecimalFormat>()).computeIfAbsent(n2, n -> new DecimalFormat("0." + MathUtil.multiply("0", n)));
    }

    public static String format(double d, int n) {
        return MathUtil.getFormat(n).format(d);
    }

    public static double range(Iterable<? extends Number> iterable) {
        return MathUtil.highest(iterable) - MathUtil.lowest(iterable);
    }

    public static double kurtosis(Iterable<? extends Number> iterable) {
        double d = 0.0;
        double d2 = 0.0;
        for (Number number : iterable) {
            d += number.doubleValue();
            d2 += 1.0;
        }
        if (d2 < 3.0) {
            return 0.0;
        }
        double d3 = d2 * (d2 + 1.0) / ((d2 - 1.0) * (d2 - 2.0) * (d2 - 3.0));
        double d4 = 3.0 * Math.pow(d2 - 1.0, 2.0) / ((d2 - 2.0) * (d2 - 3.0));
        d /= d2;
        double d5 = 0.0;
        double d6 = 0.0;
        for (Number number : iterable) {
            d5 += Math.pow(d - number.doubleValue(), 2.0);
            d6 += Math.pow(d - number.doubleValue(), 4.0);
        }
        return d3 * (d6 /= Math.pow(d5 /= d2, 2.0)) - d4;
    }

    private static String multiply(String string, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        while (n-- > 0) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static float[] getBlockRotations(PlayerLocation playerLocation, BlockPosition blockPosition) {
        double d = (double)blockPosition.getX() + 0.5 - playerLocation.getX();
        double d2 = (double)blockPosition.getY() + 0.5 - (playerLocation.getY() + (double)1.62f);
        double d3 = (double)blockPosition.getZ() + 0.5 - playerLocation.getZ();
        float f = (float)Math.sqrt(d * d + d3 * d3);
        float f2 = (float)Math.toDegrees(Math.atan2(d, d3)) - 90.0f;
        float f3 = (float)(-Math.toDegrees(Math.atan2(d2, f)));
        return new float[]{f2, f3};
    }

    public static double lowestAbs(Number ... arrnumber) {
        return MathUtil.lowestAbs(Arrays.asList(arrnumber));
    }

    public static double totalAbs(Iterable<? extends Number> iterable) {
        double d = 0.0;
        for (Number number : iterable) {
            d += Math.abs(number.doubleValue());
        }
        return d;
    }

    public static double average(Iterable<? extends Number> iterable) {
        double d = 0.0;
        int n = 0;
        for (Number number : iterable) {
            d += number.doubleValue();
            ++n;
        }
        return d / (double)n;
    }

    public static double relEntityRoundPos(double d) {
        return (double)MathHelper.floor((double)(d * 32.0)) / 32.0;
    }

    public static double average(Number ... arrnumber) {
        return MathUtil.average(Arrays.asList(arrnumber));
    }

    public static int lcd(int n, int n2) {
        return BigInteger.valueOf(n).gcd(BigInteger.valueOf(n2)).intValueExact();
    }

    public static boolean onGround(double d) {
        return d % 0.015625 == 0.0;
    }

    public static int lcd(long l, int n, int n2) {
        return (long)n2 <= l ? n : MathUtil.gcd(l, n2, n % n2);
    }

    public static double zeros(Queue<Float> queue) {
        double d = 0.0;
        Iterator iterator = queue.iterator();
        while (iterator.hasNext()) {
            float f = ((Float)iterator.next()).floatValue();
            if (f != 0.0f) continue;
            d += 1.0;
        }
        return d / (double)(queue.size() + 1);
    }

    public static boolean isScientificNotation(String string) {
        try {
            new BigDecimal(string);
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
        return string.toUpperCase().contains("E");
    }

    public static int gcd(long l, int n, int n2) {
        return (long)n2 <= l ? n : MathUtil.gcd(l, n2, n % n2);
    }

    public static double hypotSquared(double ... arrd) {
        double d = 0.0;
        for (double d2 : arrd) {
            d += Math.pow(d2, 2.0);
        }
        return d;
    }

    public static double fastSqrt(double d) {
        return Double.longBitsToDouble((Double.doubleToLongBits(d) - 0x10000000000000L >> 1) + 0x2000000000000000L);
    }

    public static double lowest(Number ... arrnumber) {
        return MathUtil.lowest(Arrays.asList(arrnumber));
    }

    public static double lowest(Iterable<? extends Number> iterable) {
        Double d = null;
        for (Number number : iterable) {
            if (d != null && !(number.doubleValue() < d)) continue;
            d = number.doubleValue();
        }
        return JavaV.firstNonNull(d, 0.0);
    }

    public static double getDistanceBetweenAngles360(double d, double d2) {
        double d3 = Math.abs(d % 360.0 - d2 % 360.0);
        d3 = Math.min(360.0 - d3, d3);
        return Math.abs(d3);
    }

    public static double hypot(double ... arrd) {
        return Math.sqrt(MathUtil.hypotSquared(arrd));
    }

    public static double deviationSquared(Iterable<? extends Number> iterable) {
        double d = 0.0;
        int n = 0;
        for (Number number : iterable) {
            d += number.doubleValue();
            ++n;
        }
        double d2 = d / (double)n;
        double d3 = 0.0;
        for (Number number : iterable) {
            d3 += Math.pow(number.doubleValue() - d2, 2.0);
        }
        return d3 == 0.0 ? 0.0 : d3 / (double)(n - 1);
    }

    public static float[] getRotationsBlockOld(double d, PlayerLocation playerLocation, BlockPosition blockPosition) {
        double d2 = (double)blockPosition.getX() - playerLocation.getX();
        double d3 = (double)blockPosition.getY() - (playerLocation.getY() + d);
        double d4 = (double)blockPosition.getZ() - playerLocation.getZ();
        float f = (float)Math.sqrt(d2 * d2 + d4 * d4);
        float f2 = (float)(Math.atan2(d4, d2) * 180.0 / Math.PI) - 90.0f;
        float f3 = (float)(-(Math.atan2(d3, f) * 180.0 / Math.PI));
        return new float[]{f2, f3};
    }

    public static int toInt(float f) {
        return (int)((double)new BigDecimal(f).setScale(5, RoundingMode.UP).floatValue() * 10000.0);
    }

    public static double lowestAbs(Iterable<? extends Number> iterable) {
        Double d = null;
        for (Number number : iterable) {
            if (d != null && !(Math.abs(number.doubleValue()) < Math.abs(d))) continue;
            d = number.doubleValue();
        }
        return JavaV.firstNonNull(d, 0.0);
    }

    public static float relEntityRoundLook(float f) {
        return MathHelper.d((float)(f * 256.0f / 360.0f));
    }

    public static float getHeight(PlayerLocation playerLocation, PlayerLocation playerLocation2) {
        return (float)playerLocation.distanceXZ(playerLocation2) * (float)Math.cos(Math.toRadians(playerLocation.getPitch()));
    }

    public static double variance(Number number, Iterable<? extends Number> iterable) {
        return Math.sqrt(MathUtil.varianceSquared(number, iterable));
    }

    public static int getLog(double d) {
        if (d == 0.0) {
            return 0;
        }
        int n = 0;
        while (d < 1.0) {
            d *= 10.0;
            ++n;
        }
        return n;
    }

    public static double positiveSmaller(Number number, Number number2) {
        return Math.abs(number.doubleValue()) < Math.abs(number2.doubleValue()) ? number.doubleValue() : number2.doubleValue();
    }

    public static float[] getRotationFromPosition(PlayerLocation playerLocation, PacketLocation packetLocation) {
        double d = packetLocation.getX() - playerLocation.getX();
        double d2 = packetLocation.getZ() - playerLocation.getZ();
        double d3 = packetLocation.getY() + 0.81 - playerLocation.getY() - 1.2;
        float f = (float)Math.sqrt(d * d + d2 * d2);
        float f2 = (float)(Math.atan2(d2, d) * 180.0 / Math.PI) - 90.0f;
        float f3 = (float)(-(Math.atan2(d3, f) * 180.0 / Math.PI));
        return new float[]{f2, f3};
    }

    public static double highestAbs(Iterable<? extends Number> iterable) {
        Double d = null;
        for (Number number : iterable) {
            if (d != null && !(Math.abs(number.doubleValue()) > Math.abs(d))) continue;
            d = number.doubleValue();
        }
        return JavaV.firstNonNull(d, 0.0);
    }

    public static double highestAbs(Number ... arrnumber) {
        return MathUtil.highestAbs(Arrays.asList(arrnumber));
    }

    public static double highest(Iterable<? extends Number> iterable) {
        Double d = null;
        for (Number number : iterable) {
            if (d != null && !(number.doubleValue() > d)) continue;
            d = number.doubleValue();
        }
        return JavaV.firstNonNull(d, 0.0);
    }

    public static double highest(Number ... arrnumber) {
        return MathUtil.highest(Arrays.asList(arrnumber));
    }

    public static Double getMinimumAngle(PacketLocation packetLocation, PlayerLocation ... arrplayerLocation) {
        Double d = null;
        for (PlayerLocation playerLocation : arrplayerLocation) {
            double d2 = packetLocation.getX() - playerLocation.getX();
            double d3 = packetLocation.getZ() - playerLocation.getZ();
            float f = (float)(Math.atan2(d3, d2) * 180.0 / Math.PI) - 90.0f;
            double d4 = MathUtil.getDistanceBetweenAngles360(playerLocation.getYaw(), f);
            if (d != null && !(d > d4)) continue;
            d = d4;
        }
        return d;
    }

    public static int gcd(int n, int n2) {
        return BigInteger.valueOf(n).gcd(BigInteger.valueOf(n2)).intValueExact();
    }
}

