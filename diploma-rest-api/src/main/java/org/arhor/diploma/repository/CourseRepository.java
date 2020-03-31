package org.arhor.diploma.repository;

import org.arhor.diploma.domain.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends BaseRepository<Course, Long> {
}
