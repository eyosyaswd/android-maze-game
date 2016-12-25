package edu.wm.cs.cs301.amazebyeyosyas.falstad;

import edu.wm.cs.cs301.amazebyeyosyas.falstad.Robot.Direction;
import edu.wm.cs.cs301.amazebyeyosyas.falstad.Robot.Turn;
import edu.wm.cs.cs301.amazebyeyosyas.generation.Distance;

/**
 * WallFollower is a class that specifies a robot driver that uses the wall follower algorithm to drive a robot.
 * 
 * Responsibilities:
 * - drives a robot to the exit of a maze
 * 
 * Collaborators:
 * - RobotDriver
 * - BasicRobot
 * - MazeApplication
 * - MazeController
 * 
 * @author Eyosyas
 *
 */
public class WallFollower implements RobotDriver {
	
	private BasicRobot robot;
	private int width;
	private int height;
	private Distance distance;
	private int pathLength;
	
	/**
	 * Wizard constructor
	 */
	public WallFollower() {
		robot = null;
		width = 0;
		height = 0;
		distance = null;
		pathLength = 0;
	}
	
	@Override
	public void setRobot(Robot r) {
		robot = (BasicRobot) r;
	}

	@Override
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void setDistance(Distance distance) {
		this.distance = distance;
		
	}

	/**
	 * 
	 * drive2Exit uses the wall follower algorithm to automatically drive a robot to the exit of a maze
	 * 
	 * This is essentially the algorithm I followed but I switch some things up to make it easier to write the code:
	 * 	while the robot hasn't reached the exit position
	 * 		if there is a wall/border in the front and there is a wall/border to the left
	 *			rotate right
	 *		if there is a wall/border in the front and there is no wall/border to the left 
	 *			rotate left
	 *		if there is no wall/border in the front and there is a wall/border to the left
	 *			move forward
	 *		if there is no wall/border in the front and there is no wall/border to the left
	 *			move forward
	 * 	once the exit position is found, turn towards the exit and walk out
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		
		while (!robot.isAtExit()) {
			//checks if the robot still has batteryLevel
			if (robot.hasStopped()) {
				robot.getMazeController().switchToLostScreen();
				return false;
			}
				
			
			if (robot.distanceToObstacle(Direction.FORWARD) == 0 && robot.distanceToObstacle(Direction.LEFT) == 0) {
				robot.rotate(Turn.RIGHT);
			} else if (robot.distanceToObstacle(Direction.FORWARD) == 0 && robot.distanceToObstacle(Direction.LEFT) != 0) {
				rotateLeftThenMove(robot);
			} else if (robot.distanceToObstacle(Direction.FORWARD) != 0 && robot.distanceToObstacle(Direction.LEFT) == 0) {
				robot.move(1, false);
			} else if (robot.distanceToObstacle(Direction.FORWARD) != 0 && robot.distanceToObstacle(Direction.LEFT) != 0) {
				rotateLeftThenMove(robot);
			}	
		}
		
		// now that the robot is at the exit position, rotate towards the exit opening and move
		if (robot.hasStopped())
			return false;
		exitMaze(robot);
		return true;
	}

	@Override
	public boolean drive2ExitOneStep() throws Exception {
        //checks if the robot still has batteryLevel
        if (robot.hasStopped()) {
            robot.getMazeController().switchToLostScreen();
            return false;
        }


        if (robot.distanceToObstacle(Direction.FORWARD) == 0 && robot.distanceToObstacle(Direction.LEFT) == 0) {
            robot.rotate(Turn.RIGHT);
        } else if (robot.distanceToObstacle(Direction.FORWARD) == 0 && robot.distanceToObstacle(Direction.LEFT) != 0) {
            rotateLeftThenMove(robot);
        } else if (robot.distanceToObstacle(Direction.FORWARD) != 0 && robot.distanceToObstacle(Direction.LEFT) == 0) {
            robot.move(1, false);
        } else if (robot.distanceToObstacle(Direction.FORWARD) != 0 && robot.distanceToObstacle(Direction.LEFT) != 0) {
            rotateLeftThenMove(robot);
        }

        // now that the robot is at the exit position, rotate towards the exit opening and move
        if (robot.isAtExit() == true) {
            exitMaze(robot);
            return true;
        }
        return false;
	}

	/**
	 * Helper method that makes the robot turn towards the exit and move forward so it can exit
	 * @param robot
	 */
	private void exitMaze(BasicRobot robot) {
		if (robot.canSeeExit(Direction.LEFT))
			rotateLeftThenMove(robot);
		else if (robot.canSeeExit(Direction.RIGHT))
			rotateRightThenMove(robot);
		else 
			robot.move(1, false);
	}

	/**
	 * Helper method that rotates a robot to the left then moves it forward
	 * @param robot
	 */
	private void rotateLeftThenMove(BasicRobot robot) {
		robot.rotate(Turn.LEFT);
		move(robot);
	}
	
	/**
	 * Helper method that rotates a robot to the right then moves it forward
	 * @param robot
	 */
	private void rotateRightThenMove(BasicRobot robot) {
		robot.rotate(Turn.RIGHT);
		move(robot);
	}

	/**
	 * Helper method that moves a robot forward only if it has enough battery
	 * @param robot
	 */
	private void move(BasicRobot robot) {
		if (!robot.hasStopped())
			robot.move(1, false);
	}
	
	/**
	 * Returns how much energy was consumed
	 */
	@Override
	public float getEnergyConsumption() {
		return 2500 - robot.getBatteryLevel();
	}

	/**
	 * Returns the path length
	 */
	@SuppressWarnings("static-access")
	@Override
	public int getPathLength() {
		return (int) robot.getPathLength();
	}


    @Override
    public BasicRobot getRobot() {
        return robot;
    }

}
