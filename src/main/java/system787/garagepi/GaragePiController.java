package system787.garagepi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GaragePiController {

    @RequestMapping("/")
    public String index() {
        return "root";
    }

    @RequestMapping("/garagedoor")
    public String garageDoor() {
        return null;
    }
}
