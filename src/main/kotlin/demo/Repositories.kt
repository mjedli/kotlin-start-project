package demo

import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


interface UserRepository : CrudRepository<User, Int> {

    fun findByLogin(login: String): User?
    fun save(user: User): User?;
    fun deleteById(id: Int?);
}