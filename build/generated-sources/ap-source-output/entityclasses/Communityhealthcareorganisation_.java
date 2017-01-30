package entityclasses;

import entityclasses.Carehome;
import entityclasses.Communityhospital;
import entityclasses.Hospital;
import entityclasses.Hospitalgroup;
import entityclasses.Localhealthoffice;
import entityclasses.Mapregion;
import entityclasses.Primarycarenetwork;
import entityclasses.Primarycareteam;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-07T11:32:31")
@StaticMetamodel(Communityhealthcareorganisation.class)
public class Communityhealthcareorganisation_ { 

    public static volatile SingularAttribute<Communityhealthcareorganisation, Integer> id;
    public static volatile SingularAttribute<Communityhealthcareorganisation, Mapregion> region;
    public static volatile CollectionAttribute<Communityhealthcareorganisation, Hospital> hospitalCollection;
    public static volatile CollectionAttribute<Communityhealthcareorganisation, Carehome> carehomeCollection;
    public static volatile CollectionAttribute<Communityhealthcareorganisation, Primarycarenetwork> primarycarenetworkCollection;
    public static volatile SingularAttribute<Communityhealthcareorganisation, String> name;
    public static volatile CollectionAttribute<Communityhealthcareorganisation, Communityhospital> communityhospitalCollection;
    public static volatile CollectionAttribute<Communityhealthcareorganisation, Primarycareteam> primarycareteamCollection;
    public static volatile CollectionAttribute<Communityhealthcareorganisation, Hospitalgroup> hospitalgroupCollection;
    public static volatile CollectionAttribute<Communityhealthcareorganisation, Localhealthoffice> localhealthofficeCollection;
    public static volatile SingularAttribute<Communityhealthcareorganisation, Integer> population;

}