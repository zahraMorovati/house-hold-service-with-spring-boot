package ir.maktab.homeservicespringboot.service.interfaces;

import ir.maktab.homeservicespringboot.data.dto.OrderDto;
import ir.maktab.homeservicespringboot.data.entity.Address;
import ir.maktab.homeservicespringboot.data.entity.Customer;
import ir.maktab.homeservicespringboot.data.entity.Order;
import ir.maktab.homeservicespringboot.data.entity.SubService;
import ir.maktab.homeservicespringboot.data.enums.OrderState;

import java.util.Date;
import java.util.List;


public interface OrderService {

    void save(Order order);

    void delete(Order order);

    void update(Order order);

    Iterable<Order> findAll(int page, int size);

    Order findById(int id);

    Order findByOrderCode(String orderCode);

    void addCustomerOrder(Customer customer, SubService subService, double suggestedPrice, String explanations, Address address, Date startDate);

    void selectSpecialistSuggestion(String orderCode, String suggestionCode);

    List<OrderDto> getCustomerOrders(String email);

    List<OrderDto> getSpecialistOrders(String email);

    List<OrderDto> getSpecialistAvailableOrders(String email);

    List<OrderDto> getCustomerOrdersByOrderState(String email, OrderState orderState);

    void paymentByBalance(String customerEmail,String orderCode);

    void paymentByCard(String customerEmail,String orderCode);

    List<OrderDto> orderAdvancedFilter(String startDate, String endDate, String orderState, String serviceName, String subServiceName);
}
