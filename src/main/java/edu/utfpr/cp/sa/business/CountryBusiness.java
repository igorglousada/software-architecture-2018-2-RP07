package edu.utfpr.cp.sa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.utfpr.cp.sa.dao.CountryDAO;
import edu.utfpr.cp.sa.entity.Country;

import edu.utfpr.cp.sa.entity.Customer;

@Component
public class CountryBusiness {

    private CountryDAO countryDAO;

    @Autowired
    public CountryBusiness (CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }
    
    public boolean create(Country country) throws Exception {

        if (this.read().stream().map(Country::getName).anyMatch(e -> e.equals(country.getName()))) 
            throw new IllegalArgumentException("There already is a country with this name!");
        
        else {
            this.countryDAO.save(country);
            return true;
        }
            
    }

    public List<Country> read() {
        return this.countryDAO.findAll();
    }

    public boolean update(Country country) throws Exception {
        if (this.read().stream().map(Country::getName).anyMatch(e -> e.equals(country.getName()))) 
            throw new IllegalArgumentException("There already is a country with this name!");

        else {
            this.countryDAO.saveAndFlush(country);
            return true;
        }
    }

    public boolean delete(List<Customer> listCus, Long id) throws Exception{

        Country c = this.read().stream().filter(e -> e.getId() == id).findAny().get();

        for (Customer cmer : listCus){
            if (cmer.getCountry().getId() == c.getId()){
                throw new IllegalArgumentException("ERROR.");
            }
        }
        
        this.countryDAO.delete(c);
        return true;
    }

}