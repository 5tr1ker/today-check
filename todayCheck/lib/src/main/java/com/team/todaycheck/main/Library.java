/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.team.todaycheck.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Library {
    public boolean someLibraryMethod() {
        return true;
    }
    
    public static void main(String[] args) {
		SpringApplication.run(Library.class, args);
	}
}