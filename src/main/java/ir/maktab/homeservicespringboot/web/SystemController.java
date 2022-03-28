package ir.maktab.homeservicespringboot.web;

import com.google.gson.Gson;
import ir.maktab.homeservicespringboot.service.SubServiceServiceImpl;
import ir.maktab.homeservicespringboot.service.ServiceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class SystemController {

    private final ServiceServiceImpl serviceService;
    private final SubServiceServiceImpl subServiceService;

    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    @RequestMapping(value ="/loadService" ,method = RequestMethod.GET)
    public String getTest(ModelMap modelMap) {
        modelMap.put("services", serviceService.getServiceNames());
        return "test";
    }

    @ResponseBody
    @RequestMapping(value = "/loadService/{serviceName}", method = RequestMethod.GET)
    public String loadSubServiceByService(@PathVariable("serviceName") String serviceName) {
        Gson gson = new Gson();
        return gson.toJson(subServiceService.findSubServiceByServiceName(serviceName));
    }
}
