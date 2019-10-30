package toonwire.match3;

public interface Match3Level {

    /**
     * Fetches each wave of monsters of the level
     * @return The waves of monsters. Each row should indicate a wave of monsters.
     */
    public Monster[][] getMonsterWaves();
}
