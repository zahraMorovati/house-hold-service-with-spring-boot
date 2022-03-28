package ir.maktab.homeservicespringboot.service;

import ir.maktab.homeservicespringboot.data.dao.interfaces.ServiceDao;
import ir.maktab.homeservicespringboot.data.dto.ServiceDto;
import ir.maktab.homeservicespringboot.data.dto.mappers.ServiceMapper;
import ir.maktab.homeservicespringboot.data.entity.Service;
import ir.maktab.homeservicespringboot.exception.serviceExceptions.DuplicatedServiceException;
import ir.maktab.homeservicespringboot.exception.serviceExceptions.ServiceNotFoundException;
import ir.maktab.homeservicespringboot.service.interfaces.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceDao serviceDao;

    @Override
    public void save(ServiceDto serviceDto) {

        int result = serviceDao.findByServiceName(serviceDto.getName()).size();
        if (result <= 0) {
            Service service = ServiceMapper.toService(serviceDto);
            serviceDao.save(service);
        } else throw new DuplicatedServiceException();
    }

    @Override
    public void update(Service service) {
        serviceDao.update(service.getServiceName(), service.getId());
    }

    @Override
    public Iterable<Service> findAll(int page, int size) {
        return serviceDao.findAll(PageRequest.of(page, size));
    }

    @Override
    public Service findById(int id) {
        Optional<Service> optionalService = serviceDao.findById(id);
        if (optionalService.isPresent())
            return optionalService.get();
        else throw new ServiceNotFoundException();
    }

    @Override
    public Service findByName(String name) {
        List<Service> result = serviceDao.findByServiceName(name);
        if(result.isEmpty())
            throw new ServiceNotFoundException();
        else return result.get(0);
    }

    @Override
    public List<ServiceDto> getServiceNames() {
        List<Service> services = StreamSupport
                .stream(serviceDao.findAll().spliterator(),false)
                .collect(Collectors.toList());
        return services.stream().map(ServiceMapper::toServiceDto).collect(Collectors.toList());

    }

    @Override
    public void delete(Service service) {
        serviceDao.delete(service);
    }


}
