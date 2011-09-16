import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

import jobs.*;

public class BasicTest extends UnitTest {

    @Test
    public void runJob() {
        new Populator();
        
    }

}
