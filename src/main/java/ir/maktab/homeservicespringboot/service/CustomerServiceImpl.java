package ir.maktab.homeservicespringboot.service;

import ir.maktab.homeservicespringboot.data.dao.interfaces.CustomerDao;
import ir.maktab.homeservicespringboot.data.dao.interfaces.SpecialistDao;
import ir.maktab.homeservicespringboot.data.dto.CustomerDto;
import ir.maktab.homeservicespringboot.data.dto.mappers.CustomerMapper;
import ir.maktab.homeservicespringboot.data.entity.Customer;
import ir.maktab.homeservicespringboot.data.enums.UserState;
import ir.maktab.homeservicespringboot.exception.UserEceptions.UserNotConfirmedException;
import ir.maktab.homeservicespringboot.exception.UserEceptions.WrongEmailException;
import ir.maktab.homeservicespringboot.exception.customerExceptions.CannotSaveCustomerException;
import ir.maktab.homeservicespringboot.exception.customerExceptions.CustomerNotFoundException;
import ir.maktab.homeservicespringboot.service.interfaces.CustomerService;
import ir.maktab.homeservicespringboot.util.Convert;
import ir.maktab.homeservicespringboot.util.SendEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final SpecialistDao specialistDao;


    @Override
    public void save(Customer customer) {
        customerDao.save(customer);
        if (customer.getId() < 0)
            throw new CannotSaveCustomerException();
    }

    @Override
    public void update(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    public Iterable<Customer> findAll(int page, int size) {
        return customerDao.findAll(PageRequest.of(page, size));
    }

    @Override
    public void delete(String email) {
        List<Customer> result = customerDao.findCustomerByEmail(email);
        if (!result.isEmpty()) {
            Customer customer = result.get(0);
            customerDao.delete(customer);
        } else throw new CustomerNotFoundException();
    }

    @Override
    public void changePassword(String email, String newPassword) {
        List<Customer> result = customerDao.findCustomerByEmail(email);
        if (!result.isEmpty()) {
            customerDao.updatePasswordByEmail(newPassword, email);
        } else throw new WrongEmailException();
    }

    @Override
    public Customer findById(int id) {
        Optional<Customer> optionalCustomer = customerDao.findById(id);
        if (optionalCustomer.isPresent())
            return optionalCustomer.get();
        else throw new CustomerNotFoundException();
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = StreamSupport
                .stream(customerDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return customers.stream()
                .map(CustomerMapper::toCustomerDto).collect(Collectors.toList());
    }

    @Override
    public Customer findByEmail(String email) {
        List<Customer> result = customerDao.findCustomerByEmail(email);
        if (result.size() >= 1) {
            return result.get(0);
        } else throw new CustomerNotFoundException();
    }

    @Override
    public Customer findByEmailAndPassword(String email, String password) {
        List<Customer> result = customerDao.findCustomerByEmailAndPassword(email, password);
        if (result.size() >= 1) {
            Customer customer = result.get(0);
            if(customer.getState().equals( UserState.CONFIRMED)){
               return customer;
            }else throw new UserNotConfirmedException();
        } else throw new CustomerNotFoundException();
    }

    @Override
    public List<CustomerDto> filterNotConfirmedCustomers(String name, String family, String email) {

        Specification<Customer> specification = CustomerDao.filterNotConfirmedCustomers(name, family, email);
        return customerDao.findAll(specification).stream()
                .map(CustomerMapper::toCustomerDto).collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> advancedFilterCustomers(String name, String family, String email, String startingRegistrationDate, String endingRegistrationDate, Integer minOrderNumber, Integer maxOrderNumber) {

        Date startingDate = Convert.toDate(startingRegistrationDate);
        Date endingDate = Convert.toDate(endingRegistrationDate);
        int minNumber = 0;
        int maxNumber = 0;
        if (minOrderNumber != null) minNumber = minOrderNumber;
        if (maxOrderNumber != null) maxNumber = maxOrderNumber;
        Specification<Customer> specification = CustomerDao.advancedFilter(name, family, email, startingDate, endingDate, minNumber, maxNumber);
        return customerDao.findAll(specification).stream()
                .map(CustomerMapper::toCustomerDto).collect(Collectors.toList());
    }

    @Override
    public void confirmCustomer(String email) {
        List<Customer> customerResult = customerDao.findCustomerByEmail(email);
        if (!customerResult.isEmpty()) {
            Customer customer = customerResult.get(0);
            customer.setState(UserState.CONFIRMED);
            customerDao.save(customer);
            String emailText = "hello dear " + customer.getName() + " " + customer.getFamily() + " we confirmed your account you can check betterHouse.com.";
            SendEmail.sendEmail(customer.getEmail(), "Better House", emailText);
        } else throw new CustomerNotFoundException();
    }

    @Override
    public List<CustomerDto> getAllNotConfirmedCustomers() {
        Specification<Customer> specification = CustomerDao.filterCustomersByUserState(UserState.WAITING_FOR_CONFIRM);
        return customerDao.findAll(specification).stream()
                .map(CustomerMapper::toCustomerDto).collect(Collectors.toList());
    }

    @Override
    public void updateCustomerState(UserState userState, String email) {
        List<Customer> result = customerDao.findCustomerByEmail(email);
        if (!result.isEmpty()) {
            Customer customer = result.get(0);
            customer.setState(userState);
            customerDao.save(customer);
        } else throw new WrongEmailException();
    }


}
