package entityclasses;

import entityclasses.Carehome;
import entityclasses.Communityhealthcareorganisation;
import entityclasses.Mapregion;
import entityclasses.Primarycareteam;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-07T11:32:31")
@StaticMetamodel(Primarycarenetwork.class)
public class Primarycarenetwork_ { 

    public static volatile SingularAttribute<Primarycarenetwork, Integer> id;
    public static volatile SingularAttribute<Primarycarenetwork, Mapregion> region;
    public static volatile CollectionAttribute<Primarycarenetwork, Carehome> carehomeCollection;
    public static volatile SingularAttribute<Primarycarenetwork, String> name;
    public static volatile SingularAttribute<Primarycarenetwork, Communityhealthcareorganisation> cho;
    public static volatile CollectionAttribute<Primarycarenetwork, Primarycareteam> primarycareteamCollection;

}