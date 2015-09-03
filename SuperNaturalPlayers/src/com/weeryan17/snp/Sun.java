package com.weeryan17.snp;

//import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Sun {
    public static final int MID_DAY_TICKS = 6000;
    public static final int DAY_TICKS = 24000;
    public static final int HALF_DAY_TICKS = 12000;
    public static final int DAYTIME_TICKS = 14000;
    public static final int HALF_DAYTIME_TICKS = 7000;
    public static final double HALF_PI = 1.5707963267948966;
    public static final double MDTICKS_TO_ANGLE_FACTIOR = 2.243994752564138E-4;
    public static double rad;

    public static int calcMidDeltaTicks(World world) {
        int ret = (int)((world.getFullTime() - 6000) % 24000);
        if (ret >= 12000) {
            ret-=24000;
        }
        return ret;
    }

    public static double calcSunAngle(World world) {
        int mdticks = Sun.calcMidDeltaTicks(world);
        return 2.243994752564138E-4 * (double)mdticks;
    }

    public static double calcSolarRad(World world) {
        if (world.getEnvironment() != World.Environment.NORMAL) {
            return 0.0;
        }
        if (world.hasStorm()) {
            return 0.0;
        }
        double angle = Sun.calcSunAngle(world);
        double absangle = Math.abs(angle);
        if (absangle >= 1.5707963267948966) {
            return 0.0;
        }
        double a = 1.5707963267948966 - absangle;
        return Math.sin(a);
    }

    public static double calcTerrainOpacity(Block block) {
        Double opacity;
        double ret = 0.0;
        int x = block.getX();
        int z = block.getZ();
        World world = block.getWorld();
        int maxy = world.getMaxHeight() - 1;
        for (int y = block.getY(); y <= maxy && ret < 1.0; ret+=opacity.doubleValue(), ++y) {
            @SuppressWarnings("deprecation")
			int typeId = world.getBlockTypeIdAt(x, y, z);
            opacity = typeId == 0 ? Double.valueOf(0.0) : Double.valueOf(1.0);
        }
        if (ret > 1.0) {
            ret = 1.0;
        }
        return ret;
    }

    public static double calcPlayerIrradiation(Player player) {
        if (!player.isOnline()) {
            return 0.0;
        }
        if (player.isDead()) {
            return 0.0;
        }
        World world = player.getWorld();
        double ret = Sun.calcSolarRad(world);
        if (ret == 0.0) {
            return 0.0;
        }
        Block block = player.getLocation().getBlock().getRelative(0, 1, 0);
        double terrainOpacity = Sun.calcTerrainOpacity(block);
        if ((ret*=1.0 - terrainOpacity) == 0.0) {
            return 0.0;
        }
        return ret;
    }
}
