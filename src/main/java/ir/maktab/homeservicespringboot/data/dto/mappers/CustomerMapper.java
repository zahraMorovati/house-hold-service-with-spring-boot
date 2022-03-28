package ir.maktab.homeservicespringboot.data.dto.mappers;

import ir.maktab.homeservicespringboot.data.dto.CustomerDto;
import ir.maktab.homeservicespringboot.data.entity.Customer;

public class CustomerMapper {

    public  static CustomerDto toCustomerDto(Customer customer){
        return CustomerDto.builder()
                .setName(customer.getName())
                .setFamily(customer.getFamily())
                .setEmail(customer.getEmail())
                .setState(customer.getState().name().toLowerCase())
                .setRegistrationDate(customer.getRegistrationDate().toString()).build();
    }
}
