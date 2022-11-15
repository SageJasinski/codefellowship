package repositories;

import model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterface extends JpaRepository<UserModel, Long> {
    public UserModel findUserByUserName(String UserName);
}
