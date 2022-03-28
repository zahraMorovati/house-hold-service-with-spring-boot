package ir.maktab.homeservicespringboot.data.dto.mappers;

import ir.maktab.homeservicespringboot.data.dto.AddressDto;
import ir.maktab.homeservicespringboot.data.entity.Address;

public class AddressMapper {

    public static Address toAddress(AddressDto addressDto){
        return Address.builder()
                .setCity(addressDto.getCity())
                .setCityState(addressDto.getCityState())
                .setPlaque(addressDto.getPlaque())
                .setExplanations(addressDto.getExplanations()).build();
    }

    public static AddressDto addressDto(Address address){
        return AddressDto.builder()
                .setCity(address.getCity())
                .setCityState(address.getCityState())
                .setPlaque(address.getPlaque())
                .setExplanations(address.getExplanations()).build();
    }
}
