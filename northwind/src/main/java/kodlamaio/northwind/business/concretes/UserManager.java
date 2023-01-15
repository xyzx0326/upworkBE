package kodlamaio.northwind.business.concretes;

import kodlamaio.northwind.business.abstracts.UserService;
import kodlamaio.northwind.core.utilities.results.*;
import kodlamaio.northwind.dataAccess.abstracts.UserDao;
import kodlamaio.northwind.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
public class UserManager implements UserService {
    private UserDao userDao;

//    @Autowired
//    private UserRepository userRepo;

    @Autowired
    public UserManager(UserDao userDao) {
        super();
        this.userDao = userDao;
    }

    @Override
    public DataResult<List<User>> getAll() {
        return new SuccessDataResult<List<User>>(this.userDao.findAll(),"Kullanıcı Listelendi");
    }
//    @Override
//    public DataResult<List<String>> getContactList(String un) {
//        User user1 =  this.userDao.findByUserName(un);
//        String[] cl = new String[]{null};
////        cl = user1.getContactList();
////        System.out.println("cl.length000000000000000000000000000000aaaa");
////        System.out.println(cl.length);
////        cl.
//        List<String> listFromArray = List.of(cl);
////        List<String> listFromArray = Arrays.asList(cl);
//        List<String> tempList = new ArrayList<String>();
//
//        return new SuccessDataResult<List<String>>(tempList,"contact list Listelendi");
////        return new SuccessResult("contact list Listelendi");
//
//    }
//
//    @Override
//    public Result appendToContactList(String un, String contact) {
//        User user1 = this.userDao.findByUserName(un);
//
////        String[] cl = user1.getContactList();
////        List<String> listFromArray = Arrays.asList(cl);
////        List<String> tempList = new ArrayList<String>(listFromArray);
////        tempList.add(contact);
////        String[] tempArray = new String[tempList.size()];
////        cl = tempList.toArray(tempArray);
//
//        return new SuccessResult("contact list Listelendi");
//    }
//
    @Override
    public Result add(User user) {
        this.userDao.save(user);
        return new SuccessResult("Kullanıcı Eklendi");
    }
//
//    @Override
//    public Result registerNewUserAccount(User user) {
//        this.userDao.save(user);
//        return new SuccessResult("Kullanıcı Eklendi");
//    }

    @Override
    public Result loginAccount(String un, String password){
        User user1 = this.userDao.findByUserName(un);
        if (user1.getPassword().equals(password)){
            return new SuccessResult("Giriş yapıldı");
        }
        else{
            return new ErrorResult("Giriş yapılamadı");
        }
    }
//
//    @Override
//    public ChatUser getCurrentUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String prin = (String) auth.getPrincipal();
//        ChatUser userByUsername = userRepo.findTopByUserName(prin);
//
//        return userByUsername;
//
//    }
//
//    public ChatUser getUserByUsername(String userName) throws ChatException {
//        ChatUser user = userRepo.findTopByUserName(userName);
//        return user;
//    }
//
//    @Override
//    public List<String> getRole(){
//        Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        Iterator<? extends GrantedAuthority> it = roles.iterator();
//        List<String> strRoles = new ArrayList<String>();
//
//        while (it.hasNext()) {
//            strRoles.add(it.next().getAuthority());
//        }
//
//        return strRoles;
//    }

}

