package com.ravindra.taskmanager.specification;

import com.ravindra.taskmanager.entity.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> hasTitle(String title){
        return (root,query,criteriaBuilder)->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%"+title.toLowerCase()+"%"
                );
    }

    public static Specification<Task> hasStatus(String status){
        return(root,query,criteriaBuilder)->
                criteriaBuilder.equal(root.get("status"),status);
    }
}
