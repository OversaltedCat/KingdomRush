import greenfoot.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 *  The Path Class
 *  
 *  Originally created for advanced students with the Vehicle Simulation in mind. Can be used for anything that
 *  requires pre-set or dynamically generated paths (I.e. enemies patrolling, vehicles following a road, etc.)
 *  
 *  To use a Path:
 *  
 *  - First, create a Path Object, populate it with points, and then call calculateRotationVectors() to fill
 *    in the necessary rotation information. (See Patroller class for an example)
 *  - Second, store your path object somewhere that makes sense - often it will be an object assigned to the 
 *    Actor that is following it, but sometimes it might also be stored in the World, in a Lane class, etc. 
 *  - Finally, use the Path's move method. Each time you call the move method, you specify how far you want
 *    to travel along the path. The returned PathResult is the coordinate and rotation that represents moving
 *    along the path the desired distance, so you simply set the location and rotation of the Actor accordingly
 *    (again, see the example in the Patroller class)
 *  
 */
public class Path {
    private ArrayList<Point> pathPoints;
    private ArrayList<Integer> rotationAngles;
    private int currentPointIndex;
    private double resolution;

    public Path(double resolution) {
        pathPoints = new ArrayList<Point>();
        rotationAngles = new ArrayList<Integer>();
        currentPointIndex = 0;
        this.resolution = resolution;
    }

    public void addPoint(int x, int y) {
        pathPoints.add(new Point(x, y));
    }

    public void calculateRotationVectors() {
        for (int i = 0; i < pathPoints.size() - 1; i++) {
            Point currentPoint = pathPoints.get(i);
            Point nextPoint = pathPoints.get(i + 1);
            double dx = nextPoint.getX() - currentPoint.getX();
            double dy = nextPoint.getY() - currentPoint.getY();
            int numSteps = (int) (Math.sqrt(dx * dx + dy * dy) / resolution);

            for (int step = 0; step < numSteps; step++) {
                double progress = (double) step / numSteps;
                int angle = (int) Math.toDegrees(Math.atan2(dy, dx));
                rotationAngles.add(angle);
            }
        }
    }

    public PathResult move(Actor vehicle, double speed, double rotationSpeed) {
        if (pathPoints.isEmpty()) {
            // No path available
            return null;
        }

        int rotationAngle = rotationAngles.get(currentPointIndex);

        double adjustedSpeed = speed / resolution;
        double adjustedRotationSpeed = rotationSpeed / resolution;

        // Calculate the direction to the next point
        double deltaX = pathPoints.get(currentPointIndex).getX() - vehicle.getX();
        double deltaY = pathPoints.get(currentPointIndex).getY() - vehicle.getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance < adjustedSpeed) {
            // Move to the next point
            currentPointIndex = (currentPointIndex + 1) % pathPoints.size();
            deltaX = pathPoints.get(currentPointIndex).getX() - vehicle.getX();
            deltaY = pathPoints.get(currentPointIndex).getY() - vehicle.getY();
            distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        }

        // Calculate the new rotation angle
        int targetRotation = (int) Math.toDegrees(Math.atan2(deltaY, deltaX));

        // Normalize the angle to be within [0, 360] degrees
        targetRotation = (targetRotation + 360) % 360;

        // Calculate the shortest rotation change needed
        int rotationChange = targetRotation - vehicle.getRotation();
        if (Math.abs(rotationChange) > 180) {
            if (rotationChange > 0) {
                rotationChange -= 360;
            } else {
                rotationChange += 360;
            }
        }

        if (Math.abs(rotationChange) > adjustedRotationSpeed) {
            if (rotationChange > 0) {
                vehicle.setRotation(vehicle.getRotation() + (int) adjustedRotationSpeed);
            } else {
                vehicle.setRotation(vehicle.getRotation() - (int) adjustedRotationSpeed);
            }
        } else {
            vehicle.setRotation(targetRotation);
        }

        // Calculate the new position
        double newX = vehicle.getX() + (deltaX / distance) * adjustedSpeed;
        double newY = vehicle.getY() + (deltaY / distance) * adjustedSpeed;

        // Set the new position
        vehicle.setLocation((int) newX, (int) newY);

        return new PathResult(newX, newY, vehicle.getRotation());
    }

    public PathResult move(Actor vehicle, double speed) {
        if (rotationAngles.isEmpty()) {
            // No path available
            return null;
        }

        int rotationAngle = rotationAngles.get(currentPointIndex);

        double adjustedSpeed = speed / resolution;

        // Calculate the direction to the next point
        double deltaX = pathPoints.get(currentPointIndex).getX() - vehicle.getX();
        double deltaY = pathPoints.get(currentPointIndex).getY() - vehicle.getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance < adjustedSpeed) {
            // Move to the next point
            currentPointIndex = (currentPointIndex + 1) % pathPoints.size();
            deltaX = pathPoints.get(currentPointIndex).getX() - vehicle.getX();
            deltaY = pathPoints.get(currentPointIndex).getY() - vehicle.getY();
            distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        }

        // Calculate the new rotation angle
        int newRotation = (int) Math.toDegrees(Math.atan2(deltaY, deltaX));

        // Normalize the angle to be within [0, 360] degrees
        newRotation = (newRotation + 360) % 360;

        // Calculate the new position
        double newX = vehicle.getX() + (deltaX / distance) * adjustedSpeed;
        double newY = vehicle.getY() + (deltaY / distance) * adjustedSpeed;

        // Set the new rotation and position
        vehicle.setRotation(newRotation);
        vehicle.setLocation((int) newX, (int) newY);

        return new PathResult(newX, newY, newRotation);
    }
    
    public static void drawPath (GreenfootImage base, Path path){
        
    }
    
}



class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

class PathResult {
    private double newX;
    private double newY;
    private int rotationAngle;

    public PathResult(double newX, double newY, int rotationAngle) {
        this.newX = newX;
        this.newY = newY;
        this.rotationAngle = rotationAngle;
    }

    public double getNewX() {
        return newX;
    }

    public double getNewY() {
        return newY;
    }

    public int getRotationAngle() {
        return rotationAngle;
    }
}
