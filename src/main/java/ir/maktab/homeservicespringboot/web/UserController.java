package ir.maktab.homeservicespringboot.web;

import ir.maktab.homeservicespringboot.config.LastViewInterceptor;
import ir.maktab.homeservicespringboot.data.dto.OrderDto;
import ir.maktab.homeservicespringboot.data.dto.UserDto;
import ir.maktab.homeservicespringboot.data.dto.mappers.UserMapper;
import ir.maktab.homeservicespringboot.data.entity.Customer;
import ir.maktab.homeservicespringboot.data.entity.Manager;
import ir.maktab.homeservicespringboot.data.entity.Specialist;
import ir.maktab.homeservicespringboot.data.enums.OrderState;
import ir.maktab.homeservicespringboot.data.validators.Validation;
import ir.maktab.homeservicespringboot.exception.UserEceptions.DuplicatedEmailException;
import ir.maktab.homeservicespringboot.exception.UserEceptions.UserNotConfirmedException;
import ir.maktab.homeservicespringboot.exception.customerExceptions.CustomerNotFoundException;
import ir.maktab.homeservicespringboot.exception.managerExceptions.ManagerNotFoundException;
import ir.maktab.homeservicespringboot.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.homeservicespringboot.service.*;

import ir.maktab.homeservicespringboot.util.SendEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final CustomerServiceImpl customerService;
    private final SpecialistServiceImpl specialistService;
    private final ManagerServiceImpl managerService;
    private final OrderServiceImpl orderService;
    private final SubServiceServiceImpl subServiceService;

    @RequestMapping("/signup")
    public String newUser(Map<String, Object> model) {
        UserDto userDto = new UserDto();
        model.put("userDto", userDto);
        return "signup";
    }

    @RequestMapping(value = "/doSignup")
    public ModelAndView register(@ModelAttribute("userDto") UserDto userDto,
                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                 HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        String errors = Validation.onRegister(userDto);
        if (errors.equals("")) {
            String verificationCode = getRandomCode();
            SendEmail.sendEmail(userDto.getEmail(), "betterHouse email verification", "your verify code is: " + verificationCode);
            modelAndView.setViewName("confirmEmailPage");
            HttpSession session = request.getSession();
            session.setAttribute("singupUserDto", userDto);
            try {
                session.setAttribute("image", image.getBytes());
            } catch (IOException e) {
                session.setAttribute("image", null);
            }
            session.setAttribute("verificationCode", verificationCode);
            return modelAndView;
        } else {
            modelAndView.addObject("signupErrors", errors);
            modelAndView.setViewName("signup");
            return modelAndView;
        }

    }

    @RequestMapping(value = "/confirmEmail")
    public ModelAndView confirmEmail(HttpServletRequest request,
                                     @RequestParam("code") String code) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        String verificationCode = (String) session.getAttribute("verificationCode");
        UserDto userDto = (UserDto) session.getAttribute("singupUserDto");
        byte[] image = (byte[]) session.getAttribute("image");
        if (verificationCode.equals(code)) {
            userService.saveUserByType(userDto, image);
            modelAndView.setViewName("userRegisteredSuccessfullyPage");
            return modelAndView;
        } else {
            modelAndView.setViewName("signup");
            modelAndView.addObject("userDto", new UserDto());
            modelAndView.addObject("emailNotValid", "your email is not valid, please try again!");
            return modelAndView;
        }

    }

    @RequestMapping("/signIn")
    public ModelAndView userLogin() {
        return new ModelAndView("loginPage");
    }

    @PostMapping("/login")
    public ModelAndView userLoginEvaluation(@RequestParam("email") String email,
                                            @RequestParam("password") String password,
                                            @RequestParam("userType") String userType,
                                            HttpServletRequest httpServletRequest) {

        ModelAndView modelAndView = new ModelAndView();
        HttpSession session;
        if (userType.equalsIgnoreCase("customer")) {
            Customer customer = customerService.findByEmailAndPassword(email, password);
            if (customer != null) {
                session = httpServletRequest.getSession();
                session.setAttribute("testSession", "hello");
                return getCustomerAccountModelAndView(modelAndView, customer, orderService, httpServletRequest.getSession());
            }
        } else if (userType.equalsIgnoreCase("specialist")) {
            Specialist specialist = specialistService.findByEmailAndPassword(email, password);
            if (specialist != null) {
                return getSpecialistModelAndView(modelAndView, specialist, orderService, subServiceService, httpServletRequest.getSession());
            }
        } else if (userType.equalsIgnoreCase("manager")) {
            Manager manager = managerService.findByEmailAndPassword(email, password);
            if (manager != null) {
                return getManagerModelAndView(modelAndView, manager, httpServletRequest.getSession());
            }
        }

        modelAndView.setViewName("/");
        modelAndView.addObject("userDto", new UserDto());
        return modelAndView;
    }

    public static ModelAndView getManagerModelAndView(ModelAndView modelAndView, Manager manager, HttpSession session) {
        UserDto managerDto = UserMapper.toUserDto(manager.getName(), manager.getFamily(), manager.getEmail());
        modelAndView.setViewName("managerAccountPage");
        session.setAttribute("userDto", managerDto);
        session.setAttribute("email", managerDto.getEmail());
        return modelAndView;
    }

    public static ModelAndView getSpecialistModelAndView(ModelAndView modelAndView,
                                                         Specialist specialist, OrderServiceImpl orderService,
                                                         SubServiceServiceImpl subServiceService, HttpSession session) {
        List<OrderDto> orderList = orderService.getSpecialistOrders(specialist.getEmail());
        UserDto specialistDto = UserMapper.toUserDto(specialist.getName(), specialist.getFamily(), specialist.getEmail(), specialist.getBalance());
        List<String> subServices = subServiceService.getSpecialistSubServices(specialist.getEmail());
        List<OrderDto> availableOrders = orderService.getSpecialistAvailableOrders(specialist.getEmail());
        List<OrderDto> specialistOrders = orderService.getSpecialistOrders(specialist.getEmail());
        modelAndView.setViewName("specialistAccountPage");
        session.setAttribute("userDto", specialistDto);
        session.setAttribute("orders", orderList);
        session.setAttribute("subServices", subServices);
        session.setAttribute("availableOrders", availableOrders);
        session.setAttribute("specialistOrders", specialistOrders);
        session.setAttribute("email", specialistDto.getEmail());
        return modelAndView;
    }

    public static ModelAndView getCustomerAccountModelAndView(ModelAndView modelAndView, Customer customer, OrderServiceImpl orderService, HttpSession session) {
        List<OrderDto> orderList = orderService.getCustomerOrders(customer.getEmail());
        List<OrderDto> paymentOrders = orderService.getCustomerOrdersByOrderState(customer.getEmail(), OrderState.DONE);
        UserDto customerDto = UserMapper.toUserDto(customer.getName(), customer.getFamily(), customer.getEmail(), customer.getBalance());
        modelAndView.setViewName("customerAccountPage");
        session.setAttribute("userDto", customerDto);
        session.setAttribute("orders", orderList);
        session.setAttribute("paymentOrders", paymentOrders);
        session.setAttribute("email", customerDto.getEmail());
        return modelAndView;
    }


    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ModelAndView loginExceptionHandler(CustomerNotFoundException ex) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", "wrong email or password!");
        return new ModelAndView("loginPage", model);
    }

    @ExceptionHandler(value = SpecialistNotFoundException.class)
    public ModelAndView loginExceptionHandler(SpecialistNotFoundException ex) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", "wrong email or password!");
        return new ModelAndView("loginPage", model);
    }

    @ExceptionHandler(value = ManagerNotFoundException.class)
    public ModelAndView loginExceptionHandler(ManagerNotFoundException ex) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", "wrong email or password!");
        return new ModelAndView("loginPage", model);
    }

    @ExceptionHandler(value = DuplicatedEmailException.class)
    public ModelAndView signupExceptionHandler(DuplicatedEmailException ex) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", "this email has been used before!");
        model.put("userDto", new UserDto());
        return new ModelAndView("signup", model);
    }

    @ExceptionHandler(value = UserNotConfirmedException.class)
    public ModelAndView handleUserNotConfirmedException() {
        ModelAndView modelAndView = new ModelAndView();
        String message = "you're not confirmed yet come back <br/> after you received our confirmation email!";
        modelAndView.addObject("UserNotConfirmed", message);
        modelAndView.setViewName("loginPage");
        return modelAndView;
    }

    private String getRandomCode() {
        Random random = new Random();
        int randomNumber = random.nextInt();
        if (randomNumber < 0) {
            return getRandomCode();
        } else {
            return String.valueOf(randomNumber).substring(0, 5);
        }
    }

}
