package byow.Core.Items;
import byow.Core.Item;

public class Weapon implements Item {
    private String name;
    private String desc;
    private double damageBuff;
    private double accuracyBuff;
    // private double healthBuff;
    // private int durability;

    public Weapon(String name, String desc, double dBuff, double aBuff) {
        this.name = name;
        this.desc = desc;
        this.damageBuff = dBuff;
        this.accuracyBuff = aBuff;
        // this.healthBuff = hBuff;
        // this.durability = durability;
    }

    public String getName() {
        return name;
    }

    public String examine() {
        return desc;
        /* switch (durability / 5) {
            case 4:
                return desc + " It seems brand new!";
            case 3:
                return desc + " It seems to be sturdy.";
            case 2:
                return desc + " It's seen better days.";
            case 1:
                return desc + " It's starting to fall apart.";
            case 0:
                return desc + " It's barely holding together.";
            default:
                return desc;
        } */
    }

    @Override
    public double damageBuff() {
        return damageBuff;
    }

    @Override
    public double accuracyBuff() {
        return 0;
    }

    // @Override
    /* public double healthBuff() {
        return 0;
    } */


    public Item drop() {
        return null;
    }


    /* public int durability() {
        return durability;
    }
    //deal the weapons damage to the enemy, reduced according to its durability
    // max reduction factor 1.5
    //return true if the weapon breaks due to this attack
    public boolean attack(Avatar me, Enemy enemy) {
        enemy.takeDamage((int) (damage / (1.5 - durability / 50)));
        if (durability > 1) {
            durability -= 1;
            return false;
        } else {
            return true;
        }
    } */

}
