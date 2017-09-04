package rest.fuyou;

import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
public class Term {

    private String  term_serial_no;   //终端序列号
    private String term_name=null;    //终端别名
    private String term_id;   //终端号

    public String getTerm_id() {
        return term_id;
    }

    public void setTerm_id(String term_id) {
        this.term_id = term_id;
    }

    public String getTerm_serial_no() {
        return term_serial_no;
    }

    public void setTerm_serial_no(String term_serial_no) {
        this.term_serial_no = term_serial_no;
    }

    public String getTerm_name() {
        return term_name;
    }

    public void setTerm_name(String term_name) {
        this.term_name = term_name;
    }
    @Override
    public String toString(){
//        return new Gson().toJson(this);
        return JSONObject.fromObject(this).toString();
    }


}


