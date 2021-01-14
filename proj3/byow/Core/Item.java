package byow.Core;

public interface Item {

    //return the item's name
    String getName();
    //return a brief description of the item
    String examine();

    //drop the item on the floor and remove it from the inventory
    // public Item drop();

    //return the number of uses that the item has before breaking
    //public int durability();

    double damageBuff();

    double accuracyBuff();

    // public double healthBuff();
}
