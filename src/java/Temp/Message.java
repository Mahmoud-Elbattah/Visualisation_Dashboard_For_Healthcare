package Temp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "message", eager = true)
@RequestScoped
public class Message {
	
	private String text = " [" +
"{id:0,pId:0, name:'Healthcare Landscape', open:true},"+
"{id:1, pId:0, name:'Area 1',type:'CHO1'},"+
"{id:12, pId:1, name:'Dublin North East',type:'HospitalGroup2'},"+
"{id:1214, pId:12, name:'Connolly Hospital, Blanchardstown',type:'AcuteHospital14'},"+
"{id:1221, pId:12, name:'Beaumont Hospital',type:'AcuteHospital21'},"+
"{id:1223, pId:12, name:'Rotunda Hospital',type:'AcuteHospital23'},"+
"{id:1262, pId:12, name:'Our Lady of Lourdes Hospital, Drogheda',type:'AcuteHospital62'},"+
"{id:1263, pId:12, name:'General Hospital, Cavan',type:'AcuteHospital63'},"+
"{id:1264, pId:12, name:'Louth County Hospital',type:'AcuteHospital64'},"+
"{id:1265, pId:12, name:'Monaghan General Hospital',type:'AcuteHospital65'},"+
"{id:1268, pId:12, name:\"St Luke's Radiation Oncology Network - Beaumont Centre\",type:'AcuteHospital68'},"+
"{id:16, pId:1, name:'West/North West',type:'HospitalGroup6'},"+
"{id:1650, pId:16, name:'Roscommon County Hospital',type:'AcuteHospital50'},"+
"{id:1651, pId:16, name:'Portiuncula Hospital',type:'AcuteHospital51'},"+
"{id:1652, pId:16, name:'Galway University Hospitals',type:'AcuteHospital52'},"+
"{id:1653, pId:16, name:'Regional, Merlin Park, Galway',type:'AcuteHospital53'},"+
"{id:1654, pId:16, name:'Mayo General Hospital',type:'AcuteHospital54'},"+
"{id:1659, pId:16, name:'Letterkenny General Hospital',type:'AcuteHospital59'},"+
"{id:1660, pId:16, name:'Sligo General Hospital',type:'AcuteHospital60'}"+
"					]";
                                  
	
	public Integer getMessage() {
       return 6660;
   }
   public void setMessage(String message) {
        this.text = message;
    }
}