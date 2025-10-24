package org.example.lab01.controller;

import org.example.lab01.entity.Person;
import org.example.lab01.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    // GET /persons/{personId} - 获取人员信息
    @GetMapping("/{personId}")
    public ResponseEntity<Person> getPerson(@PathVariable Long personId) {
        Person person = personService.getPersonById(personId);
        if (person == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(person); // 200 OK
    }

    // GET /persons - 获取所有人员信息
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        return ResponseEntity.ok(persons); // 200 OK
    }

    // POST /persons - 创建新的人员记录
    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        // 返回201 Created，包含Location header
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/persons/" + createdPerson.getId())
                .build();
    }

    // PATCH /persons/{personId} - 更新现有人员记录
    @PatchMapping("/{personId}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long personId,
                                               @RequestBody Person personDetails) {
        Person updatedPerson = personService.updatePerson(personId, personDetails);
        if (updatedPerson == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(updatedPerson); // 200 OK
    }

    // DELETE /persons/{personId} - 删除人员记录
    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long personId) {
        boolean deleted = personService.deletePerson(personId);
        if (!deleted) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}