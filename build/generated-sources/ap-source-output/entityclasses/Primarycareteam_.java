package entityclasses;

import entityclasses.Communityhealthcareorganisation;
import entityclasses.Mapregion;
import entityclasses.Primarycarenetwork;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-07T11:32:31")
@StaticMetamodel(Primarycareteam.class)
public class Primarycareteam_ { 

    public static volatile SingularAttribute<Primarycareteam, Integer> id;
    public static volatile SingularAttribute<Primarycareteam, Mapregion> region;
    public static volatile SingularAttribute<Primarycareteam, String> pCTLogName;
    public static volatile SingularAttribute<Primarycareteam, String> name;
    public static volatile SingularAttribute<Primarycareteam, Communityhealthcareorganisation> choareaid;
    public static volatile SingularAttribute<Primarycareteam, Primarycarenetwork> pcn;
    public static volatile SingularAttribute<Primarycareteam, Integer> population;

}