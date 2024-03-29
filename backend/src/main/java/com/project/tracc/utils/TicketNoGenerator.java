package com.project.tracc.utils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

public class TicketNoGenerator extends SequenceStyleGenerator{

    public static final String DATE_FORMAT_PARAMETER = "dateFormat";
    public static final String DATE_FORMAT_DEFAULT = "T%ty%tm%td";
     
    public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
    public static final String NUMBER_FORMAT_DEFAULT = "%s";
     
    private String format;
     
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        super.generate(session, object);
        String random = RandomStringUtils.randomAlphabetic(4).toUpperCase();
            
        return String.format(format, LocalDate.now(), LocalDate.now(), LocalDate.now(), random);
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);

        String dateFormat = ConfigurationHelper.getString(DATE_FORMAT_PARAMETER, params, DATE_FORMAT_DEFAULT); 
        String numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params, NUMBER_FORMAT_DEFAULT); 
        this.format = dateFormat+numberFormat; 
    } 
    
    // public static final String DATE_FORMAT_PARAMETER = "dateFormat";
    // public static final String DATE_FORMAT_DEFAULT = "%ty%tm%td";
     
    // public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
    // public static final String NUMBER_FORMAT_DEFAULT = "%03d";
     
    // public static final String DATE_NUMBER_SEPARATOR_PARAMETER = "dateNumberSeparator";
    // public static final String DATE_NUMBER_SEPARATOR_DEFAULT = "_";
     
    // private String format;

    // @Override
    // public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
            
    //     return String.format(format, LocalDate.now(), LocalDate.now(), LocalDate.now(), super.generate(session, object));
    // }

    // @Override
    // public void configure(Type type, Properties params,
    //         ServiceRegistry serviceRegistry) throws MappingException {
    //     super.configure(LongType.INSTANCE, params, serviceRegistry);

    //     String dateFormat = ConfigurationHelper.getString(DATE_FORMAT_PARAMETER, params, DATE_FORMAT_DEFAULT); 
    //     String numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params, NUMBER_FORMAT_DEFAULT); 
    //     String dateNumberSeparator = ConfigurationHelper.getString(DATE_NUMBER_SEPARATOR_PARAMETER, params, DATE_NUMBER_SEPARATOR_DEFAULT); 
    //     this.format = dateFormat+dateNumberSeparator+numberFormat; 
    // } 
    
}
