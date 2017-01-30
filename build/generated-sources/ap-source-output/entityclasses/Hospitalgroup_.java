package entityclasses;

import entityclasses.Communityhealthcareorganisation;
import entityclasses.Hospital;
import entityclasses.Mapregion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-07T11:32:31")
@StaticMetamodel(Hospitalgroup.class)
public class Hospitalgroup_ { 

    public static volatile SingularAttribute<Hospitalgroup, Integer> id;
    public static volatile SingularAttribute<Hospitalgroup, Mapregion> region;
    public static volatile CollectionAttribute<Hospitalgroup, Hospital> hospitalCollection;
    public static volatile SingularAttribute<Hospitalgroup, String> description;
    public static volatile SingularAttribute<Hospitalgroup, String> name;
    public static volatile CollectionAttribute<Hospitalgroup, Communityhealthcareorganisation> communityhealthcareorganisationCollection;

}