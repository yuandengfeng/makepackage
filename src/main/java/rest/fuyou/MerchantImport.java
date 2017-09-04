package rest.fuyou;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
public class MerchantImport {
    private String trace_no;  //唯一流水号
    private String ins_cd;    //机构号,接入机构在富友的唯一代码
    private String mchnt_name;   //商户名称
    private String mchnt_shortname;  //商户简称
    private String real_name;  //商户真实名称（与营业执照上相同）
    private int mchnt_type;  // 1.移动商户2.收单商户
    private String link_mchnt_cd=null;   //挂靠商户号，若商户需要使用之前已在富友入网商户的微信商户号，则填写需要挂靠的富友商户号。
    private String license_type;    //证件类型：0营业执照，1三证合一，2医疗许可证，3民办非企业单位凭证登记书，9其他，A身份证（一证下机）
    private String license_no;     //证件号码
    private int license_expire_dt;   // 证件到期日（格式YYYYMMDD）格式
    private String certif_id;  //法人身份证号
    private String certif_id_expire_dt=null;  //法人身份证到期日（格式YYYYMMDD）
    private String contact_person;  //联系人姓名
    private String contact_phone;  //客服电话
    private String contact_mobile;   //联系电话
    private String contact_email;     //联系邮箱
    private String business;     //经营范围代码（新开户则必填）
    private String city_cd;  //市代码
    private String county_cd;  //区县代码（必须属于city_cd所辖）
    private String acnt_type;  //入账卡类型：1：对公；2：对私
    private String bank_type; //行别 (参考行别对照表)
    private String inter_bank_no; //入账卡开户行联行号
    private String iss_bank_nm;  //入账卡开户行（在联行号找不到时生效）
    private String acnt_nm;   //入账卡户名
    private String acnt_no;   //入账卡号（不带长度位）
    private String set_cd;    //扣率套餐代码
    private String settle_amt;   //小额清算金额（单位分
    private String settle_tp;    //清算类型：1自动结算；2手动结算
    private String tx_set_cd=null;  //D0扣率套餐代码（若开通D0则必填）
    private String tx_flag;      //是否开通D0提现（0:不开通，1：开通）
    private String artif_nm;  //法人姓名(默认取contact_person) 【acnt_artif_flag = 0时必填】
    private String acnt_artif_flag=null;  //法人入账标识（0:非法人入账,1:法人入账【默认】）
    private String acnt_certif_tp;    //入账证件类型("0":"身份证"【默认】,"1":"护照","2":"军官证","3":"士兵证","4":"回乡证","5":"户口本","6":"外国护照","7":"其它")【acnt_artif_flag = 0 时必填】
    private String acnt_certif_id;   //入账证件号【acnt_artif_flag = 0时必填】
    private String acnt_certif_expire_dt=null;  //入账证件到期日（格式YYYYMMDD）
    private String contact_addr; //联系地址
    private String th_flag=null;  //退货标识(0:不能退货【默认】,1:可以退货)
    private String wx_flag=null;   //微信支付标识（0：不开通,1：开通【默认】
    private String ali_flag=null;   //支付宝支付标识（0：不开通,1：开通【默认】）
    private String wx_set_cd=null;   //微信扣率套餐代码（默认取set_cd）
    private String ali_set_cd=null;  //支付宝扣率套餐代码（默认取set_cd）
    private String qpay_flag=null;  //QQ钱包支付标识（0：不开通【默认】,1：开通）
    private String qpay_set_cd=null;   //QQ钱包扣率套餐代码（默认取wx_set_cd）
    private String jdpay_flag=null;    //JD钱包支付标识（0：不开通【默认】,1：开通）
    private String jdpay_set_cd=null;   //JD钱包扣率套餐代码（默认取wx_set_cd）
    private String wxapp_flag=null;   //微信APP支付标识（0：不开通【默认】,1：开通）
    private String wxapp_set_cd=null;   //微信APP扣率套餐代码（默认取wx_set_cd）
    private String cup_qrpay_st=null;  //银联二维码支付支付标识（0：不开通【默认】,1：开通）
    private String cup_qrpay_set_cd=null;  //银联二维码支付扣率套餐代码（cup_qrpay_st ==1时必填）
    private String wxapp_link_mchnt_cd=null;  //微信APP支付挂靠商户号，若商户需要使用之前已在富友入网商户的微信APP商户号，则填写需要挂靠的富友商户号。
    private String daily_settle_set_cd=null;   //D1扣率套餐代码（若开通D1则必填）
    private String daily_settle_flag; //是否开通D1提现（0:不开通，1：开通）
    private String acquiring_flag;  //是否开通收单业务（0:不开通，1：开通）
    private String card_set_cd;   //mchnt_type为2则必填，其他选填,借记卡扣率
    private String creditCard_set_cd;  //mchnt_type为2则必填，其他选填,贷记卡扣率

    private List<Term> terms;  //终端信息，支持多台展示终端别称，与终端号对应
    public void addTerm(Term term) {
        if (terms == null) {
            terms = new ArrayList<Term>();
        }
        terms.add(term);
    }

    private String sign;   //签名（固定32位长度key+报文体内容作MD5加密结果）

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

    public String getMchnt_shortname() {
        return mchnt_shortname;
    }

    public void setMchnt_shortname(String mchnt_shortname) {
        this.mchnt_shortname = mchnt_shortname;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public int getMchnt_type() {
        return mchnt_type;
    }

    public void setMchnt_type(int mchnt_type) {
        this.mchnt_type = mchnt_type;
    }

    public String getLink_mchnt_cd() {
        return link_mchnt_cd;
    }

    public void setLink_mchnt_cd(String link_mchnt_cd) {
        this.link_mchnt_cd = link_mchnt_cd;
    }

    public String getLicense_type() {
        return license_type;
    }

    public void setLicense_type(String license_type) {
        this.license_type = license_type;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public int getLicense_expire_dt() {
        return license_expire_dt;
    }

    public void setLicense_expire_dt(int license_expire_dt) {
        this.license_expire_dt = license_expire_dt;
    }

    public String getCertif_id() {
        return certif_id;
    }

    public void setCertif_id(String certif_id) {
        this.certif_id = certif_id;
    }

    public String getCertif_id_expire_dt() {
        return certif_id_expire_dt;
    }

    public void setCertif_id_expire_dt(String certif_id_expire_dt) {
        this.certif_id_expire_dt = certif_id_expire_dt;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getContact_mobile() {
        return contact_mobile;
    }

    public void setContact_mobile(String contact_mobile) {
        this.contact_mobile = contact_mobile;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCity_cd() {
        return city_cd;
    }

    public void setCity_cd(String city_cd) {
        this.city_cd = city_cd;
    }

    public String getCounty_cd() {
        return county_cd;
    }

    public void setCounty_cd(String county_cd) {
        this.county_cd = county_cd;
    }

    public String getAcnt_type() {
        return acnt_type;
    }

    public void setAcnt_type(String acnt_type) {
        this.acnt_type = acnt_type;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getInter_bank_no() {
        return inter_bank_no;
    }

    public void setInter_bank_no(String inter_bank_no) {
        this.inter_bank_no = inter_bank_no;
    }

    public String getIss_bank_nm() {
        return iss_bank_nm;
    }

    public void setIss_bank_nm(String iss_bank_nm) {
        this.iss_bank_nm = iss_bank_nm;
    }

    public String getAcnt_nm() {
        return acnt_nm;
    }

    public void setAcnt_nm(String acnt_nm) {
        this.acnt_nm = acnt_nm;
    }

    public String getAcnt_no() {
        return acnt_no;
    }

    public void setAcnt_no(String acnt_no) {
        this.acnt_no = acnt_no;
    }

    public String getSet_cd() {
        return set_cd;
    }

    public void setSet_cd(String set_cd) {
        this.set_cd = set_cd;
    }

    public String getSettle_amt() {
        return settle_amt;
    }

    public void setSettle_amt(String settle_amt) {
        this.settle_amt = settle_amt;
    }

    public String getSettle_tp() {
        return settle_tp;
    }

    public void setSettle_tp(String settle_tp) {
        this.settle_tp = settle_tp;
    }

    public String getTx_set_cd() {
        return tx_set_cd;
    }

    public void setTx_set_cd(String tx_set_cd) {
        this.tx_set_cd = tx_set_cd;
    }

    public String getTx_flag() {
        return tx_flag;
    }

    public void setTx_flag(String tx_flag) {
        this.tx_flag = tx_flag;
    }

    public String getArtif_nm() {
        return artif_nm;
    }

    public void setArtif_nm(String artif_nm) {
        this.artif_nm = artif_nm;
    }

    public String getAcnt_artif_flag() {
        return acnt_artif_flag;
    }

    public void setAcnt_artif_flag(String acnt_artif_flag) {
        this.acnt_artif_flag = acnt_artif_flag;
    }

    public String getAcnt_certif_tp() {
        return acnt_certif_tp;
    }

    public void setAcnt_certif_tp(String acnt_certif_tp) {
        this.acnt_certif_tp = acnt_certif_tp;
    }

    public String getAcnt_certif_id() {
        return acnt_certif_id;
    }

    public void setAcnt_certif_id(String acnt_certif_id) {
        this.acnt_certif_id = acnt_certif_id;
    }

    public String getAcnt_certif_expire_dt() {
        return acnt_certif_expire_dt;
    }

    public void setAcnt_certif_expire_dt(String acnt_certif_expire_dt) {
        this.acnt_certif_expire_dt = acnt_certif_expire_dt;
    }

    public String getContact_addr() {
        return contact_addr;
    }

    public void setContact_addr(String contact_addr) {
        this.contact_addr = contact_addr;
    }

    public String getTh_flag() {
        return th_flag;
    }

    public void setTh_flag(String th_flag) {
        this.th_flag = th_flag;
    }

    public String getWx_flag() {
        return wx_flag;
    }

    public void setWx_flag(String wx_flag) {
        this.wx_flag = wx_flag;
    }

    public String getAli_flag() {
        return ali_flag;
    }

    public void setAli_flag(String ali_flag) {
        this.ali_flag = ali_flag;
    }

    public String getWx_set_cd() {
        return wx_set_cd;
    }

    public void setWx_set_cd(String wx_set_cd) {
        this.wx_set_cd = wx_set_cd;
    }

    public String getAli_set_cd() {
        return ali_set_cd;
    }

    public void setAli_set_cd(String ali_set_cd) {
        this.ali_set_cd = ali_set_cd;
    }

    public String getQpay_flag() {
        return qpay_flag;
    }

    public void setQpay_flag(String qpay_flag) {
        this.qpay_flag = qpay_flag;
    }

    public String getQpay_set_cd() {
        return qpay_set_cd;
    }

    public void setQpay_set_cd(String qpay_set_cd) {
        this.qpay_set_cd = qpay_set_cd;
    }

    public String getJdpay_flag() {
        return jdpay_flag;
    }

    public void setJdpay_flag(String jdpay_flag) {
        this.jdpay_flag = jdpay_flag;
    }

    public String getJdpay_set_cd() {
        return jdpay_set_cd;
    }

    public void setJdpay_set_cd(String jdpay_set_cd) {
        this.jdpay_set_cd = jdpay_set_cd;
    }

    public String getWxapp_flag() {
        return wxapp_flag;
    }

    public void setWxapp_flag(String wxapp_flag) {
        this.wxapp_flag = wxapp_flag;
    }

    public String getWxapp_set_cd() {
        return wxapp_set_cd;
    }

    public void setWxapp_set_cd(String wxapp_set_cd) {
        this.wxapp_set_cd = wxapp_set_cd;
    }

    public String getCup_qrpay_st() {
        return cup_qrpay_st;
    }

    public void setCup_qrpay_st(String cup_qrpay_st) {
        this.cup_qrpay_st = cup_qrpay_st;
    }

    public String getCup_qrpay_set_cd() {
        return cup_qrpay_set_cd;
    }

    public void setCup_qrpay_set_cd(String cup_qrpay_set_cd) {
        this.cup_qrpay_set_cd = cup_qrpay_set_cd;
    }

    public String getWxapp_link_mchnt_cd() {
        return wxapp_link_mchnt_cd;
    }

    public void setWxapp_link_mchnt_cd(String wxapp_link_mchnt_cd) {
        this.wxapp_link_mchnt_cd = wxapp_link_mchnt_cd;
    }

    public String getDaily_settle_set_cd() {
        return daily_settle_set_cd;
    }

    public void setDaily_settle_set_cd(String daily_settle_set_cd) {
        this.daily_settle_set_cd = daily_settle_set_cd;
    }

    public String getDaily_settle_flag() {
        return daily_settle_flag;
    }

    public void setDaily_settle_flag(String daily_settle_flag) {
        this.daily_settle_flag = daily_settle_flag;
    }

    public String getAcquiring_flag() {
        return acquiring_flag;
    }

    public void setAcquiring_flag(String acquiring_flag) {
        this.acquiring_flag = acquiring_flag;
    }

    public String getCard_set_cd() {
        return card_set_cd;
    }

    public void setCard_set_cd(String card_set_cd) {
        this.card_set_cd = card_set_cd;
    }

    public String getCreditCard_set_cd() {
        return creditCard_set_cd;
    }

    public void setCreditCard_set_cd(String creditCard_set_cd) {
        this.creditCard_set_cd = creditCard_set_cd;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


}
