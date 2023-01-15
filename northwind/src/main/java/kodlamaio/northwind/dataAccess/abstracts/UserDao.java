package kodlamaio.northwind.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.northwind.entities.concretes.User;

public interface UserDao extends JpaRepository<User,Integer> {
    /**
     * Find by email.
     *
     * @param email
     *            the email
     * @return the user
     */
    User findByEmail(String email);

    /**
     * Find by userName.
     *
     * @param userName
     *            the userName
     * @return the user
     */
    User findByUserName(String userName);

    /**
     * Delete.
     *
     * @param user
     *            the user
     */
    @Override
    void delete(User user);
}
