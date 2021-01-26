package com.example.demo.dao;
import java.util.UUID;
import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface PersonDao {
    int insertPerson(UUID id, Person person);
    
    default int insertPerson(Person person){
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }
    
    List<Person> selectAllPeople();

    int deletePersonById(UUID id);

    int updatePersonById(UUID id, Person person);

    Optional<Person> selectPersonById(UUID id);

    @Repository ("fakeDao")
    public class FakePersonDataAccessService implements PersonDao{

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person){
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople(){
        return DB;
    }

    @Override
    public int deletePersonById(UUID id){
       return 0;
    }

    @Override
    public int  updatePersonById(UUID id, Person person){
        return 0;
    }

    @Override
    public Optional<Person>  selectPersonById(UUID id){
         return DB.stream()
        .filter(person -> person.getId().equals(id))
        .findFirst();
    }

}
}
