package syksy2021.Notebook.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CreatorRepository extends CrudRepository<Creator, Long> {

	List<Creator> findByCreatorName(String creatorName);
}
