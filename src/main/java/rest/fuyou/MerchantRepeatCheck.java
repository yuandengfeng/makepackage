package rest.fuyou;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
public class MerchantRepeatCheck {

    private String trace_no;
    private String ins_cd=null;
    private String mchnt_name=null;
    private String sign;

    public String getTrace_no() {
        return trace_no;
    }

    public void setTrace_no(String trace_no) {
        this.trace_no = trace_no;
    }

    public String getIns_cd() {
        return ins_cd;
    }

    public void setIns_cd(String ins_cd) {
        this.ins_cd = ins_cd;
    }

    public String getMchnt_name() {
        return mchnt_name;
    }

    public void setMchnt_name(String mchnt_name) {
        this.mchnt_name = mchnt_name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "MerchantRepeatCheck[trace_no=" + trace_no + ",ins_cd=" + ins_cd +",mchnt_name"+mchnt_name+",sign"+sign+ "]";
    }



}
