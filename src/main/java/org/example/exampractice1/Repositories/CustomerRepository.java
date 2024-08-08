//package org.example.exampractice1.Repositories;
//
//import org.example.exampractice1.Entities.Customer;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface CustomerRepository extends JpaRepository<Customer,Long> {
//    List<Customer> findByCustnoContaining (String keyword);
//
//    List<Customer> findAll();
//}


package org.example.exampractice1.Repositories;

import org.example.exampractice1.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByCustnoContaining(String custno);
    boolean existsByCustno(String custno);
    Customer findByCustno(String custno);
}
