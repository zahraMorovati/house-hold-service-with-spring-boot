package ir.maktab.homeservicespringboot.data.dto.mappers;

import ir.maktab.homeservicespringboot.data.dto.SpecialistDto;
import ir.maktab.homeservicespringboot.data.entity.Specialist;

public class SpecialistMapper {

    public static SpecialistDto toSpecialistDto(Specialist specialist) {
        return SpecialistDto.builder()
                .setName(specialist.getName())
                .setFamily(specialist.getFamily())
                .setEmail(specialist.getEmail())
                .setState(specialist.getState().name().toLowerCase())
                .setRegistrationDate(specialist.getRegistrationDate().toString()).build();
    }
}
