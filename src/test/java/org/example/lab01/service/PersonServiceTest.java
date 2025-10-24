package org.example.lab01.service;

import org.example.lab01.entity.Person;
import org.example.lab01.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void testGetAllPersons() {
        // 准备测试数据
        Person person1 = new Person("张三", 25, "zhangsan@example.com");
        Person person2 = new Person("李四", 30, "lisi@example.com");
        List<Person> expectedPersons = Arrays.asList(person1, person2);

        // 模拟Repository行为
        when(personRepository.findAll()).thenReturn(expectedPersons);

        // 执行测试
        List<Person> actualPersons = personService.getAllPersons();

        // 验证结果
        assertEquals(2, actualPersons.size());
        assertEquals("张三", actualPersons.get(0).getName());
        assertEquals("李四", actualPersons.get(1).getName());

        // 验证Repository方法被调用
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testGetPersonById_Found() {
        // 准备测试数据
        Long personId = 1L;
        Person expectedPerson = new Person("张三", 25, "zhangsan@example.com");
        expectedPerson.setId(personId);

        // 模拟Repository行为
        when(personRepository.findById(personId)).thenReturn(Optional.of(expectedPerson));

        // 执行测试
        Person actualPerson = personService.getPersonById(personId);

        // 验证结果
        assertNotNull(actualPerson);
        assertEquals(personId, actualPerson.getId());
        assertEquals("张三", actualPerson.getName());

        // 验证Repository方法被调用
        verify(personRepository, times(1)).findById(personId);
    }

    @Test
    void testGetPersonById_NotFound() {
        // 准备测试数据
        Long personId = 999L;

        // 模拟Repository行为
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        // 执行测试
        Person actualPerson = personService.getPersonById(personId);

        // 验证结果
        assertNull(actualPerson);

        // 验证Repository方法被调用
        verify(personRepository, times(1)).findById(personId);
    }

    @Test
    void testCreatePerson() {
        // 准备测试数据
        Person inputPerson = new Person("张三", 25, "zhangsan@example.com");
        Person savedPerson = new Person("张三", 25, "zhangsan@example.com");
        savedPerson.setId(1L);

        // 模拟Repository行为
        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);

        // 执行测试
        Person actualPerson = personService.createPerson(inputPerson);

        // 验证结果
        assertNotNull(actualPerson);
        assertEquals(1L, actualPerson.getId());
        assertEquals("张三", actualPerson.getName());

        // 验证Repository方法被调用
        verify(personRepository, times(1)).save(inputPerson);
    }

    @Test
    void testUpdatePerson_Found() {
        // 准备测试数据
        Long personId = 1L;
        Person existingPerson = new Person("张三", 25, "zhangsan@example.com");
        existingPerson.setId(personId);

        Person updateDetails = new Person();
        updateDetails.setName("张三丰");
        updateDetails.setAge(26);

        // 模拟Repository行为
        when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).thenReturn(existingPerson);

        // 执行测试
        Person actualPerson = personService.updatePerson(personId, updateDetails);

        // 验证结果
        assertNotNull(actualPerson);
        assertEquals("张三丰", actualPerson.getName());
        assertEquals(26, actualPerson.getAge());
        assertEquals("zhangsan@example.com", actualPerson.getEmail()); // 邮箱保持不变

        // 验证Repository方法被调用
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(existingPerson);
    }

    @Test
    void testUpdatePerson_NotFound() {
        // 准备测试数据
        Long personId = 999L;
        Person updateDetails = new Person();
        updateDetails.setName("不存在的用户");

        // 模拟Repository行为
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        // 执行测试
        Person actualPerson = personService.updatePerson(personId, updateDetails);

        // 验证结果
        assertNull(actualPerson);

        // 验证Repository方法被调用
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void testDeletePerson_Success() {
        // 准备测试数据
        Long personId = 1L;

        // 模拟Repository行为
        when(personRepository.existsById(personId)).thenReturn(true);

        // 执行测试
        boolean result = personService.deletePerson(personId);

        // 验证结果
        assertTrue(result);

        // 验证Repository方法被调用
        verify(personRepository, times(1)).existsById(personId);
        verify(personRepository, times(1)).deleteById(personId);
    }

    @Test
    void testDeletePerson_NotFound() {
        // 准备测试数据
        Long personId = 999L;

        // 模拟Repository行为
        when(personRepository.existsById(personId)).thenReturn(false);

        // 执行测试
        boolean result = personService.deletePerson(personId);

        // 验证结果
        assertFalse(result);

        // 验证Repository方法被调用
        verify(personRepository, times(1)).existsById(personId);
        verify(personRepository, never()).deleteById(personId);
    }
}