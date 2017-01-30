package entityclasses;

import entityclasses.Communityhealthcareorganisation;
import entityclasses.Hospitalgroup;
import entityclasses.Primarycarenetwork;
import entityclasses.Primarycareteam;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-07T11:32:31")
@StaticMetamodel(Mapregion.class)
public class Mapregion_ { 

    public static volatile SingularAttribute<Mapregion, Integer> id;
    public static volatile CollectionAttribute<Mapregion, Primarycarenetwork> primarycarenetworkCollection;
    public static volatile SingularAttribute<Mapregion, String> name;
    public static volatile CollectionAttribute<Mapregion, Communityhealthcareorganisation> communityhealthcareorganisationCollection;
    public static volatile CollectionAttribute<Mapregion, Primarycareteam> primarycareteamCollection;
    public static volatile CollectionAttribute<Mapregion, Hospitalgroup> hospitalgroupCollection;

}