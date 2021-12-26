package demo

import org.springframework.data.repository.CrudRepository


interface UserRepository : CrudRepository<User, Int> {

    fun findByLogin(login: String): User?
    fun save(user: User): User?;
    fun deleteById(id: Int?);
}