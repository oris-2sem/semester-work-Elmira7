package ru.itis.sem_1.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.itis.sem_1.model.Room;
import ru.itis.sem_1.model.User;


import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room> {

    default List<Room> getRoomOptionalParameter(Map<String, String> params) {
        Specification<Room> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (params.containsKey("owner")) {
                UUID owner = UUID.fromString(params.get("owner"));
                Join<Room, User> join = root.join("owner", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(join.get("id"), owner));
            }

            if (params.containsKey("address")) {
                String address =  params.get("address");
                if (address != null) {
                    predicates.add(criteriaBuilder.equal(root.get("address"), address));
                }
            }

            if (params.containsKey("status")) {
                Room.Status status = Room.Status.valueOf(params.get("status"));
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (params.containsKey("city")) {
                String city =  params.get("city");
                if (city != null) {
                    predicates.add(criteriaBuilder.equal(root.get("city"), city));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return findAll(specification);
    }

    //@Query("select r from Room inner jo")
    //List<Room> findRoomByRenter(List<User> users);


}
