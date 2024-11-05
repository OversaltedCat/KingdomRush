import greenfoot.*;

/**
 * Write a description of class ArrowTower here.
 * 
 * @version (a version number or a date)
 */
public class ArrowTower extends Towers
{
    private int range;
    private int damage;
    private boolean AOE;

    public ArrowTower( int range, int damage, boolean AOE)
    {
        super(range, damage, AOE);
    }
    
    public boolean damageMonster(){
        return false;
    }
    
    int x;
}
