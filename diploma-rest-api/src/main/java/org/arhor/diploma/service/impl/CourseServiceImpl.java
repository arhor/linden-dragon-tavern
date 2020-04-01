//package org.arhor.diploma.service.impl;
//
//import lombok.RequiredArgsConstructor;
//import org.arhor.diploma.domain.Course;
//import org.arhor.diploma.domain.Group;
//import org.arhor.diploma.dto.CourseDTO;
//import org.arhor.diploma.exception.EntityNotFoundException;
//import org.arhor.diploma.repository.CourseRepository;
//import org.arhor.diploma.service.CourseService;
//import org.arhor.diploma.util.Converter;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CourseServiceImpl implements CourseService {
//
//  private final CourseRepository repository;
//  private final Converter converter;
//
//  @Override
//  public CourseDTO getCourseById(Long id) {
//    return repository
//        .findById(id)
//        .map(course -> converter.convert(course, CourseDTO.class))
//        .orElseThrow(() -> new EntityNotFoundException("Course", "id", id));
//  }
//
//  @Override
//  public List<CourseDTO> getCourses(int page, int size) {
//    return repository
//        .findAll(PageRequest.of(page, size))
//        .map(course -> converter.convert(course, CourseDTO.class))
//        .toList();
//  }
//
//  @Override
//  public List<CourseDTO> getCoursesByGroupId(Long id, int page, int size) {
//    final var exampleGroup = new Group();
//    exampleGroup.setId(id);
//
//    final var exampleCourse = new Course();
//    exampleCourse.setGroups(List.of(exampleGroup));
//
//    return repository
//        .findAll(Example.of(exampleCourse), PageRequest.of(page, size))
//        .map(course -> converter.convert(course, CourseDTO.class))
//        .toList();
//  }
//}
