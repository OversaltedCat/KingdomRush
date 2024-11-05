import greenfoot.*;

public abstract class Monsters extends SuperSmoothMover
{
    protected double health;
    protected double speed;
    protected Path path;
    protected MonsterSpawner origin;

    public Monsters(MonsterSpawner origin, double health, double speed)
    {
        this.origin = origin;
        this.health = health;
        this.speed = speed;
    
        path = new Path(0.1);
        path.addPoint(525, 130);
        path.addPoint(560, 170);
        path.addPoint(580, 220);
        path.addPoint(645, 235);
        path.addPoint(690, 245);
        path.addPoint(780, 250);
        path.addPoint(845, 250);
        path.addPoint(950, 300);
        path.addPoint(955, 350);
        path.addPoint(950, 450);
        path.addPoint(925, 500);
        path.addPoint(8, 300);
        path.calculateRotationVectors();
        enableStaticRotation();
    }
    
    public void act()
    {
        PathResult start = path.move(this, speed, 3.6);
        setLocation(start.getNewX(), start.getNewY());
        setRotation(start.getRotationAngle()); 
    }

    public void takeDamage(int damage)
    {
        health -= damage;
    }

    public double getHealth()
    {
        return health;
    }
}
