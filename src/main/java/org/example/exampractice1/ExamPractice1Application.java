//package org.example.exampractice1;
//
//import org.example.exampractice1.Entities.Customer;
//import org.example.exampractice1.Repositories.CustomerRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//@SpringBootApplication
//public class ExamPractice1Application {
//
//    public static void main(String[] args) {
//        SpringApplication.run(ExamPractice1Application.class, args);
//    }
//
//        @Bean
//        CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
//        return args -> {
//            customerRepository.save(new Customer(null, "115", "Jasper Diaz", 15000, 5, "Savings-Delux"));
//            customerRepository.save(new Customer(null, "112", "Zanip Mendez", 5000, 2, "Savings-Delux"));
//            customerRepository.save(new Customer(null, "113", "Geronima Esper", 6000, 5, "Savings-Regular"));
//            customerRepository.findAll().forEach(p->{
//                System.out.println(p.getCustname());
//            });
//        };
//    }
//
//}

package org.example.exampractice1;

import org.example.exampractice1.Entities.Customer;
import org.example.exampractice1.Repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExamPractice1Application {

    public static void main(String[] args) {
        SpringApplication.run(ExamPractice1Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
        return args -> {
            customerRepository.save(new Customer(null, "115", "Jasper Diaz", 15000, 5, "Savings-Delux"));
            customerRepository.save(new Customer(null, "112", "Zanip Mendez", 5000, 2, "Savings-Delux"));
            customerRepository.save(new Customer(null, "113", "Geronima Esper", 6000, 5, "Savings-Regular"));
            customerRepository.findAll().forEach(p -> {
                System.out.println(p.getCustname());
            });
        };
    }
}

