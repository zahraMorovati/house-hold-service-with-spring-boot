package ir.maktab.homeservicespringboot.data.dao.interfaces;


import ir.maktab.homeservicespringboot.data.entity.Customer;
import ir.maktab.homeservicespringboot.data.enums.UserState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface CustomerDao extends PagingAndSortingRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {


    static Specification<Customer> filterNotConfirmedCustomers(String name, String family, String email) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate condition1 = criteriaBuilder.like(root.get("name"), name);
            Predicate condition2 = criteriaBuilder.like(root.get("family"), family);
            Predicate condition3 = criteriaBuilder.like(root.get("email"), email);
            Predicate conditionState = criteriaBuilder.equal(root.get("state"), UserState.WAITING_FOR_CONFIRM);
            return criteriaBuilder.or(condition1, condition2, condition3,conditionState);
        };
    }

    static Specification<Customer> filterCustomersByUserState(UserState userState) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            CriteriaQuery<Customer> resultCriteria = criteriaBuilder.createQuery(Customer.class);
            List<Predicate> filterPredicates = new ArrayList<>();

            if (userState != null) {
                filterPredicates.add(criteriaBuilder.equal(root.get("state"), userState));
            }
            resultCriteria.select(root).where(filterPredicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };
    }

    static Specification<Customer> advancedFilter(String name, String family, String email,
                                                  Date startingRegistrationDate,
                                                  Date endingRegistrationDate,
                                                  int minOrderNumber, int maxOrderNumber) {

        return (root, criteriaQuery, criteriaBuilder) -> {

            CriteriaQuery<Customer> resultCriteria = criteriaBuilder.createQuery(Customer.class);
            List<Predicate> filterPredicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                filterPredicates.add(criteriaBuilder.like(root.get("name"), name));
            } else if (family != null && !family.isEmpty()) {
                filterPredicates.add(criteriaBuilder.like(root.get("family"), family));
            } else if (email != null && !email.isEmpty()) {
                filterPredicates.add(criteriaBuilder.like(root.get("email"), email));
            } else if (startingRegistrationDate != null && endingRegistrationDate != null) {
                filterPredicates.add(criteriaBuilder.between(root.get("RegistrationDate"), startingRegistrationDate, endingRegistrationDate));
            } else if (maxOrderNumber > 1 && minOrderNumber >= 0) {
                filterPredicates.add(criteriaBuilder.between(criteriaBuilder.size(root.get("orders")), minOrderNumber, maxOrderNumber));
            }
            resultCriteria.select(root).where(filterPredicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };
    }

    List<Customer> findCustomerByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update Customer c set c.name=:name,c.family=:family,c.email=:email,c.password=:password,c.balance=:balance where c.id=:id")
    void update(@Param("name") String name, @Param("family") String family, @Param("email") String email, @Param("password") String password, @Param("balance") double balance, @Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "update Customer c set c.password=:newPassword where c.email=:email")
    void updatePasswordByEmail(@Param("newPassword") String newPassword, @Param("email") String email);

    List<Customer> findCustomerByEmailAndPassword(String email, String password);


}
