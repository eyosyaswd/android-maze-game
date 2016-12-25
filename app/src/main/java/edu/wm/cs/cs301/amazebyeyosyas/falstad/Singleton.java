package edu.wm.cs.cs301.amazebyeyosyas.falstad;

import edu.wm.cs.cs301.amazebyeyosyas.generation.MazeConfiguration;
import edu.wm.cs.cs301.amazebyeyosyas.generation.MazeContainer;
import edu.wm.cs.cs301.amazebyeyosyas.generation.Order;

/**
 * Created by Eyosyas on 11/30/2016.
 */

public class Singleton {

    private MazeController controller = new MazeController();
    private MazeConfiguration mazeConfiguration = new MazeContainer();

    private static Singleton instance = null;

    private Singleton() {
        controller = new MazeController();
    }

    public static Singleton getInstance() {
        if (instance == null) instance = new Singleton();
        return instance;
    }

    public void updateDriver(RobotDriver driver) {
        controller.updateDriver(driver);
    }

    public void updateBuilder(Order.Builder builder) {
        controller.updateBuilder(builder);
    }

    public void updateSkillLevel(int skillLevel) {
        controller.setSkillLevel(skillLevel);
    }

    public MazeController getMazeController() {
        return controller;
    }

    public void killInstance() {
        instance = null;
    }

}
