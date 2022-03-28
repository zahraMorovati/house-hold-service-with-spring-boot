package ir.maktab.homeservicespringboot.data.dto.mappers;

import ir.maktab.homeservicespringboot.data.dto.ServiceDto;
import ir.maktab.homeservicespringboot.data.entity.Service;

public class ServiceMapper {

    public static ServiceDto toServiceDto(Service service){
        return ServiceDto.builder()
                .setName(service.getServiceName()).build();
    }

    public static Service toService(ServiceDto serviceDto){
        return Service.builder()
                .setServiceName(serviceDto.getName()).build();
    }
}
