package ir.maktab.homeservicespringboot.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "set")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceDto {

    private String name;


}
