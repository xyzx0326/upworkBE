package kodlamaio.northwind.api.controllers;

import kodlamaio.northwind.business.abstracts.UserService;
import kodlamaio.northwind.core.utilities.results.DataResult;
import kodlamaio.northwind.core.utilities.results.Result;
import kodlamaio.northwind.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import toruk.UserManagement.business.abstracts.IMessageService;
//import toruk.UserManagement.business.abstracts.UserService;
//import toruk.UserManagement.core.utilities.results.DataResult;
//import toruk.UserManagement.core.utilities.results.Result;
//import toruk.UserManagement.entities.concretes.User;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    private UserService userServiceChat;

//    @Autowired
//    private IMessageService messageService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getall")
    public DataResult<List<User>> getAll(){
        return this.userService.getAll();
    }

//    @GetMapping("/getContactList")
//    public DataResult<List<String>> getContactList(String un){
//        return this.userService.getContactList(un);
//    }

//    @PostMapping("/appendToContactList")
//    public Result appendToContactList(String un, String contact){
//        return this.userService.appendToContactList(un,contact);
//    }
//
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        return this.userService.add(user);
    }
//    @PostMapping("/registerNewUserAccount")
//    public Result registerNewUserAccount(@RequestBody User user){
//        return  this.userService.registerNewUserAccount(user);
//    }

    @PostMapping("/loginAccount")
    public Result loginAccount(String un, String password){
        return  this.userService.loginAccount(un, password);
    }

//    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> addBook(@RequestBody Map<String, Object> input) throws ChatException {
//
//        ChatMessage msg = messageService.addMessage(input);
//
//        Map<String, Object> retMap = MessageUtils.mapMessage(msg);
//
//        ResponseEntity<Map<String, Object>> retValue = new ResponseEntity<Map<String, Object>>(retMap, HttpStatus.OK);
//        return retValue;
//    }
//
//    @RequestMapping(path="/findall", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> findAll() {
//        Iterable<ChatMessage> messages = messageService.getMessages();
//        List<Map<String, Object>> result = new ArrayList<>();
//        messages.forEach((t) -> {
//            result.add(MessageUtils.mapMessage(t));
//        });
//        return new ResponseEntity<List<Map<String, Object>>>(result, HttpStatus.OK);
//    }
//
//    @RequestMapping(path = "/role", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getRole() throws ChatException {
//        // dummy method
//        Map<String, Object> retMap = new HashMap<String, Object>();
//
//        retMap.put("role", userServiceChat.getRole());
//
//        ResponseEntity<Map<String, Object>> retValue = new ResponseEntity<Map<String, Object>>(retMap, HttpStatus.OK);
//        return retValue;
//    }

}
