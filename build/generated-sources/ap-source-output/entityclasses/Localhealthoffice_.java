package entityclasses;

import entityclasses.Carehome;
import entityclasses.Communityhealthcareorganisation;
import entityclasses.Communityhospital;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-07T11:32:31")
@StaticMetamodel(Localhealthoffice.class)
public class Localhealthoffice_ { 

    public static volatile SingularAttribute<Localhealthoffice, Integer> id;
    public static volatile CollectionAttribute<Localhealthoffice, Carehome> carehomeCollection;
    public static volatile SingularAttribute<Localhealthoffice, Communityhealthcareorganisation> choarea;
    public static volatile SingularAttribute<Localhealthoffice, String> description;
    public static volatile CollectionAttribute<Localhealthoffice, Communityhospital> communityhospitalCollection;

}