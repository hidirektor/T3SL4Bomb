package me.t3sl4.snowball.util;

import java.util.Objects;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class BlockLocation {
    private final int x;

    private final int y;

    private final int z;

    private final String world;

    public BlockLocation(int x, int y, int z, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = Objects.<String>requireNonNull(world, "world");
    }

    public BlockLocation(Location location) {
        this(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getName());
    }

    public BlockLocation(Block block) {
        this(block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public String getWorld() {
        return this.world;
    }

    public boolean isInChunk(Chunk chunk) {
        return (this.x >> 4 == chunk.getX() && this.z >> 4 == chunk.getZ() && this.world.equals(chunk.getWorld().getName()));
    }

    public boolean blockEquals(Block block) {
        return (this.x == block.getX() && this.y == block.getY() && this.z == block.getZ() && this.world.equals(block.getWorld().getName()));
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BlockLocation that = (BlockLocation)o;
        return (this.x == that.x && this.y == that.y && this.z == that.z && this.world

                .equals(that.world));
    }

    public int hashCode() {
        return Objects.hash(new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z), this.world });
    }

    public String toString() {
        return "BlockLocation{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", world='" + this.world + '\'' + '}';
    }
}
