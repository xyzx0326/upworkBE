package kodlamaio.northwind.business.abstracts;


import kodlamaio.northwind.core.utilities.results.DataResult;
import kodlamaio.northwind.core.utilities.results.Result;
import kodlamaio.northwind.entities.concretes.User;

import java.util.List;

public interface UserService {
    DataResult<List<User>> getAll();
    Result add(User user);

//    DataResult<List<String>> getContactList(String un);
//
//    Result appendToContactList(String un, String contact);
//
//    Result registerNewUserAccount(User user);
    Result loginAccount(String un, String password);


//    ChatUser getCurrentUser();
//
//    List<String> getRole() ;
//
//    public ChatUser getUserByUsername(String userName) throws ChatException;

}
