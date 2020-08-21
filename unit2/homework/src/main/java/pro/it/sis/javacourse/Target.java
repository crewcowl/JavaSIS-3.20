package pro.it.sis.javacourse;

public class Target {

    public int getPhysicalDamage() {
        return physicalDamage;
    }

    public int getFireDamage() {
        return fireDamage;
    }

    public int getIceDamage() {
        return iceDamage;
    }

    public void setPhysicalDamage(int physicalD) {

        physicalDamage += physicalD;
    }

    public void setFireDamage(int fireD) {

        fireDamage += fireD;
    }

    public void setIceDamage(int iceD) {

        iceDamage += iceD;

    }

    public void resurection () {
        physicalDamage = 0;
        fireDamage = 0;
        iceDamage = 0;
    }

    private int physicalDamage;

    private int fireDamage;

    private int iceDamage;
}

class IceGiant extends Target {

    @Override
    public void setIceDamage(int iceD) {
        System.out.printf("Resisted: %d ice damage \n", iceD);
    }

}

class Ifrit extends Target {

    @Override
    public void setFireDamage(int fireD) {
        System.out.printf("Resisted: %d fire damage \n", fireD);
    }

}