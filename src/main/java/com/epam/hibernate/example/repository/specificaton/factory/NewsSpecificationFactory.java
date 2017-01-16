package com.epam.hibernate.example.repository.specificaton.factory;


import com.epam.hibernate.example.entity.SearchCriteria;
import com.epam.hibernate.example.repository.specificaton.NewsSpecification;

/**
 * Created by Ivan_Lohvinau on 11/28/2016.
 */
public abstract class NewsSpecificationFactory {

    public abstract NewsSpecification createSpecification(SearchCriteria criteria);
}
