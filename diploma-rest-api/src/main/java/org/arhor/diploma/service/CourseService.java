package org.arhor.diploma.service;

import org.arhor.diploma.dto.CourseDTO;

import java.util.List;

public interface CourseService {

  CourseDTO getCourseById(Long id);

  List<CourseDTO> getCourses(int page, int size);

  List<CourseDTO> getCoursesByGroupId(Long id, int page, int size);

}
