package entityclasses;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-07T11:32:31")
@StaticMetamodel(Summarycounts.class)
public class Summarycounts_ { 

    public static volatile SingularAttribute<Summarycounts, BigInteger> commHospitalCount;
    public static volatile SingularAttribute<Summarycounts, Long> id;
    public static volatile SingularAttribute<Summarycounts, BigInteger> pctCount;
    public static volatile SingularAttribute<Summarycounts, BigInteger> carehomeCount;
    public static volatile SingularAttribute<Summarycounts, BigInteger> hospitalCount;
    public static volatile SingularAttribute<Summarycounts, BigInteger> choCount;
    public static volatile SingularAttribute<Summarycounts, BigInteger> totalPopulation;

}