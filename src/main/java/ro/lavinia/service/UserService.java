package ro.lavinia.service;



import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("UserDetailsService")
@Transactional
public interface UserService extends UserDetailsService {

    String determineRedirectURL(Authentication authentication);

}
