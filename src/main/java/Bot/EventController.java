package Bot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class EventController {

    @Autowired
    private EventManager eventManager;

    @PostMapping("")
    public String getMessage(@RequestBody String json) {
        return eventManager.manageEvent(json);
    }
}
