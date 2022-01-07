package com.example.demo.services

import com.example.demo.entity.User
import com.example.demo.repositroy.UserRepository
import com.example.demo.repositroy.UserRepositoryPer
import org.springframework.stereotype.Service


@Service
class Services(private val repository: UserRepository,
               private val repositoryPer: UserRepositoryPer
) {

    fun findByLogin(login: String): User? {
        return repository.findByLogin(login);
    }

    fun save(user: User): User? {
        return repository.save(user);
    }

    fun deleteById(id: Int?):Int {
        return repositoryPer.deleteById(id);
    }
}