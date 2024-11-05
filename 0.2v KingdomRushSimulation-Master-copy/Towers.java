import greenfoot.*;
import java.util.ArrayList;

public abstract class Towers extends Actor
{
    protected int range; 
    protected int damage; 
    protected boolean AOE;
    
    protected abstract boolean damageMonster();

    public Towers(int range, int damage, boolean AOE)
    {
        this.range = range;
        this.damage = damage;
        this.AOE = AOE;
    }

    public void act()
    {
        damageClosestMonster();
    }
    
    public void damageClosestMonster()
    {
        Monsters closestMonster = getClosestMonsterInRange();
        if (closestMonster != null) {
            closestMonster.takeDamage(damage); 
        }
    }
    
    private Monsters getClosestMonsterInRange()
    {
        ArrayList<Monsters> monsters = (ArrayList<Monsters>) getObjectsInRange(range, Monsters.class);
        Monsters closestMonster = null;
        double closestDistance = Double.MAX_VALUE;

        for (Monsters monster : monsters) {
            double distance = getDistance(monster);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestMonster = monster;
            }
        }

        return closestMonster;
    }

    private double getDistance(Actor actor)
    {
        int dx = actor.getX() - getX();
        int dy = actor.getY() - getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

}
