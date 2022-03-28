package ir.maktab.homeservicespringboot.service;

import ir.maktab.homeservicespringboot.data.dao.interfaces.CustomerDao;
import ir.maktab.homeservicespringboot.data.dao.interfaces.SpecialistDao;
import ir.maktab.homeservicespringboot.data.dto.UserDto;
import ir.maktab.homeservicespringboot.data.entity.Customer;
import ir.maktab.homeservicespringboot.data.entity.Specialist;
import ir.maktab.homeservicespringboot.data.enums.UserState;
import ir.maktab.homeservicespringboot.exception.UserEceptions.DuplicatedEmailException;
import ir.maktab.homeservicespringboot.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final CustomerDao customerDao;
    private final SpecialistDao specialistDao;

    @Override
    public void saveUserByType(UserDto userDto, byte[] image) {

        String userType = userDto.getUserType();

        if (userType.equalsIgnoreCase("specialist")) {
            saveSpecialistByUserDto(userDto, image);

        } else if (userType.equalsIgnoreCase("customer")) {
            saveCustomerByUserDto(userDto);
        }
    }

    private void saveCustomerByUserDto(UserDto userDto) {
        int result = customerDao.findCustomerByEmail(userDto.getEmail()).size();
        if (result <= 0) {
            Customer customer = getCustomer(userDto);
            customerDao.save(customer);
        } else throw new DuplicatedEmailException();
    }

    private void saveSpecialistByUserDto(UserDto userDto, byte[] image) {
        int results = specialistDao.findSpecialistByEmail(userDto.getEmail()).size();
        if (results <= 0) {
            Specialist specialist = getSpecialist(userDto);
            specialist.setImage(image);
            specialistDao.save(specialist);
        } else throw new DuplicatedEmailException();
    }

    private Customer getCustomer(UserDto userDto) {
        return Customer.CustomerBuilder.aCustomer()
                .setName(userDto.getName())
                .setFamily(userDto.getFamily())
                .setState(UserState.WAITING_FOR_CONFIRM)
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword()).build();
    }

    private Specialist getSpecialist(UserDto userDto) {
        return Specialist.SpecialistBuilder.aSpecialist()
                .setName(userDto.getName())
                .setFamily(userDto.getFamily())
                .setEmail(userDto.getEmail())
                .setState(UserState.WAITING_FOR_CONFIRM)
                .setPassword(userDto.getPassword()).build();
    }
}
