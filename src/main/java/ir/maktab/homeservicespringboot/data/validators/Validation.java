package ir.maktab.homeservicespringboot.data.validators;

import ir.maktab.homeservicespringboot.data.dto.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static final String regexEmail = "^(.+)@(.+)$";
    public static final String regexPassword = "^(?=.*[0-9])(?=.*[a-z]).{4,6}$";


    public static String onRegister(UserDto userDto) {
        String message = "";
        Pattern patternPassword = Pattern.compile(regexPassword);
        Pattern patternEmail = Pattern.compile(regexEmail);
        if (userDto.getName() == null) {
            message += "you should enter your first name! <br/>";
        }else if(userDto.getName().isEmpty()){
            message += "you should enter your first name! <br/>";
        }
        if (userDto.getFamily()==null ) {
            message += "you should enter your last name! <br/>";
        }else if(userDto.getEmail().isEmpty()){
            message += "you should enter your last name! <br/>";
        }
        if (userDto.getEmail() == null && userDto.getEmail().matches(regexEmail)) {
            message += "your email is not valid! <br/>";
        }else {
            Matcher matcher = patternEmail.matcher(userDto.getEmail());
            if (!matcher.matches()) {
                message += "your email is not valid! <br/>";
            }
        }
        if (userDto.getPassword() == null) {
            message += "you should enter your password! <br/>";
        } else {
            Matcher matcher = patternPassword.matcher(userDto.getPassword());
            if (!matcher.matches()) {
                message += "your password should contain number and alphabet between 4-6! <br/>";
            }

        }
        return message;
    }

    public static String onOrderDto(OrderDto orderDto){
        String message = "";
        if (orderDto.getSubService() == null) {
            message += "please enter select subService! <br/>";
        }else if(orderDto.getSubService().isEmpty()){
            message += "please enter select subService! <br/>";
        }
        if (orderDto.getSuggestedPrice() == 0) {
            message += "please enter enter your suggested price! <br/>";
        }
        if (orderDto.getCity() == null) {
            message += "please enter enter city name! <br/>";
        }else if(orderDto.getCity().isEmpty()){
            message += "please enter enter city name! <br/>";
        }
        if (orderDto.getCityState() == null) {
            message += "please enter enter city state! <br/>";
        }else if(orderDto.getCityState().isEmpty()){
            message += "please enter enter city state! <br/>";
        }
        if (orderDto.getPlaque() == null) {
            message += "please enter plaque! <br/>";
        }else if(orderDto.getPlaque().isEmpty()){
            message += "please enter plaque! <br/>";
        }
        if (orderDto.getExplanations() == null) {
            message += "please enter explanations! <br/>";
        }else if(orderDto.getExplanations().isEmpty()){
            message += "please enter explanations! <br/>";
        }
        if (orderDto.getAddressExplanations() == null) {
            message += "please enter addressExplanations! <br/>";
        }else if(orderDto.getAddressExplanations().isEmpty()){
            message += "please enter addressExplanations! <br/>";
        }
        return message;
    }

    public static String onSuggestionDto(SuggestionDto suggestionDto){
        String message = "";
        if (suggestionDto.getWorkHour() == 0) {
            message += "please enter work hour! <br/>";
        }
        if(suggestionDto.getSuggestedPrice()==0){
            message += "please enter suggested price! <br/>";
        }
        if(suggestionDto.getStartTime() == null){
            message += "please enter start time! <br/>";
        }

        return message;
    }

    public static String onSaveSpecialistToSubService(String email,String subService){
        String message = "";
        if (email == null) {
            message += "please select specialist! <br/>";
        }else if(email.isEmpty()){
            message += "please select specialist! <br/>";
        }

        if (subService == null) {
            message += "please select subService! <br/>";
        }else if(subService.isEmpty()){
            message += "please select specialist! <br/>";
        }
        return message;
    }

    public static String onCommentDto(CommentDto commentDto){

        String message ="";
        if (commentDto.getComment() == null) {
            message += "please enter your comment! <br/>";
        }else if(commentDto.getComment().isEmpty()){
            message += "please enter your comment! <br/>";
        }
        if (commentDto.getPoint() == 0) {
            message += "please rate! <br/>";
        }
        return message;
    }

    public static String onServiceDto(ServiceDto serviceDto){

        String message ="";
        if (serviceDto.getName() == null) {
            message += "please enter service name! <br/>";
        }else if(serviceDto.getName().isEmpty()){
            message += "please enter service name! <br/>";
        }
        return message;
    }

    public static String onSubServiceDto(SubServiceDto subServiceDto){

        String message ="";
        if (subServiceDto.getSubServiceName() == null) {
            message += "please enter sub service name! <br/>";
        }else if(subServiceDto.getSubServiceName().isEmpty()){
            message += "please enter sub service name! <br/>";
        }

        if (subServiceDto.getPrice() == 0) {
            message += "please enter price! <br/>";
        }

        if (subServiceDto.getExplanations() == null) {
            message += "please enter explanations! <br/>";
        }else if(subServiceDto.getExplanations().isEmpty()){
            message += "please enter explanations! <br/>";
        }
        return message;
    }








}
