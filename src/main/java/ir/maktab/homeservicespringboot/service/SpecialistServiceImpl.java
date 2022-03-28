package ir.maktab.homeservicespringboot.service;

import ir.maktab.homeservicespringboot.data.dao.interfaces.SpecialistDao;
import ir.maktab.homeservicespringboot.data.dto.SpecialistDto;
import ir.maktab.homeservicespringboot.data.dto.mappers.SpecialistMapper;
import ir.maktab.homeservicespringboot.data.entity.Specialist;
import ir.maktab.homeservicespringboot.data.enums.UserState;
import ir.maktab.homeservicespringboot.exception.UserEceptions.UserNotConfirmedException;
import ir.maktab.homeservicespringboot.exception.UserEceptions.WrongEmailException;
import ir.maktab.homeservicespringboot.exception.customerExceptions.CustomerNotFoundException;
import ir.maktab.homeservicespringboot.exception.specialistExceptions.CannotSaveSpecialistException;
import ir.maktab.homeservicespringboot.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.homeservicespringboot.service.interfaces.SpecialistService;
import ir.maktab.homeservicespringboot.util.Convert;
import ir.maktab.homeservicespringboot.util.SendEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistDao specialistDao;

    @Override
    public void save(Specialist specialist) {
        specialistDao.save(specialist);
        if (specialist.getId() < 0) throw new CannotSaveSpecialistException();
    }

    @Override
    public void delete(String email) {
        List<Specialist> result = specialistDao.findSpecialistByEmail(email);
        if (!result.isEmpty()) {
            Specialist specialist = result.get(0);
            specialistDao.delete(specialist);
        } else throw new SpecialistNotFoundException();
    }

    @Override
    public void update(Specialist specialist) {
        specialistDao.save(specialist);
    }

    @Override
    public Iterable<Specialist> findAll(int page, int size) {
        return specialistDao.findAll(PageRequest.of(page, size));
    }

    @Override
    public void changePassword(String email, String newPass) {
        List<Specialist> result = specialistDao.findSpecialistByEmail(email);
        if (!result.isEmpty()) {
            specialistDao.updatePasswordByEmail(email, newPass);
        } else throw new WrongEmailException();
    }

    @Override
    public Specialist findById(int id) {
        Optional<Specialist> optionalSpecialist = specialistDao.findById(id);
        if (optionalSpecialist.isPresent()) return optionalSpecialist.get();
        else throw new SpecialistNotFoundException();
    }

    @Override
    public void uploadSpecialistImage(Specialist specialist, CommonsMultipartFile image) throws IOException {
        specialistDao.updateSpecialistImage(image.getBytes(), specialist.getEmail());
    }

    @Override
    public List<SpecialistDto> getAllSpecialists() {
        List<Specialist> specialists = StreamSupport.stream(specialistDao.findAll().spliterator(), false).collect(Collectors.toList());
        return specialists.stream().map(SpecialistMapper::toSpecialistDto).collect(Collectors.toList());

    }

    @Override
    public Specialist findByEmail(String email) {
        List<Specialist> result = specialistDao.findSpecialistByEmail(email);
        if (result.size() >= 1) {
            return result.get(0);
        } else throw new SpecialistNotFoundException();
    }

    @Override
    public Specialist findByEmailAndPassword(String email, String password) {
        List<Specialist> result = specialistDao.findSpecialistByEmailAndPassword(email, password);
        if (!result.isEmpty()) {
            Specialist specialist = result.get(0);
            if (specialist.getState().equals(UserState.CONFIRMED)) {
                return specialist;
            } else throw new UserNotConfirmedException();
        } else throw new SpecialistNotFoundException();
    }

    @Override
    public List<SpecialistDto> filterNotConfirmedSpecialists(String name, String family, String email) {
        Specification<Specialist> specification = SpecialistDao.filterNotConfirmedSpecialists(name, family, email);
        return specialistDao.findAll(specification).stream().map(SpecialistMapper::toSpecialistDto).collect(Collectors.toList());
    }

    @Override
    public List<SpecialistDto> advancedFilterSpecialists(String name, String family, String email, String startingRegistrationDate, String endingRegistrationDate, Integer minOrderNumber, Integer maxOrderNumber) {

        Date startingDate = Convert.toDate(startingRegistrationDate);
        Date endingDate = Convert.toDate(endingRegistrationDate);

        int minNumber = 0;
        int maxNumber = 0;
        if (minOrderNumber != null) minNumber = minOrderNumber;
        if (maxOrderNumber != null) maxNumber = maxOrderNumber;

        Specification<Specialist> specification = SpecialistDao.advancedFilter(name, family, email, startingDate, endingDate, minNumber, maxNumber);
        return specialistDao.findAll(specification).stream().map(SpecialistMapper::toSpecialistDto).collect(Collectors.toList());
    }

    @Override
    public void confirmSpecialist(String email) {
        List<Specialist> specialistResult = specialistDao.findSpecialistByEmail(email);
        if (!specialistResult.isEmpty()) {
            Specialist specialist = specialistResult.get(0);
            specialist.setState(UserState.CONFIRMED);
            specialistDao.save(specialist);
            String emailText = "hello dear " + specialist.getName() + " " + specialist.getFamily() + " we confirmed your account you can check betterHouse.com for more details.";
            SendEmail.sendEmail(specialist.getEmail(), "Better House", emailText);
        } else throw new CustomerNotFoundException();
    }

    @Override
    public List<SpecialistDto> getAllNotConfirmedSpecialist() {
        Specification<Specialist> specification = SpecialistDao.filterSpecialistsByUserState(UserState.WAITING_FOR_CONFIRM);
        return specialistDao.findAll(specification).stream().map(SpecialistMapper::toSpecialistDto).collect(Collectors.toList());
    }

    @Override
    public void updateSpecialistState(UserState userState, String email) {
        List<Specialist> result = specialistDao.findSpecialistByEmail(email);
        if (!result.isEmpty()) {
            Specialist specialist = result.get(0);
            specialist.setState(userState);
            specialistDao.save(specialist);
        } else throw new WrongEmailException();
    }


}
