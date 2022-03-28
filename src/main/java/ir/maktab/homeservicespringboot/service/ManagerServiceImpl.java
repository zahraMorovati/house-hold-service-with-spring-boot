package ir.maktab.homeservicespringboot.service;

import ir.maktab.homeservicespringboot.data.dao.interfaces.ManagerDao;
import ir.maktab.homeservicespringboot.data.entity.Manager;
import ir.maktab.homeservicespringboot.exception.UserEceptions.WrongEmailException;
import ir.maktab.homeservicespringboot.exception.managerExceptions.CannotSaveManagerException;
import ir.maktab.homeservicespringboot.exception.managerExceptions.ManagerNotFoundException;
import ir.maktab.homeservicespringboot.service.interfaces.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerDao managerDao;

    @Override
    public void save(Manager manager){
        managerDao.save(manager);
        if(manager.getId()<0)
            throw new CannotSaveManagerException();
    }

    @Override
    public void delete(Manager manager){
        managerDao.delete(manager);
    }

    @Override
    public void update(Manager manager) {
        managerDao.updateById(manager.getName(),manager.getFamily(),manager.getEmail(),manager.getPassword(),manager.getId());
    }

    @Override
    public Iterable<Manager> findAll(int page , int size) {
        return managerDao.findAll(PageRequest.of(page,size));
    }

    @Override
    public void changePassword(String email,String newPass){
        List<Manager> result = managerDao.findManagerByEmail(email);
        if(!result.isEmpty()){
            Manager manager = result.get(0);
            manager.setPassword(newPass);
            managerDao.updatePasswordByEmail(newPass,email);
        }else throw new WrongEmailException();
    }

    @Override
    public Manager findById(int id) {
        Optional<Manager> optionalManager = managerDao.findById(id);
        if(optionalManager.isPresent())
            return optionalManager.get();
        else throw new ManagerNotFoundException();
    }

    @Override
    public List<Manager> filterByNameOrFamilyOrEmail(String name, String family, String email) {
        List<Manager> managers = managerDao.findManagerByNameOrFamilyOrEmail(name, family, email);
        if(!managers.isEmpty())
            return managers;
        else throw new ManagerNotFoundException();
    }

    @Override
    public Manager findByEmailAndPassword(String email, String password) {
        List<Manager> result = managerDao.findManagerByEmailAndPassword(email,password);
        if (result.size() >= 1) {
            return result.get(0);
        } else throw new ManagerNotFoundException();
    }

    @Override
    public Manager findByEmail(String email) {
        List<Manager> result = managerDao.findManagerByEmail(email);
        if (result.size() >= 1) {
            return result.get(0);
        } else throw new ManagerNotFoundException();
    }


}
