package SpringProject.WebCommunity.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = HelloController.class)
public class ModelControllerTest {
    @Autowired
    private MockMvc mvc;

}
