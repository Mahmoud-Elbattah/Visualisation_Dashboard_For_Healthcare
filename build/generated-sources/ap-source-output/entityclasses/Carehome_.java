package entityclasses;

import entityclasses.Communityhealthcareorganisation;
import entityclasses.County;
import entityclasses.Localhealthoffice;
import entityclasses.Primarycarenetwork;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-07T11:32:31")
@StaticMetamodel(Carehome.class)
public class Carehome_ { 

    public static volatile SingularAttribute<Carehome, Integer> id;
    public static volatile SingularAttribute<Carehome, County> county;
    public static volatile SingularAttribute<Carehome, String> name;
    public static volatile SingularAttribute<Carehome, Double> longitude;
    public static volatile SingularAttribute<Carehome, Double> latitude;
    public static volatile SingularAttribute<Carehome, Localhealthoffice> lho;
    public static volatile SingularAttribute<Carehome, Communityhealthcareorganisation> choArea;
    public static volatile SingularAttribute<Carehome, Primarycarenetwork> pcn;

}