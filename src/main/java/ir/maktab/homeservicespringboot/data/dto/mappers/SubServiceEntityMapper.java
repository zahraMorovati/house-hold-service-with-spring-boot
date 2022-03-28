package ir.maktab.homeservicespringboot.data.dto.mappers;

import ir.maktab.homeservicespringboot.data.dto.SubServiceEntityDto;
import ir.maktab.homeservicespringboot.data.entity.SubService;

public class SubServiceEntityMapper {

    public static SubServiceEntityDto toSubServiceEntityDto(SubService subService){
        return SubServiceEntityDto.builder().setName(subService.getSubServiceName()).build();
    }
}
