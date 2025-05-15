package net.masuno;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.awt.*;

public class MathUtility {
    public static Quaternionf euler(float x, float y, float z){
        //TURNS EULER ANGLES TO QUATERNIONS
        Quaternionf qx = new Quaternionf().fromAxisAngleDeg(new Vector3f(1F,0F,0F),x);
        Quaternionf qy = new Quaternionf().fromAxisAngleDeg(new Vector3f(0F,1F,0F),y);
        Quaternionf qz = new Quaternionf().fromAxisAngleDeg(new Vector3f(0F,0F,1F),z);

        return qx.mul(qy).mul(qz);
    }

    public static boolean isShielding(PlayerEntity player, PlayerEntity victim){
        if (victim.isBlocking()){
            double shield_angle = victim.getYaw();
            Vec3d attack_dir = player.getPos().subtract(victim.getPos()).normalize();
            double attack_angle = Math.atan2(attack_dir.x, attack_dir.z);
            attack_angle = Math.toDegrees(attack_angle);
            return angleDif(shield_angle,attack_angle) < 90 || angleDif(shield_angle,attack_angle) > 270;
        }
        return false;
    }

    public static double angleDif(double angle1, double angle2){
        if (angle1 < 0){
            angle1 += 360D;
        }
        if (angle2 < 0){
            angle2 += 360D;
        }
        double result = angle2 + angle1;

        if (result < 0){
            result += 360D;
        }
        if(result > 360){
            result -= 360D;
        }
        return result;
    }
}


