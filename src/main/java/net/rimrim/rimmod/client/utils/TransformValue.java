package net.rimrim.rimmod.client.utils;


import net.minecraft.util.Mth;

public class TransformValue {
    public float tx, ty, tz;   // translation values
    public float qx, qy, qz, qw; // rotation quaternion
    public float px, py, pz;   // position offset

    public TransformValue(float tx, float ty, float tz,
                        float qx, float qy, float qz, float qw,
                        float px, float py, float pz) {
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
        this.qx = qx;
        this.qy = qy;
        this.qz = qz;
        this.qw = qw;
        this.px = px;
        this.py = py;
        this.pz = pz;
    }

    public boolean updateQuaternion(char component, float newValue) {
        switch (component) {
            case 'x':
                if (newValue == qx) return false;
                break;
            case 'y':
                if (newValue == qy) return false;
                break;
            case 'z':
                if (newValue == qz) return false;
                break;
            case 'w':
                if (newValue == qw) return false;
                break;
            default:
                throw new IllegalArgumentException("Invalid quaternion component");
        }

        float denum = (qx * qx + qy * qy + qz * qz + qw * qw) - (component == 'x' ? qx * qx : component == 'y' ? qy * qy : component == 'z' ? qz * qz : qw * qw);
        if (denum == 0) {
            switch (component) {
                case 'x':
                    qx = Mth.sign(newValue);
                    break;
                case 'y':
                    qy = Mth.sign(newValue);
                    break;
                case 'z':
                    qz = Mth.sign(newValue);
                    break;
                case 'w':
                    qw = Mth.sign(newValue);
                    break;
            }
            return true;
        }

        double factor = Math.sqrt((1 - newValue * newValue) / denum);
        qx *= (float) factor;
        qy *= (float) factor;
        qz *= (float) factor;
        qw *= (float) factor;

        switch (component) {
            case 'x':
                qx = newValue;
                break;
            case 'y':
                qy = newValue;
                break;
            case 'z':
                qz = newValue;
                break;
            case 'w':
                qw = newValue;
                break;
        }
        return true;
    }
}

