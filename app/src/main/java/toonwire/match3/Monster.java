package toonwire.match3;

import toonwire.match3.grid_elements.NodeElement;

public interface Monster {

    public int getMaxHP();
    public int getCurrentHP();
    public void setCurrentHP();
    public int getBaseAttack();
    public int getBaseHealing();
    public int getLevel();
    public NodeElement getElement();

    public void attack(int modifier);


}
