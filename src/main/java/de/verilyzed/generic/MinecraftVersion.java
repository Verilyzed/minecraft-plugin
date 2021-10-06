package de.verilyzed.generic;

import org.apache.commons.lang.Validate;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

public enum MinecraftVersion {

    /**
     * This constant represents Minecraft (Java Edition) Version 1.17
     * (The "Caves and Cliffs: Part I" Update)
     *
     */
    MINECRAFT_1_17(17, "1.17.x"),

    /**
     * This constant represents an exceptional state in which we were unable
     * to identify the Minecraft Version we are using
     */
    UNKNOWN("Unknown", true),

    /**
     * This is a very special state that represents the environment being a Unit
     * Test and not an actual running Minecraft Server.
     */
    UNIT_TEST("Unit Test Environment", true);

    private final String name;
    private final boolean virtual;
    private final int majorVersion;

    /**
     * This constructs a new {@link MinecraftVersion} with the given name.
     * This constructor forces the {@link MinecraftVersion} to be real.
     * It must be a real version of Minecraft.
     *
     * @param majorVersion
     *            The major version of minecraft as an {@link Integer}
     * @param name
     *            The display name of this {@link MinecraftVersion}
     */
    MinecraftVersion(int majorVersion, @NotNull String name) {
        this.name = name;
        this.majorVersion = majorVersion;
        this.virtual = false;
    }

    /**
     * This constructs a new {@link MinecraftVersion} with the given name.
     * A virtual {@link MinecraftVersion} (unknown or unit test) is not an actual
     * version of Minecraft but rather a state of the {@link Server} software.
     *
     * @param name
     *            The display name of this {@link MinecraftVersion}
     * @param virtual
     *            Whether this {@link MinecraftVersion} is virtual
     */
    MinecraftVersion(@NotNull String name, boolean virtual) {
        this.name = name;
        this.majorVersion = 0;
        this.virtual = virtual;
    }

    /**
     * This returns the name of this {@link MinecraftVersion} in a readable format.
     *
     * @return The name of this {@link MinecraftVersion}
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * This returns whether this {@link MinecraftVersion} is virtual or not.
     * A virtual {@link MinecraftVersion} does not actually exist but is rather
     * a state of the {@link Server} software used.
     * Virtual {@link MinecraftVersion MinecraftVersions} include "UNKNOWN" and
     * "UNIT TEST".
     *
     * @return Whether this {@link MinecraftVersion} is virtual or not
     */
    public boolean isVirtual() {
        return virtual;
    }

    public boolean isMinecraftVersion(int minecraftVersion) {
        return !isVirtual() && this.majorVersion == minecraftVersion;
    }

    /**
     * This method checks whether this {@link MinecraftVersion} is newer or equal to
     * the given {@link MinecraftVersion},
     *
     * An unknown version will default to {@literal false}.
     *
     * @param version
     *            The {@link MinecraftVersion} to compare
     *
     * @return Whether this {@link MinecraftVersion} is newer or equal to the given {@link MinecraftVersion}
     */
    public boolean isAtLeast(@NotNull MinecraftVersion version) {
        Validate.notNull(version, "A Minecraft version cannot be null!");

        if (this == UNKNOWN) {
            return false;
        }

        return this.ordinal() >= version.ordinal();
    }

    /**
     * This checks whether this {@link MinecraftVersion} is older than the specified {@link MinecraftVersion}.
     *
     * An unknown version will default to {@literal true}.
     *
     * @param version
     *            The {@link MinecraftVersion} to compare
     *
     * @return Whether this {@link MinecraftVersion} is older than the given one
     */
    public boolean isBefore(@NotNull MinecraftVersion version) {
        Validate.notNull(version, "A Minecraft version cannot be null!");

        if (this == UNKNOWN) {
            return true;
        }

        return version.ordinal() > this.ordinal();
    }

}