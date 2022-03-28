package ir.maktab.homeservicespringboot.service;


import ir.maktab.homeservicespringboot.data.dao.interfaces.SpecialistDao;
import ir.maktab.homeservicespringboot.data.dao.interfaces.SubServiceDao;
import ir.maktab.homeservicespringboot.data.dto.SubServiceDto;
import ir.maktab.homeservicespringboot.data.dto.SubServiceEntityDto;
import ir.maktab.homeservicespringboot.data.dto.mappers.SubServiceEntityMapper;
import ir.maktab.homeservicespringboot.data.dto.mappers.SubServiceMapper;
import ir.maktab.homeservicespringboot.data.entity.Specialist;
import ir.maktab.homeservicespringboot.data.entity.SubService;
import ir.maktab.homeservicespringboot.exception.specialistExceptions.DuplicatedSpecialist;
import ir.maktab.homeservicespringboot.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.homeservicespringboot.exception.subServiceExceptions.DuplicatedSubServiceException;
import ir.maktab.homeservicespringboot.exception.subServiceExceptions.SubServiceNotFoundException;
import ir.maktab.homeservicespringboot.service.interfaces.SubServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class SubServiceServiceImpl implements SubServiceService {

    private final SubServiceDao subServiceDao;
    private final SpecialistDao specialistDao;

    @Override
    public void save(SubServiceDto subServiceDto) {
        int result = subServiceDao.findSubServiceBySubServiceName(subServiceDto.getSubServiceName()).size();
        if (result <= 0) {
            SubService subService = SubServiceMapper.toSubService(subServiceDto);
            subServiceDao.save(subService);
        } else throw new DuplicatedSubServiceException();
    }

    @Override
    public void delete(SubService subService) {
        subServiceDao.delete(subService);
    }

    @Override
    public void update(SubService subService) {
        subServiceDao.update(subService.getService(), subService.getSubServiceName(), subService.getExplanations(), subService.getId());
    }

    @Override
    public Iterable<SubService> findAll(int page, int size) {
        return subServiceDao.findAll(PageRequest.of(page, size));
    }

    @Override
    public SubService findById(int id) {
        Optional<SubService> optionalSubService = subServiceDao.findById(id);
        if (optionalSubService.isPresent())
            return optionalSubService.get();
        else throw new SubServiceNotFoundException();
    }

    @Override
    public SubService findByName(String name) {
        List<SubService> result = subServiceDao.findSubServiceBySubServiceName(name);
        if (result.isEmpty())
            throw new SubServiceNotFoundException();
        else return result.get(0);
    }

    @Override
    public void addSpecialistToSubService(SubService subservice, Specialist specialist) {

        subservice = subServiceDao.findById(subservice.getId()).orElse(null);
        if (subservice != null) {

            specialist = specialistDao.findSpecialistByEmail(specialist.getEmail()).get(0);
            if (specialist != null) {
                int id = specialist.getId();

                Optional<Specialist> duplicatedSpecialist = subservice.getSpecialists().stream().filter(i -> i.getId() == id).findAny();
                if (duplicatedSpecialist.isPresent()) {
                    throw new DuplicatedSpecialist("this specialist already exist in this subService!");
                } else {
                    List<Specialist> specialistList = subservice.getSpecialists();
                    specialistList.add(specialist);
                    subServiceDao.save(subservice);
                }
            } else throw new SpecialistNotFoundException();
        } else throw new SubServiceNotFoundException();
    }

    @Override
    public void removeSpecialistFromSubService(SubService subservice, Specialist specialist) {

        subservice = subServiceDao.findById(subservice.getId()).orElse(null);
        if (subservice != null) {

            specialist = specialistDao.findSpecialistByEmail(specialist.getEmail()).get(0);
            if (specialist != null) {
                int id = specialist.getId();

                Optional<Specialist> duplicatedSpecialist = subservice.getSpecialists().stream().filter(i -> i.getId() == id).findAny();
                if (duplicatedSpecialist.isPresent()) {
                    List<Specialist> specialistList = subservice.getSpecialists();
                    specialistList.remove(specialist);
                    subServiceDao.save(subservice);

                } else throw new SpecialistNotFoundException("there is no specialist in this subService!");
            } else throw new SpecialistNotFoundException();
        } else throw new SubServiceNotFoundException();

    }

    @Override
    public List<SubServiceDto> getAllSubServices() {
        List<SubService> subServices = StreamSupport
                .stream(subServiceDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return subServices.stream()
                .map(SubServiceMapper::toSubServiceDto).collect(Collectors.toList());
    }

    @Override
    public List<String> getSpecialistSubServices(String email) {
        List<Specialist> specialists = specialistDao.findSpecialistByEmail(email);
        if (!specialists.isEmpty()) {
            Specialist specialist = specialists.get(0);
            return subServiceDao.findSubServiceNameBySpecialistId(specialist.getId());
        } else throw new SpecialistNotFoundException();
    }

    @Override
    public List<SubServiceEntityDto> findSubServiceByServiceName(String serviceName) {
        Specification<SubService> specification = SubServiceDao.filterSubServiceByServiceName(serviceName);
        return subServiceDao.findAll(specification).stream()
                .map(SubServiceEntityMapper::toSubServiceEntityDto).collect(Collectors.toList());
    }


}
