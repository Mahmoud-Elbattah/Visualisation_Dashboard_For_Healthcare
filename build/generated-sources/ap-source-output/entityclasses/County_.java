package entityclasses;

import entityclasses.Carehome;
import entityclasses.Communityhospital;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-07T11:32:31")
@StaticMetamodel(County.class)
public class County_ { 

    public static volatile SingularAttribute<County, Integer> id;
    public static volatile CollectionAttribute<County, Carehome> carehomeCollection;
    public static volatile SingularAttribute<County, String> name;
    public static volatile CollectionAttribute<County, Communityhospital> communityhospitalCollection;

}