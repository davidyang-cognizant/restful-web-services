package com.example.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("filtering")
    public MappingJacksonValue filtering(){
        // MappingJacksonValue will filter variables dynamically
        SomeBean someBean = new SomeBean("value 1", "value 2", "value 3");
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("s1","s2");
        FilterProvider fp = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        mappingJacksonValue.setFilters(fp);
        return mappingJacksonValue;
    }

    @GetMapping("filtering-list")
    public List<SomeBean> filteringList(){
        return Arrays.asList(
                new SomeBean("value 1", "value 2", "value 3"),
                new SomeBean("value 4", "value 5", "value 6")
        );
    }
}
