package h2.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "WenShuData")
public class WenShuData {

    @DatabaseField(id = true,width = 10000)
    private String WENSHUID;  //docno

    @DatabaseField(index = true,width = 10000)
    private String  AJLX;  //ajlx

    @DatabaseField(index = true, width = 10000)
    private String DRRQ;   //date

    @DatabaseField(index = true,width = 10000)
    private String AJMC; //ajmc

    @DatabaseField(dataType = DataType.LONG_STRING)
    private String AH;  //ah

    @DatabaseField(index = true,width = 10000)
    private String FYMC;  //fymc

    @DatabaseField(index = true,width = 10000)
    private String SPCX;  //spcx

    @DatabaseField(index = true,width = 10000)
    private String CPRQ;  //cprj

    @DatabaseField(dataType = DataType.LONG_STRING)
    private String CPYZYW;  //cpyyd

    @DatabaseField(index = true,width = 10000)
    private String WORD=null;  //null

    @DatabaseField(index = true,width = 10000)
    private int ISWORDGOT=0;  //0

    @DatabaseField(dataType = DataType.LONG_STRING)
    private String  DOC=null;  //null

    public String getWENSHUID() {
        return WENSHUID;
    }

    public void setWENSHUID(String WENSHUID) {
        this.WENSHUID = WENSHUID;
    }

    public String getAJLX() {
        return AJLX;
    }

    public void setAJLX(String AJLX) {
        this.AJLX = AJLX;
    }

    public String getDRRQ() {
        return DRRQ;
    }

    public void setDRRQ(String DRRQ) {
        this.DRRQ = DRRQ;
    }

    public String getAJMC() {
        return AJMC;
    }

    public void setAJMC(String AJMC) {
        this.AJMC = AJMC;
    }

    public String getAH() {
        return AH;
    }

    public void setAH(String AH) {
        this.AH = AH;
    }

    public String getFYMC() {
        return FYMC;
    }

    public void setFYMC(String FYMC) {
        this.FYMC = FYMC;
    }

    public String getSPCX() {
        return SPCX;
    }

    public void setSPCX(String SPCX) {
        this.SPCX = SPCX;
    }

    public String getCPRQ() {
        return CPRQ;
    }

    public void setCPRQ(String CPRQ) {
        this.CPRQ = CPRQ;
    }

    public String getCPYZYW() {
        return CPYZYW;
    }

    public void setCPYZYW(String CPYZYW) {
        this.CPYZYW = CPYZYW;
    }

    public String getWORD() {
        return WORD;
    }

    public void setWORD(String WORD) {
        this.WORD = WORD;
    }

    public int getISWORDGOT() {
        return ISWORDGOT;
    }

    public void setISWORDGOT(int ISWORDGOT) {
        this.ISWORDGOT = ISWORDGOT;
    }

    public String getDOC() {
        return DOC;
    }

    public void setDOC(String DOC) {
        this.DOC = DOC;
    }

  public String toString(){
      return this.getWENSHUID()+","+this.getAH()+","+this.getAJLX()+","+this.getAJMC()+
              ","+this.getCPRQ()+","+this.getCPYZYW()+","+this.getDOC()+","+this.getDRRQ()+
              ","+this.getFYMC()+","+this.getFYMC();
  }


}
