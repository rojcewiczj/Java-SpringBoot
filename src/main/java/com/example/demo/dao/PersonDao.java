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
       Optional<Person> personMaybe = selectPersonById(id);
       if(personMaybe.isEmpty()){
           return 0;
       }
       else{
           DB.remove(personMaybe.get());
           return 1;
       }
    }

    @Override
    public int  updatePersonById(UUID id, Person person){
        return selectPersonById(id)
            .map(p -> {
                int indexOfPersonToDelete = DB.indexOf(person);
                if (indexOfPersonToDelete >= 0){
                    DB.set(indexOfPersonToDelete, person);
                    return 1;
                }
                return 0;
            })
            .orElse(0);
        
    }

    @Override
    public Optional<Person>  selectPersonById(UUID id){
         return DB.stream()
        .filter(person -> person.getId().equals(id))
        .findFirst();
    }

}
}
