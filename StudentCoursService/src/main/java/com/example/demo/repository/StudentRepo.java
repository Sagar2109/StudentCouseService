package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.medel.Student;


@Repository
public interface StudentRepo extends MongoRepository<Student, String> {
   // @Query(value="{'_id':?0,'suspended':false}")
	
	   @Query(value="{'_id':?0}")

	public Student findStudById(String id);

    @Query(value="{'email':?0}")
 
//    @Query(value="{'email':?0}",fields = "{'email':1,}")
   	public Student findStudByEmailId(String id);
}
