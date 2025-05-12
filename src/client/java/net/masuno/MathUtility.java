package net.masuno;

import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class MathUtility {
    public static Quaternionf euler(float x, float y, float z){

        Quaternionf qx = new Quaternionf().fromAxisAngleDeg(new Vector3f(1F,0F,0F),x);
        Quaternionf qy = new Quaternionf().fromAxisAngleDeg(new Vector3f(0F,1F,0F),y);
        Quaternionf qz = new Quaternionf().fromAxisAngleDeg(new Vector3f(0F,0F,1F),z);

        return qx.mul(qy).mul(qz);
    }
}
