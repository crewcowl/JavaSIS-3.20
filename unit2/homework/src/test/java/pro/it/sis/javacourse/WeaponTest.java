package pro.it.sis.javacourse;

import org.junit.Test;

import java.nio.file.Watchable;

import static org.junit.Assert.*;

public class WeaponTest {

    @Test
    public void testBlazingAsphalt() {

        Target testTarget = new Target();
        Weapon blazingAsphalt = new Weapon(100,50,0);
        blazingAsphalt.hit(testTarget);

        assertEquals(100, testTarget.getPhysicalDamage());
        assertEquals(50, testTarget.getFireDamage());

        Ifrit Boris = new Ifrit();
        blazingAsphalt.hit(Boris);
        assertEquals(100, Boris.getPhysicalDamage());
        assertEquals(0, Boris.getFireDamage());
    }

    @Test
    public void testYakutskNightSword() {

        Target testTarget = new Target();
        Weapon YakutskNightSword = new Weapon(100,0,50);
        YakutskNightSword.hit(testTarget);

        assertEquals(100, testTarget.getPhysicalDamage());
        assertEquals(50, testTarget.getIceDamage());

        IceGiant Ivan = new IceGiant();
        YakutskNightSword.hit(Ivan);
        assertEquals(100, Ivan.getPhysicalDamage());
        assertEquals(0, Ivan.getIceDamage());
    }

}