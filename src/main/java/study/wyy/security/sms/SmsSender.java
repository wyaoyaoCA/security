package study.wyy.security.sms;

/**
 * @author wyaoyao
 * @data 2019-11-04 12:38
 */
public interface SmsSender {

    void send(String mobile,String code);
}
