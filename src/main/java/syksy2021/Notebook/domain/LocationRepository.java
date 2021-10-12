package syksy2021.Notebook.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {

	List<Location> findByLocationName(String locationName);
}
