package com.edu.studentms.resource;

import com.edu.studentms.model.Student;
import com.edu.studentms.repo.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentResource.class);

    @Autowired
    private StudentRepo repo;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getAllStudents() {
        LOGGER.info("fetching all students");
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping(path = "/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getSingleStudent(@PathVariable Integer studentId) {
        Optional<Student> student = repo.findById(studentId);
        if (student.isPresent()) {
            LOGGER.info("Student {}, found.", studentId);
            return ResponseEntity.ok(student.get());
        }

        LOGGER.error("Student {}, not found.", studentId);
        return ResponseEntity.notFound().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws URISyntaxException {
        Student newStudent = repo.save(student);
        LOGGER.info("New Student {}, added.", newStudent.getId());
        return ResponseEntity.created(new URI(newStudent.getId().toString())).body(newStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer studentId) {
        repo.deleteById(studentId);
        LOGGER.info("Student {}, deleted.", studentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> updateStudent(@PathVariable Integer studentId, @RequestBody Student student) {
        boolean isIdFound = repo.existsById(studentId);
        if (!isIdFound) {
            LOGGER.error("Student with id {}, not found.", student.getId());
            return ResponseEntity.notFound().build();
        }

        student.setId(studentId);
        Student updatedStudent = repo.save(student);
        LOGGER.info("Student {}, updated.", student.getId());
        return ResponseEntity.ok(updatedStudent);
    }
}
