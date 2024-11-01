package ao.co.isptec.aplm.Lab4;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
