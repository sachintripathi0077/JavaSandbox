package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Courses;
import com.eazybytes.eazyschool.model.EazyClass;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.repository.ClassRepository;
import com.eazybytes.eazyschool.repository.CoursesRepository;
import com.eazybytes.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("admin")
public class AdminController {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model){
        Iterable<EazyClass> allClasses = classRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("eazyClasses",allClasses);
        modelAndView.addObject("eazyClass", new EazyClass());
        return modelAndView;
    }

    @RequestMapping(value = "/addNewClass", method = RequestMethod.POST)
    public ModelAndView addNewClass(Model model, @ModelAttribute("eazyClass") EazyClass eazyClass){
        classRepository.save(eazyClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping(value = "/deleteClass",method = RequestMethod.GET)
    public ModelAndView deleteClass(Model model, @RequestParam int id){
        Optional<EazyClass> eazyClass = classRepository.findById(id);

        for(Person person:eazyClass.get().getPersons()){
            person.setEazyClass(null);
            personRepository.save(person);
        }

        classRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping(value = "/displayStudents",method = RequestMethod.GET)
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession httpSession,
                                        @RequestParam(value = "error",required = false) String error){
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<EazyClass> optionalEazyClass = classRepository.findById(classId);
        EazyClass eazyClass;
        if (optionalEazyClass.isPresent()) {
            eazyClass = optionalEazyClass.get();
            modelAndView.addObject("eazyClass", eazyClass);
            modelAndView.addObject("person", new Person());
            httpSession.setAttribute("eazyClass", eazyClass);
            httpSession.setAttribute("eazyClass",eazyClass);
        } else {
            // Handle case where classId is not found
            modelAndView.addObject("errorMessage", "Class not found for id: " + classId);
            // Optionally redirect or show an error page
        }
        if(error != null){
            String errorMessage = "Invalid email entered!";
            modelAndView.addObject("errorMessage",errorMessage);
        }

        return modelAndView;
    }

    @RequestMapping(value = "deleteStudent", method = RequestMethod.GET)
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession httpSession){
        EazyClass eazyClass = (EazyClass) httpSession.getAttribute("eazyClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setEazyClass(null); // as we don't want to remove the person permanently from the db, but just want to disassociate the class
        eazyClass.getPersons().remove(person.get());
        EazyClass eazyClassSaved = classRepository.save(eazyClass);
        httpSession.setAttribute("eazyClass",eazyClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView();
        EazyClass eazyClass = (EazyClass) httpSession.getAttribute("eazyClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());

        if(null == personEntity){
            // if there is not such person that exists with that email
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId()+"&error=true");
            return modelAndView;
        }

        personEntity.setEazyClass(eazyClass);
        personRepository.save(personEntity);
        eazyClass.getPersons().add(personEntity);
        classRepository.save(eazyClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }

    @RequestMapping(value = "/displayCourses", method = RequestMethod.GET)
    public ModelAndView displayCourses(Model model){
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        List<Courses> allCourses = coursesRepository.findAll();

        modelAndView.addObject("courses",allCourses);
        modelAndView.addObject("course", new Courses());
        return modelAndView;
    }

    @RequestMapping(value = "/addNewCourse", method = RequestMethod.POST)
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course){
        ModelAndView modelAndView = new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @RequestMapping(value = "/viewStudents",method = RequestMethod.GET)
    public ModelAndView viewStudents(Model model, @RequestParam int id, HttpSession httpSession,
                                     @RequestParam(value = "error",required = false) String error){
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> course = coursesRepository.findById(id);
        modelAndView.addObject("courses", course.get());
        modelAndView.addObject("person", new Person());
        httpSession.setAttribute("courses",course.get());
        if(error!=null){
            String errorMessage = "Invalid email entered!";
            modelAndView.addObject("errorMessage",errorMessage);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/addStudentToCourse", method = RequestMethod.POST)
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person, HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) httpSession.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity == null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()+"&error=true");
            return modelAndView;
        }

        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        httpSession.setAttribute("courses",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }

    @RequestMapping(value = "/deleteStudentFromCourse",method = RequestMethod.GET)
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId, HttpSession httpSession){
        Courses courses = (Courses) httpSession.getAttribute("courses");
        Optional<Person> person = personRepository.findById(personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person);
        personRepository.save(person.get());
        httpSession.setAttribute("courses",courses);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }
}
