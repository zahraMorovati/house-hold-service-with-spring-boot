package ir.maktab.homeservicespringboot.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private String orderCode;
    private String  subService;
    private double suggestedPrice;
    private String explanations;
    private Date registrationDate;
    private String startDate;
    private String address;
    private String orderState;
    private String specialist;
    private String customer;
    private String comment;
    private double point;
    private String city;
    private String cityState; //استان
    private String plaque;
    private String addressExplanations;
    private String specialistEmail;

}
