package com.cst438.controllers;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentListDTO;
import com.cst438.domain.Course;
import com.cst438.domain.GradebookDTO;


/*
 * 2b.  Gradebook app:  create REST apis to add/delete assignments for a course.

The story is :   As an instructor for a course , I can add a new assignment for my course.  The assignment has a name and a due date.
As an instructor, I can change the name of the assignment for my course.
As an instructor, I can delete an assignment  for my course (only if there are no grades for the assignment).
 */
public class StudentController {

/*
 * The first method in the controller gets a list of assignment that need grading for an instructor. 
 *  If the instructor is teaching multiple courses,  assignments from all courses are returned.  
 * This is done by the front end issuing a HTTP GET request with the URL /gradebook.  
 * Spring server will call the getAssignmentsNeedGrading method.
 */

@GetMapping("/gradebook")
public AssignmentListDTO getAssignmentsNeedGrading( ) {
        
    String email = "dwisneski@csumb.edu";  // user name (should be instructor's email) 
    // In week 5 will do sign on function.  For now we will hardcode the instructor's email. 

  List<Assignment> assignments = assignmentRepository.findNeedGradingByEmail(email);

    // The data from the entity objects is copied into DTO object (Data Transfer Objects) 
    // that will be returned to the front end in JSON (JavaScript object notation) form.

    AssignmentListDTO result = new AssignmentListDTO();
    for (Assignment a: assignments) {
        result.assignments.add(...);
    }
    return result;
    }

    // Using POSTMAN do an HTTP GET  localhost:8081/gradebook
// Be sure to use port 8081 (this is the server port defined in application.properties file).
// The GET request returns the data

    @GetMapping("/gradebook/{id}")
    public GradebookDTO getGradebook(@PathVariable("id") Integer assignmentId  ) {
        // get the assignment from the database
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        // get the list of assignment grades for the assignment
        List<AssignmentGrade> assignmentGrades = assignmentGradeRepository.findByAssignment(assignment);
        // The data from the entity objects is copied into DTO object (Data Transfer Objects) 
        // that will be returned to the front end in JSON (JavaScript object notation) form.
        GradebookDTO gradebook = new GradebookDTO();
        for (AssignmentGrade ag: assignmentGrades) {
            Grade grade = new Grade();
            grade.student_name = ag.getStudent().getName();
            grade.grade = ag.getGrade();
            gradebook.grades.add(grade);
        }
        return gradebook;
    }

   @PostMapping("/course/{course_id}/finalgrades")
    @Transactional
    public void calcFinalGrades(@PathVariable int course_id) {
        // get the course from the database
        Course course = courseRepository.findById(course_id).get();
        // get the list of assignments for the course
        List<Assignment> assignments = assignmentRepository.findByCourse(course);
        // for each assignment in the course, calculate the final grade for the student
        for (Assignment a: assignments) {
            // get the student and the grade
            Student student = a.getStudent();
            int grade = a.getGrade();
            // get the course grade for the student
            CourseGrade cg = courseGradeRepository.findByStudentAndCourse(student, course);
            // calculate the new course grade
            cg.setGrade((cg.getGrade() + grade)/2);
            // save the course grade back to the database
            courseGradeRepository.save(cg);
        }
    }

    @PutMapping("/gradebook/{id}")
    @Transactional
    public void updateGradebook (@RequestBody GradebookDTO gradebook, @PathVariable("id") Integer assignmentId ) {
        // get the assignment from the database
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        // update the grade
        assignment.setGrade(gradebook.grade);
        // save the assignment back to the database
        assignmentRepository.save(assignment);
    }
// Both a PathVariable "assignmentId" and request BODY are used to send data from the front end to back end.  
// Spring will take the JSON data in request BODY and deserialize it into a GradebookDTO object and pass that object to the method.
// The logic of the method, for each Grade in GradebookDTO, look up the assignment_grade and update the score and save it back.  
// The save method in the AssignmentGradeRepository is used for both inserted new rows and updating existing rows.
// There is no data returned to the front end in the normal case.  If there is an error, then an Exception is returned as 
// an HTTP STATUS CODE of BAD_REQUEST and any updated already done to the database are rolledback 
// (because of the @Transactional annotation on the method). 

}
