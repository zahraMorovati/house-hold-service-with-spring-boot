package ir.maktab.homeservicespringboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLayerAspect {

    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String RESET = "\033[0m";  // Text Reset


    @Pointcut(value = "execution( * ir.maktab.homeservicespringboot.service..*(..))")
    public void customerService(){}

    @Before(value = "customerService()")
    public void before(JoinPoint ip){
        System.out.println(CYAN_BRIGHT+"service -> "+ip.getSignature()+" method is loading ..."+RESET);
    }

    @After(value = "customerService()")
    public void after(JoinPoint ip){
        System.out.println(CYAN_BRIGHT+"service -> "+ip.getSignature()+" is worked successfully!"+RESET+"\n");
    }


}
