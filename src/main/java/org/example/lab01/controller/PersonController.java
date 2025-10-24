package org.example.lab01.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.lab01.entity.Person;
import org.example.lab01.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
@Tag(name = "Person API",
        description = "人员管理API / API для управления персонами / Person Management API")
public class PersonController {

    @Autowired
    private PersonService personService;

    // GET /persons/{personId} - 获取人员信息
    @GetMapping("/{personId}")
    @Operation(summary = "根据ID获取人员 / Получить человека по ID / Get person by ID",
            description = "根据人员ID获取详细信息 / Получить подробную информацию по ID человека / Get detailed information by person ID")
    @ApiResponse(responseCode = "200", description = "成功获取人员信息 / Успешно получена информация о человеке / Successfully retrieved person information")
    @ApiResponse(responseCode = "404", description = "人员不存在 / Человек не найден / Person not found")
    public ResponseEntity<Person> getPerson(@PathVariable Long personId) {
        Person person = personService.getPersonById(personId);
        if (person == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(person); // 200 OK
    }

    // GET /persons - 获取所有人员信息
    @GetMapping
    @Operation(summary = "获取所有人员 / Получить всех людей / Get all persons",
            description = "返回系统中所有人员列表 / Возвращает список всех людей в системе / Returns a list of all persons in the system")
    @ApiResponse(responseCode = "200", description = "成功获取人员列表 / Успешно получен список людей / Successfully retrieved person list")
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        return ResponseEntity.ok(persons); // 200 OK
    }

    // POST /persons - 创建新的人员记录
    @PostMapping
    @Operation(summary = "创建新人员 / Создать нового человека / Create new person",
            description = "创建新的人员记录 / Создать новую запись о человеке / Create a new person record")
    @ApiResponse(responseCode = "201", description = "成功创建人员 / Человек успешно создан / Person successfully created")
    public ResponseEntity<Void> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        // 返回201 Created，包含Location header
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/persons/" + createdPerson.getId())
                .build();
    }

    // PATCH /persons/{personId} - 更新现有人员记录
    @PatchMapping("/{personId}")
    @Operation(summary = "更新人员信息 / Обновить информацию о человеке / Update person information",
            description = "部分更新人员信息 / Частичное обновление информации о человеке / Partial update of person information")
    @ApiResponse(responseCode = "200", description = "成功更新人员信息 / Информация о человеке успешно обновлена / Person information successfully updated")
    @ApiResponse(responseCode = "404", description = "人员不存在 / Человек не найден / Person not found")
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
    @Operation(summary = "删除人员 / Удалить человека / Delete person",
            description = "根据ID删除人员记录 / Удалить запись о человеке по ID / Delete person record by ID")
    @ApiResponse(responseCode = "204", description = "成功删除人员 / Человек успешно удален / Person successfully deleted")
    @ApiResponse(responseCode = "404", description = "人员不存在 / Человек не найден / Person not found")
    public ResponseEntity<Void> deletePerson(@PathVariable Long personId) {
        boolean deleted = personService.deletePerson(personId);
        if (!deleted) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}