package pro.it.sis.javacourse;

public class Weapon {

    private int physicalDamage;

    private int fireDamage;

    private int iceDamage;

    public Weapon (int physicalD, int fireD, int iceD) {
        physicalDamage = physicalD;
        fireDamage = fireD;
        iceDamage = iceD;
    }

    void hit(Target target) {
        target.setPhysicalDamage(physicalDamage);
        target.setFireDamage(fireDamage);
        target.setIceDamage(iceDamage);
    }

}
