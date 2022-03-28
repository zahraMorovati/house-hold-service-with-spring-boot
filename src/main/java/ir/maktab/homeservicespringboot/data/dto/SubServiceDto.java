package ir.maktab.homeservicespringboot.data.dto;

import ir.maktab.homeservicespringboot.data.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubServiceDto {

    private Service service;
    private String subServiceName;
    private double price;
    private String explanations;
}
