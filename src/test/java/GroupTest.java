import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GroupTest {
    Group g;

    @Before
    public void setUp() {
        g = new Group("G1");
    }

    @Test(timeout = 50)
    public void TestGetGroupName() {
        assertEquals("G1", g.getGroupName());
        g.setGroupName("Diff Group");
        assertEquals("Diff Group", g.getGroupName());
    }

}
