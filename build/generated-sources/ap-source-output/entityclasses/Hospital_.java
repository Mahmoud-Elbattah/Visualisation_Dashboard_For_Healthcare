package entityclasses;

import entityclasses.Communityhealthcareorganisation;
import entityclasses.Hospitalgroup;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-07T11:32:31")
@StaticMetamodel(Hospital.class)
public class Hospital_ { 

    public static volatile SingularAttribute<Hospital, Integer> id;
    public static volatile SingularAttribute<Hospital, Boolean> nho;
    public static volatile SingularAttribute<Hospital, String> name;
    public static volatile SingularAttribute<Hospital, Communityhealthcareorganisation> areaid;
    public static volatile SingularAttribute<Hospital, Double> longitude;
    public static volatile SingularAttribute<Hospital, Integer> hIPECode;
    public static volatile SingularAttribute<Hospital, Double> latitude;
    public static volatile SingularAttribute<Hospital, Hospitalgroup> hospitalGroup;
    public static volatile SingularAttribute<Hospital, Integer> doHCode;

}