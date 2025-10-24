package org.example.lab01.service;

import org.example.lab01.entity.Person;
import org.example.lab01.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    // 获取所有人员
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // 根据ID获取人员 - 如果找不到返回null
    public Person getPersonById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElse(null);
    }

    // 创建新人员 - 返回创建的人员对象（用于生成Location header）
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    // 更新人员信息 - 只更新非空字段
    public Person updatePerson(Long id, Person personDetails) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();

            // 只更新传入的非空字段
            if (personDetails.getName() != null) {
                person.setName(personDetails.getName());
            }
            if (personDetails.getAge() != null) {
                person.setAge(personDetails.getAge());
            }
            if (personDetails.getEmail() != null) {
                person.setEmail(personDetails.getEmail());
            }

            return personRepository.save(person);
        }
        return null;
    }

    // 删除人员
    public boolean deletePerson(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }
}