package com.example.demo.Service;

import com.example.demo.Entity.DTO.UserRequestDto;
import com.example.demo.Entity.DTO.UserResponseDto;
import com.example.demo.Entity.User;
import com.example.demo.MyExceptions.NoUserFound;
import com.example.demo.MyExceptions.UserAlreadyExists;
import com.example.demo.MyExceptions.UserNotFound;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Utils.MappingProfile;
import com.example.demo.Utils.Status;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService{
    @Autowired
    private final UserRepo userRepo;


    public List<UserResponseDto> getAllUsers() throws NoUserFound {
        if (userRepo.findAll().isEmpty()) {
            throw new NoUserFound();
        } else {
            return userRepo.findAll()
                    .stream()
                    .map(MappingProfile::mapToUserDto)
                    .toList();
        }
    }

    public UserResponseDto createUser(UserRequestDto userDto) throws UserAlreadyExists {
            var user = MappingProfile.mapToUserEntity(userDto);
            return MappingProfile.mapToUserDto(userRepo.save(user));
    }

    public Object getUserById(Long id) throws UserNotFound {
        var user = userRepo.findById(id).orElseThrow(UserNotFound::new);
        return new Object() {
            public final Long id = user.getId();
            public final String fullName = user.getLastName().toUpperCase() + ", " + user.getFirstName();
            public final String email = user.getEmail();

            public final List<Object> tasks = Collections.singletonList(user.getTasks().stream().map(task -> new Object() {
                public final Long id = task.getId();
                public final String title = task.getTitle();
                public final String description = task.getDescription();
                public final Status status = task.getStatus();
                public final String dueDate = task.getDueDate().toString();
                public final String createdAt = task.getCreatedAt().toString();
                public final String updatedAt = task.getUpdatedAt().toString();
            }).toList());
        };
    }

    public UserResponseDto addUser(UserRequestDto userDto) {
        var user = MappingProfile.mapToUserEntity(userDto);
        return MappingProfile.mapToUserDto(userRepo.save(user));
    }
    @Override
    @Transactional
    public void deleteUser(Long id) throws UserNotFound {
        if (userRepo.existsById(id)) {
            userRepo.deleteUserById(id);
        }else{
            System.err.println("User Not Found!");
            throw new UserNotFound();
        }
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userDto) throws UserNotFound {
        var user = userRepo.findById(id).orElseThrow(UserNotFound::new);
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        return MappingProfile.mapToUserDto(userRepo.save(user));
    }
    @Override
    public boolean isValidEmail() {
        User user = new User();
        String regex = "^[a-zA-Z_.]+@[a-zA-Z]+\\.[a-zA-Z]+$"; // Regular expression for email format with specific characters before "@" and any domain
        Pattern pattern = Pattern.compile(regex); // Compiles the regular expression into a pattern
        Matcher matcher = pattern.matcher(user.getEmail()); // Creates a matcher that will match the given input against this pattern
        return matcher.matches(); // Returns true if the email matches the pattern, false otherwise
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
