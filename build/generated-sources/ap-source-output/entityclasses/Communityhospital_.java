package entityclasses;

import entityclasses.Communityhealthcareorganisation;
import entityclasses.County;
import entityclasses.Localhealthoffice;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-07T11:32:31")
@StaticMetamodel(Communityhospital.class)
public class Communityhospital_ { 

    public static volatile SingularAttribute<Communityhospital, Integer> id;
    public static volatile SingularAttribute<Communityhospital, String> address;
    public static volatile SingularAttribute<Communityhospital, County> county;
    public static volatile SingularAttribute<Communityhospital, String> name;
    public static volatile SingularAttribute<Communityhospital, Double> longitude;
    public static volatile SingularAttribute<Communityhospital, Double> latitude;
    public static volatile SingularAttribute<Communityhospital, Localhealthoffice> lho;
    public static volatile SingularAttribute<Communityhospital, Communityhealthcareorganisation> choArea;

}