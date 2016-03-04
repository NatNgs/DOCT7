/**
 * Created by user on 04/03/16.
 */
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TotoTest {
    protected Toto toto;

    @Before
    public void setUp()
    {
        toto = new Toto();
    }

    @Test
    public void totoTest()
    {
        assertEquals(42, toto.ultimateAnwser());
        assertEquals(6, toto.fact(3));
        assertTrue(toto.isTrue(true));
        assertTrue(!toto.isTrue(false));
    }

}
