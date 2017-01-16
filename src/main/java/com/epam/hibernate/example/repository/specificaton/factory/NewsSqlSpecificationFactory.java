package com.epam.hibernate.example.repository.specificaton.factory;


import com.epam.hibernate.example.entity.SearchCriteria;
import com.epam.hibernate.example.repository.specificaton.NewsSqlSpecification;
import com.epam.hibernate.example.repository.specificaton.factory.NewsSpecificationFactory;
import com.epam.hibernate.example.repository.specificaton.impl.sql.NewByAuthorsSpecification;
import com.epam.hibernate.example.repository.specificaton.impl.sql.NewsByTagsAuthorsSpecification;
import com.epam.hibernate.example.repository.specificaton.impl.sql.NewsByTagsSpecification;
import com.epam.hibernate.example.repository.specificaton.impl.sql.NewsOrderByDateSpecification;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan_Lohvinau on 11/28/2016.
 */
@Component
public class NewsSqlSpecificationFactory extends NewsSpecificationFactory {

    @Override
    public NewsSqlSpecification createSpecification(SearchCriteria criteria) {
        NewsSqlSpecification newsSqlSpecification;
        if(criteria.getAuthors().size()>0&&criteria.getTags().size()>0){
            newsSqlSpecification = new NewsByTagsAuthorsSpecification(criteria.getAuthors(),criteria.getTags());
        }else if(criteria.getAuthors().size() > 0){
            newsSqlSpecification = new NewByAuthorsSpecification(criteria.getAuthors());
        }else if(criteria.getTags().size() > 0){
            newsSqlSpecification = new NewsByTagsSpecification(criteria.getTags());
        }else {
            newsSqlSpecification = new NewsOrderByDateSpecification();
        }
        return  newsSqlSpecification;
    }
}
