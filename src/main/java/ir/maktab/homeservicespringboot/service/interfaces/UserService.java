package ir.maktab.homeservicespringboot.service.interfaces;

import ir.maktab.homeservicespringboot.data.dto.UserDto;


public interface UserService {

    void saveUserByType(UserDto userDto, byte[] image);
}
