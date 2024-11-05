import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import greenfoot.*;
 /**
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MonsterSpawner extends Spawner
{

    protected GreenfootImage image;
    protected int height, width;
    //protected List<<Monsters> monsterTypes;
    public static final int typePlaceholder = 0;
    //public static final int typeBarbarian = 1;
    //public static final int typeGiant = 2;
    //public static final int typeSkeleton = 3;
    
    protected int monster;
    
    
    protected World w;//Get reference to the world

    protected int spawnTimer = 0;
    protected int spawnDuration = 0;

    

    public MonsterSpawner() {
        //monsterTypes = new ArrayList<>();
    }
    
    public void addedToWorld (World w){
        this.w = w;
    }
    //public void addMonsterType(int type) {
    //    monsterTypes.add(type);
    //}
    public void act() {
        if(w != null) {
            if(w.getObjects(getClass()).size() != 0) {
                this.spawnMonster();
                //System.out.println('');
            }
        }
        
        
    }
    
    public void spawnMonster()
    {
        int selected = Greenfoot.getRandomNumber(3);
        if (selected == 0) {
            w.addObject(new Placeholder(this), getX() ,getY());
        } else if (selected == 1) {
            //w.addObject(new Barbarian(this), getX(), getY());
        } else  {
           //w.addObject(new monsterTypes);
        } 
        //Spawn the monster to the world
                
            
    }

    
    
    

    
    
}
