package ai.tech5.t5billing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

    @RequestMapping("/version")
    public String getVersion() {

        return "TECH5 Billing Service 1.0";
    }

    @GetMapping("/transaction")
    public String receiveTransaction(String bla, String bli) {

        return bla + " " + bli;
    }
}
