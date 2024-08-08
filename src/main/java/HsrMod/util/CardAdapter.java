package HsrMod.util;

/**
 * @author darkbloodevil
 * @date 2024/8/8 10:20
 * @description
 */
public class CardAdapter {


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        is_cost_modified = true;
        this.cost = cost;
    }

    public int getCostForTurn() {
        return costForTurn;
    }

    public void setCostForTurn(int costForTurn) {
        is_costForTurn_modified = true;
        this.costForTurn = costForTurn;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    private int damage = 0;
    private int block = 0;
    private int cost = 0;
    public boolean is_cost_modified = false;
    private int costForTurn = 0;
    public boolean is_costForTurn_modified = false;
    private int magicNumber;

}
