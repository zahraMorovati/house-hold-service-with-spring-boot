package ir.maktab.homeservicespringboot.service;

import ir.maktab.homeservicespringboot.data.dao.interfaces.OrderDao;
import ir.maktab.homeservicespringboot.data.dao.interfaces.SpecialistDao;
import ir.maktab.homeservicespringboot.data.dao.interfaces.SuggestionDao;
import ir.maktab.homeservicespringboot.data.dto.SuggestionDto;
import ir.maktab.homeservicespringboot.data.dto.mappers.SuggestionMapper;
import ir.maktab.homeservicespringboot.data.entity.Order;
import ir.maktab.homeservicespringboot.data.entity.Specialist;
import ir.maktab.homeservicespringboot.data.entity.Suggestion;
import ir.maktab.homeservicespringboot.data.enums.OrderState;
import ir.maktab.homeservicespringboot.exception.orderExceptions.OrderNotFoundException;
import ir.maktab.homeservicespringboot.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.homeservicespringboot.exception.suggestionExceptions.SuggestedPriceIsHigherThanBasePriceException;
import ir.maktab.homeservicespringboot.exception.suggestionExceptions.SuggestionNotFoundException;
import ir.maktab.homeservicespringboot.service.interfaces.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionDao suggestionDao;
    private final OrderDao orderDao;
    private final SpecialistDao specialistDao;

    @Override
    public Suggestion findById(int id) {
        Optional<Suggestion> optionalSuggestion = suggestionDao.findById(id);
        if (optionalSuggestion.isPresent())
            return optionalSuggestion.get();
        else throw new SuggestionNotFoundException();
    }

    @Override
    public void addSpecialistSuggestion(String orderCode, String specialistEmail, double suggestedPrice,
                                        double workHour, Date startTime) {

        List<Specialist> optionalSpecialist = specialistDao.findSpecialistByEmail(specialistEmail);
        if (!optionalSpecialist.isEmpty()) {
            List<Order> optionalOrder = orderDao.findOrderByOrderCode(orderCode);
            if (!optionalOrder.isEmpty()) {
                Order order = optionalOrder.get(0);
                if (suggestedPrice < order.getSubService().getPrice()) {

                    Specialist specialist = optionalSpecialist.get(0);
                    Suggestion suggestion = getSuggestion(specialist,suggestedPrice, workHour, startTime, order);
                    order.setOrderState(OrderState.WAITING_FOR_SPECIALIST_SELECTION);
                    orderDao.save(order);
                    suggestion.setSuggestionCode(getRandomCode(suggestionDao));
                    suggestionDao.save(suggestion);

                } else throw new SuggestedPriceIsHigherThanBasePriceException();
            } else throw new OrderNotFoundException();
        } else throw new SpecialistNotFoundException();
    }

    @Override
    public Suggestion findBySuggestionCode(String suggestionCode) {
        List<Suggestion> result = suggestionDao.findSuggestionBySuggestionCode(suggestionCode);
        if(result.isEmpty())
            throw new SuggestionNotFoundException();
        else return result.get(0);
    }

    @Override
    public List<SuggestionDto> findSuggestionByOrder(String orderCode) {
        List<Order> resultOrders = orderDao.findOrderByOrderCode(orderCode);
        if(!resultOrders.isEmpty()){
            Order order = resultOrders.get(0);
            List<Suggestion> suggestions = suggestionDao.findSuggestionByOrderAndSpecialist(order.getId());
            return suggestions
                    .stream().map(i-> SuggestionMapper.toSuggestionDto(i,order)).collect(Collectors.toList());
        }else throw new OrderNotFoundException();
    }

    private Suggestion getSuggestion(Specialist specialist,double suggestedPrice, double workHour, Date startTime, Order order) {
        return Suggestion.builder()
                .setSpecialist(specialist)
                .setSuggestedPrice(suggestedPrice)
                .setWorkHour(workHour)
                .setOrder(order)
                .setStartTime(startTime).build();
    }

    private String getRandomCode(SuggestionDao suggestionDao) {
        Random random = new Random();
        int randomNumber = random.nextInt();
        if(randomNumber<0){
            return getRandomCode(suggestionDao);
        }else {
            String code = String.valueOf(randomNumber).substring(0,5);
            int result = suggestionDao.findSuggestionBySuggestionCode(code).size();
            if (result <= 0) {
                return code;
            } else return getRandomCode(suggestionDao);
        }
    }
}
