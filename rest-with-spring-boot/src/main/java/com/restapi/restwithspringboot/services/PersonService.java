package com.restapi.restwithspringboot.services;

import com.restapi.restwithspringboot.data.vo.v2.PersonVOV2;
import com.restapi.restwithspringboot.mapper.DozerMapper;
import com.restapi.restwithspringboot.mapper.custom.PersonMapper;
import com.restapi.restwithspringboot.models.Person;
import com.restapi.restwithspringboot.data.vo.v1.PersonVO;
import com.restapi.restwithspringboot.exceptions.ResourceNotFoundException;
import com.restapi.restwithspringboot.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll() {
        logger.info("Finding all people!");
        return DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person!");
        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating one person!");

        var entity = DozerMapper.parseObject(person, Person.class);
        var vo =  DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one person! - V2");

        var entity =  mapper.convertVoToEntity(person);
        var vo = mapper.convertEntityToVo(personRepository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO person) {

        logger.info("Updating one person!");

        var entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo =  DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        personRepository.delete(entity);
    }


}
