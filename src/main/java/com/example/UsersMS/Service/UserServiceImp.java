package com.example.UsersMS.Service;

import com.example.UsersMS.Client.TaskClient;
import com.example.UsersMS.Entity.DTO.UserRequestDto;
import com.example.UsersMS.Entity.DTO.UserResponseDto;
import com.example.UsersMS.Entity.User;
import com.example.UsersMS.MyExceptions.AlreadyExistsException;
import com.example.UsersMS.MyExceptions.DataNotValidException;
import com.example.UsersMS.MyExceptions.EmptyEntryException;
import com.example.UsersMS.MyExceptions.NotFoundException;
import com.example.UsersMS.Repository.UserRepo;
import com.example.UsersMS.Utils.MappingProfile;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import com.example.UsersMS.Utils.Task;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService{
    @Autowired
    private final UserRepo userRepo;
    private List<User> users;
    @Autowired
    TaskClient taskClient;



    @Override
    public List<UserResponseDto> getAllUsers() throws NotFoundException {
        if (userRepo.findAll().isEmpty()) {
            throw new NotFoundException("No user found!");
        } else {
            return userRepo.findAll()
                    .stream()
                    .map(MappingProfile::mapToUserDto)
                    .toList();
        }
    }
    @Override
    public UserResponseDto createUser(UserRequestDto userDto) throws AlreadyExistsException, DataNotValidException {
        userValidation(userDto);
        var user = MappingProfile.mapToUserEntity(userDto);

        try {
            return MappingProfile.mapToUserDto(userRepo.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Email exists already!");
        }
    }
    @Override
    public Object getUserById(Long id) throws NotFoundException {
        User user = userRepo.findById(id).orElseThrow(()->new NotFoundException("User with id: "+id+" NOT found!"));
        List<Task> tasks = taskClient.findByUserId(id);
        user.setTasks(tasks);
        return MappingProfile.mapToUserDto(user);
    }
    @Override
    @Transactional
    public void deleteUser(Long id) throws NotFoundException, EmptyEntryException {
        if (id == null||id<=0){
            throw new EmptyEntryException("Empty Input!" +id);
        }
        if(!userRepo.existsById(id)){
            throw new NotFoundException("User with id: "+id+" NOT found!");
        }
        for (int i = 0; i < taskClient.findByUserId(id).size(); i++) {
            taskClient.findByUserId(id);
        }
        userRepo.deleteUserById(id);
    }
    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userDto) throws NotFoundException {
        userValidation(userDto);
        var user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("User NOT Found"));

        //Update user fields (NOT necessary to update all fields)
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if(!isEmailValid(userDto.getEmail())) throw new DataNotValidException("Email is invalid!");
        return MappingProfile.mapToUserDto(userRepo.save(user));
    }



    @Override
    public boolean isUserIdExists(Long id) {
        return userRepo.existsById(id);
    }
    public boolean isEmailValid(String email){
        String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(emailPattern).matcher(email).matches();
    }
    public void userValidation(UserRequestDto userRequestDto){
        if(userRequestDto.getLastName() == null || userRequestDto.getLastName().isEmpty()) throw new EmptyEntryException("Last name is empty");
        if(userRequestDto.getFirstName() == null || userRequestDto.getFirstName().isEmpty()) throw new EmptyEntryException("First name is empty");
        if(userRequestDto.getEmail() == null || userRequestDto.getEmail().isEmpty()) throw new EmptyEntryException("Email is empty");
        if(!isEmailValid(userRequestDto.getEmail())) throw new DataNotValidException("Email is invalid");

    }

//Explanation for future reference:
/*
Let's break down the modified regular expression "^[a-zA-Z_.]+@[a-zA-Z]+\\.[a-zA-Z]+$":
^: Asserts the start of the line.
[a-zA-Z_.]+: Matches one or more characters from the set [a-zA-Z_.]. This includes letters (both uppercase and lowercase), underscore _, and dot . characters. Only these characters are allowed before the "@" symbol.
@: Matches the "@" symbol.
[a-zA-Z]+: Matches one or more alphabets (uppercase or lowercase) for the domain name.
\\.: Matches the dot . character in the domain name. The double backslash \\ is used to escape the dot character in regular expressions.
[a-zA-Z]+: Matches one or more alphabets (uppercase or lowercase) for the top-level domain (e.g., com, net, org).
$: Asserts the end of the line.
This regular expression will match email addresses with the following criteria:
Begin with one or more alphabets (uppercase or lowercase), underscore _, or dot . characters.
Followed by @
Followed by one or more alphabets (uppercase or lowercase) for the domain name.
Followed by a dot .
Followed by one or more alphabets (uppercase or lowercase) for the top-level domain.
If the email address matches this pattern, the method returns true; otherwise, it returns false.
* */
}
