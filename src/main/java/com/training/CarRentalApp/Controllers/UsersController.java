import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.training.CarRentalApp.Models.Users;
import com.training.CarRentalApp.Repositories.UsersRepository;


@RestController
public class UsersController
{


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody Users user)
    {
        Map<String, String> response = new HashMap<>();
        List<Users> list = userRepo.userLogin(user.getEmail(), user.getPassword());
        if(list.size() == 0)
        {
            response.put("Sign In" , "Failed");
            response.put("User" , "Not found");
        }
        else
        {
            String b = list.get(0).getPassword();
            if(!user.getPassword().equals(b))
            {
                response.put("Sign In" , "Failed");
                response.put("User" , "Invalid password");
            }
            else
            {
                response.put("Sign In" , "success");
                response.put("UserId" , Long.toString(list.get(0).getUser_id()));
                response.put("SignIn" , list.get(0).getName());
            }

        }
        return ResponseEntity.ok(response);

    }
}