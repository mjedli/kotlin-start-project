package com.example.demo.repositroy

import com.example.demo.entity.User
import org.springframework.data.jdbc.core.JdbcAggregateOperations
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


interface UserRepository : CrudRepository<User, Int> {

    fun findByLogin(login: String): User?
    fun save(user: User): User?;
}

interface UserRepositoryPer {
    fun deleteById(id: Int?):Int
}

@Repository
class UserRepositoryPerImpl(val entityManager: JdbcAggregateOperations) : UserRepositoryPer{

    override fun deleteById(id: Int?):Int {

        val user: User? = id?.let { entityManager.findById(it, User::class.java) }
        return if(user != null) {
            entityManager.deleteById(id, User::class.java);
            1
        } else {
            0
        }

    }

}